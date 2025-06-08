package org.app.Controller

import jakarta.ws.rs.core.Response.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.app.dto.GraphQLResponse
import org.app.Repository.VendorPersonRepository
import org.app.Entity.VendorPersonalDetails
import org.app.dto.VendorPersonalDetailsDto
import org.eclipse.microprofile.graphql.*
import java.util.UUID

@GraphQLApi
class VendorGraphQLController(
    private val vendorRepo: VendorPersonRepository
) {

    private val logger: Logger = LogManager.getLogger(VendorGraphQLController::class.java)

    @Query("getVendorDetails")
    @Description("Get all the personal details for a vendor")
    fun getVendorDetail(
        @Name("firstName") firstName: String,
        @Name("lastName") lastName: String
    ): VendorPersonalDetails {
        logger.info("Fetching vendor with firstName: $firstName and lastName: $lastName")
        return runBlocking {vendorRepo.findByName(firstName, lastName) ?: VendorPersonalDetails()}
    }


    @Query("getByVendorId")
    fun getVendorDetailsById(@Name("vendorId") vendorId: UUID): VendorPersonalDetails {
        logger.info("Fetching vendor with ID: $vendorId")
        return runBlocking { vendorRepo.findByVendorId(vendorId)} ?: VendorPersonalDetails()
    }

    @Query("getByVendorUsername")
    fun getByVendorUsername(@Name("username") username: String): VendorPersonalDetails {
        logger.info("Fetching vendor with username: $username")
        return runBlocking { vendorRepo.findByVendorUsername(username)} ?: VendorPersonalDetails()
    }

    @Mutation("createVendorProfile")
    fun createVendorProfile(@Name("vendorPersonalDetails") vendorPersonalDetails: VendorPersonalDetailsDto): GraphQLResponse {
        val firstName = vendorPersonalDetails.firstName
        val lastName = vendorPersonalDetails.lastName
        try{
            logger.info("Creating profile for vendor with firstName {} and lastName {}", firstName, lastName)
            val vendorEntity = vendorPersonalDetails.convertToEntity()
            val vendorId = runBlocking {vendorRepo.createProfile(vendorEntity)} ?: throw Exception("Vendor id is empty")
            logger.info("Successfully created profile: vendorId {}, firstName {} and lastName {}", vendorId, firstName, lastName)
            return GraphQLResponse(message = "Successfully created profile: vendorId {}, firstName {} and lastName {}", status = Status.OK.statusCode)
        }catch (ex: Exception){
            logger.error("Error occurred while trying to save vendorPersonalDetails with firstName {} and lastName {}", firstName, lastName)
            return GraphQLResponse(status = Status.INTERNAL_SERVER_ERROR.statusCode, message = "An error occurred while creating profile for firstName: $firstName and lastName: $lastName. Exception: $ex")
        }
    }

    @Mutation("updateVendorProfile")
    fun updateVendorProfile(@Name("vendorPersonalDetails") vendorPersonalDetails: VendorPersonalDetailsDto): GraphQLResponse {
        val vendorId = vendorPersonalDetails.vendorId
        val vendorEntity = vendorPersonalDetails.convertToEntity()
        try{
            runBlocking {
                vendorRepo.updateVendorProfile(vendorEntity)
            }
            return GraphQLResponse(message = "Successfully updated vendor details for vendorId: ${vendorPersonalDetails.vendorId}", status = Status.OK.statusCode)
        }catch (ex: Exception){
            logger.error("Error occurred while trying to updating vendorPersonalDetails with vendorId {}", vendorId)
            return GraphQLResponse(status = Status.INTERNAL_SERVER_ERROR.statusCode, message = "An error occurred while updating profile for vendorId: $vendorId. Exception: $ex")
        }
    }

//    @Mutation("updateTwice")
//    fun updateTwice(@Name("username") username: String, @Name("exception") exception: Boolean): GraphQLResponse {
//        try {
//            runBlocking {
//                logger.info("Attempt to update twice for username $username")
//                vendorRepo.twoUpdates(username, exception)
//            }
//            return GraphQLResponse(message = "Successfully updated vendor details for vendorId", status = Status.OK.statusCode)
//        } catch (ex: Exception){
//            logger.error("Error occurred while updating twice", ex)
//            return GraphQLResponse(message = "An error occurred while updating twice. Exception: $ex", status = Status.INTERNAL_SERVER_ERROR.statusCode)
//        }
//    }
}
package org.app.Controller

import jakarta.ws.rs.core.Response.Status
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.app.dto.GraphQLResponse
import org.app.Repository.VendorPersonRepository
import org.app.Entity.VendorPersonalDetails
import org.app.Mapper.VendorPersonalDetailsMapper
import org.app.dto.VendorPersonalDetailsDto
import org.eclipse.microprofile.graphql.*

@GraphQLApi
class VendorGraphQLController(
    private val vendorRepo: VendorPersonRepository,
    private val vendorPersonalDetailsMapper: VendorPersonalDetailsMapper
) {

    private val logger: Logger = LogManager.getLogger(VendorGraphQLController::class.java)

    @Query("searchVendor")
    @Description("Get all the personal details for a vendor")
    fun searchVendorDetail(
        @Name("name") name: String
    ): List<VendorPersonalDetailsDto> {
        logger.info("Fetching vendor with name: $name")

        val vendorDetails = runBlocking {vendorRepo.findByName(name)}
        val vendorDetailsDto = vendorDetails.map{vendorDetail->
            vendorPersonalDetailsMapper.toDto(vendorDetail)
        }
        return vendorDetailsDto
    }


    @Mutation("createVendor")
    fun createVendorProfile(@Name("vendorPersonalDetails") vendorPersonalDetails: VendorPersonalDetailsDto): GraphQLResponse {
        val firstName = vendorPersonalDetails.firstName
        val lastName = vendorPersonalDetails.lastName

        try{
            validateNames(firstName, lastName)
            logger.info("Creating profile for vendor with firstName {} and lastName {}", firstName, lastName)

            val vendorEntity = vendorPersonalDetailsMapper.toEntity(vendorPersonalDetails)
            val vendorId = runBlocking {vendorRepo.createProfile(vendorEntity)}

            logger.info("Successfully created profile: vendorId {}, firstName {} and lastName {}", vendorId, firstName, lastName)

            return GraphQLResponse(message = "Successfully created profile: vendorId $vendorId, firstName $firstName and lastName $lastName", status = Status.OK)
        }catch (ex: Exception){
            logger.error("Error occurred while trying to save vendorPersonalDetails with firstName $firstName and lastName $lastName", ex.message)
            return GraphQLResponse(status = Status.INTERNAL_SERVER_ERROR, message = "An error occurred while creating profile for firstName: $firstName and lastName: $lastName. Exception: $ex")
        }
    }

    @Mutation("updateVendorDetails")
    fun updateVendorProfile(@Name("vendorPersonalDetails") vendorPersonalDetails: VendorPersonalDetailsDto): GraphQLResponse {
        val vendorEntity = vendorPersonalDetailsMapper.toEntity(vendorPersonalDetails)
        val firstName = vendorPersonalDetails.firstName
        val lastName = vendorPersonalDetails.lastName

        try{
            validateNames(firstName, lastName)
            logger.info("Updating vendor with firstName {} and lastName {}", firstName, lastName)

            val vendorId = runBlocking {
                vendorRepo.updateVendorProfile(vendorEntity)
            }

            return GraphQLResponse(message = "Successfully updated vendor details for vendorId: $vendorId", status = Status.OK)
        }catch (ex: Exception){
            logger.error("Error occurred while trying to updating vendorPersonalDetails with firstName $firstName and lastName $lastName", ex.message)
            return GraphQLResponse(status = Status.INTERNAL_SERVER_ERROR, message = "An error occurred while updating profile for vendorId: ${vendorPersonalDetails.username}. Exception: $ex")
        }
    }

    private fun validateNames(firstName: String, lastName: String) {
        logger.info("Validating firstname {} and lastname {}", firstName, lastName)
        if (firstName.isBlank() || firstName.contains("\\s".toRegex())) {
            throw IllegalArgumentException("First name must be a single, non-blank word")
        }
        if (lastName.isBlank() || lastName.contains("\\s".toRegex())) {
            throw IllegalArgumentException("Last name must be a single, non-blank word")
        }
    }
}
package org.app.Controller

import kotlinx.coroutines.runBlocking
import org.jboss.logging.Logger
import org.app.dto.GraphQLResponse
import org.app.Repository.VendorPersonRepository
import org.app.Mapper.VendorPersonalDetailsMapper
import org.app.dto.Status
import org.app.dto.VendorListResponse
import org.app.dto.VendorPersonalDetailsDto
import org.eclipse.microprofile.graphql.*

@GraphQLApi
class VendorGraphQLController(
    private val vendorRepo: VendorPersonRepository,
    private val vendorPersonalDetailsMapper: VendorPersonalDetailsMapper
) {

    private val logger: Logger = Logger.getLogger(VendorGraphQLController::class.java)

    @Query("searchVendor")
    @Description("Get a list of vendor details based on search")
    fun searchVendorDetail(
        @Name("name") name: String
    ): VendorListResponse {
        try {
            if (name.isBlank()) {
                throw IllegalArgumentException("Name must not be blank")
            }
            logger.info("Fetching vendor with name: $name")

            val vendorDetails = runBlocking { vendorRepo.findByName(name) }
            val vendorDetailsDto = vendorDetails.map { vendorDetail ->
                vendorPersonalDetailsMapper.toDto(vendorDetail)
            }
            logger.info("Gotten vendor details of size ${vendorDetails.size} while fetching vendors with $name")
            return VendorListResponse(status = Status.OK, weddingDetails = vendorDetailsDto)
        }catch (ex: Exception){
            logger.error("Error occurred while trying to search vendorPersonalDetails with name $name ", ex)
            return VendorListResponse(status = Status.INTERNAL_SERVER_ERROR, message = "An error occurred while finding profile for name: $name. Exception: $ex")

        }
    }


    @Mutation("createVendor")
    fun createVendorProfile(@Name("vendorPersonalDetails") vendorPersonalDetails: VendorPersonalDetailsDto): GraphQLResponse {
        val firstName = vendorPersonalDetails.firstName
        val lastName = vendorPersonalDetails.lastName
        val username = vendorPersonalDetails.username

        try{
            validateNames(firstName, lastName)
            logger.info("Creating profile for vendor with username $username, firstName $firstName and lastName $lastName")

            val vendorEntity = vendorPersonalDetailsMapper.toEntity(vendorPersonalDetails)
            val entityUsername = runBlocking {vendorRepo.createProfile(vendorEntity)}

            logger.info("Successfully created profile: username $entityUsername, firstName $firstName and lastName $lastName")

            return GraphQLResponse(message = "Successfully created profile: username $entityUsername, firstName $firstName and lastName $lastName", status = Status.OK)
        }catch (ex: Exception){
            logger.error("Error occurred while trying to save vendorPersonalDetails with username $username, firstName $firstName and lastName $lastName", ex)
            return GraphQLResponse(status = Status.INTERNAL_SERVER_ERROR, message = "An error occurred while creating profile for firstName: $firstName and lastName: $lastName. Exception: $ex")
        }
    }

    @Mutation("updateVendorDetails")
    fun updateVendorProfile(@Name("vendorPersonalDetails") vendorPersonalDetails: VendorPersonalDetailsDto,
                            @Name("username")  username: String): GraphQLResponse {
        val firstName = vendorPersonalDetails.firstName
        val lastName = vendorPersonalDetails.lastName

        try{
            validateNames(firstName, lastName)
            logger.info("Updating vendor with username $username, firstName $firstName and lastName $lastName")

            val vendorEntity = vendorPersonalDetailsMapper.toEntity(vendorPersonalDetails)
            val entityUsername = runBlocking {
                vendorRepo.updateVendorProfile(vendorEntity, username)
            }

            return GraphQLResponse(message = "Successfully updated vendor details for username: $entityUsername", status = Status.OK)
        }catch (ex: Exception){
            logger.error("Error occurred while trying to updating vendorPersonalDetails with username $username, firstName $firstName and lastName $lastName", ex)
            return GraphQLResponse(status = Status.INTERNAL_SERVER_ERROR, message = "An error occurred while updating profile for username: $username. Exception: $ex")
        }
    }

    private fun validateNames(firstName: String, lastName: String) {
        logger.info("Validating firstname $firstName and lastname $lastName")
        if (firstName.isBlank() || firstName.contains("\\s".toRegex())) {
            throw IllegalArgumentException("First name must be a single, non-blank word")
        }
        if (lastName.isBlank() || lastName.contains("\\s".toRegex())) {
            throw IllegalArgumentException("Last name must be a single, non-blank word")
        }
    }
}
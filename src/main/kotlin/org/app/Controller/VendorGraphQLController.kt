package org.app.Controller

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.app.Repository.VendorPersonRepository
import org.app.Entity.VendorPersonalDetails
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Name
import org.eclipse.microprofile.graphql.Query

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
        return vendorRepo.findByName(firstName, lastName) ?: VendorPersonalDetails()
    }


    @Query("getByVendorId")
    fun getVendorDetailsById(@Name("vendorId") vendorId: Int): VendorPersonalDetails? {
        logger.info("Fetching vendor with ID: $vendorId")
        return vendorRepo.findById(vendorId.toLong()) ?: VendorPersonalDetails()
    }
}
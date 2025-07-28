package org.app.Repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.app.Controller.VendorGraphQLController
import org.app.Entity.VendorPersonalDetails
import java.util.UUID

//TODO: DB changes - indexing for firstname and lastname

@ApplicationScoped
class VendorPersonRepository : PanacheRepository<VendorPersonalDetails>{

    private val logger: Logger = LogManager.getLogger(VendorPersonRepository::class.java)

    suspend fun findByName(nameInput: String): List<VendorPersonalDetails> {
        logger.info("Making a call to the database to search for user with name: $nameInput")
        val names = nameInput.trim().split("\\s+.toRegex()")
        return when(names.size){
            1 -> {
                val name = names[0]
                find("LOWER(firstName) LIKE LOWER(?1) OR LOWER(lastName) LIKE(?1) OR LOWER(username) LIKE(?1)", name).list()
            }
            2-> {
                val (name1, name2) = names
                find(
                    """
                (LOWER(firstName) LIKE LOWER(?1) AND LOWER(lastName) LIKE LOWER(?2)) OR 
                (LOWER(firstName) LIKE LOWER(?2) AND LOWER(lastName) LIKE LOWER(?1)) 
                """.trimIndent(),
                    name1, name2
                ).list()
            }
            else -> {
                emptyList()
            }
        }
    }

    suspend fun findByVendorId(vendorId: UUID): VendorPersonalDetails? {
        return find("vendorId = ?1", vendorId).firstResult()
    }

    //TODO: set constraints for username to be unique, so creating profile fails if non-unique#
    //check for if username exist
    @Transactional
    suspend fun createProfile(vendorPersonalDetails: VendorPersonalDetails): UUID{
        persist(vendorPersonalDetails)
        return vendorPersonalDetails.vendorId
    }

    @Transactional
    suspend fun updateVendorProfile(vendorPersonalDetails: VendorPersonalDetails): UUID{
        var existingVendorDetails = vendorPersonalDetails.vendorId?.let { findByVendorId(it) } ?: throw Exception("Vendor details does not exist for vendorId: ${vendorPersonalDetails.vendorId} and username: ${vendorPersonalDetails.username}")
        existingVendorDetails = updateRecords(existingVendorDetails, vendorPersonalDetails)
        persist(existingVendorDetails)
        return vendorPersonalDetails.vendorId
    }

    suspend fun findByVendorUsername(username: String): VendorPersonalDetails? {
        return find("vendorId = ?1", username).firstResult()
    }

    suspend fun updateRecords(existingVendorDetail: VendorPersonalDetails, newVendorDetail: VendorPersonalDetails): VendorPersonalDetails {
        existingVendorDetail.lastName = newVendorDetail.lastName
        existingVendorDetail.vendorId = newVendorDetail.vendorId
        existingVendorDetail.firstName = newVendorDetail.firstName
        existingVendorDetail.bio = newVendorDetail.bio
        existingVendorDetail.city= newVendorDetail.city
        existingVendorDetail.profilePicture = newVendorDetail.profilePicture
        existingVendorDetail.coverPhoto = newVendorDetail.coverPhoto
        existingVendorDetail.profession =newVendorDetail.profession
        existingVendorDetail.phone = newVendorDetail.phone
        existingVendorDetail.email = newVendorDetail.email
        existingVendorDetail.password = newVendorDetail.password
        existingVendorDetail.username = newVendorDetail.username
        return existingVendorDetail
    }
}
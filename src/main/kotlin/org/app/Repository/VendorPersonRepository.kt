package org.app.Repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.jboss.logging.Logger
import org.app.Entity.VendorPersonalDetails

//TODO: DB changes - indexing for firstname and lastname

@ApplicationScoped
class VendorPersonRepository : PanacheRepository<VendorPersonalDetails>{

    private val logger: Logger = Logger.getLogger(VendorPersonRepository::class.java)

    suspend fun findByName(nameInput: String): List<VendorPersonalDetails> {
        logger.info("Searching for user with name: $nameInput")
        val names = nameInput.trim().split(" ")
        logger.info("Names to fetch from the database: $names")
        return when(names.size){
            1 -> {
                val name = names[0]
                find("LOWER(firstName) LIKE LOWER(CONCAT(?1, '%')) OR LOWER(lastName) LIKE(CONCAT(?1, '%')) OR LOWER(username) LIKE(CONCAT(?1, '%'))", name).list()
            }
            2-> {
                val (name1, name2) = names
                find(
                    """
                (LOWER(firstName) LIKE LOWER(CONCAT(?1, '%')) AND LOWER(lastName) LIKE LOWER(CONCAT(?2, '%'))) OR 
                (LOWER(firstName) LIKE LOWER(CONCAT(?2, '%')) AND LOWER(lastName) LIKE LOWER(CONCAT(?1, '%'))) 
                """.trimIndent(),
                    name1, name2
                ).list()
            }
            else -> {
                emptyList()
            }
        }
    }

    @Transactional
    suspend fun createProfile(vendorPersonalDetails: VendorPersonalDetails): String {
        if(findByVendorUsername(vendorPersonalDetails.username) != null) {
            throw Exception("Vendor details with username ${vendorPersonalDetails.username} already exist")
        }
        persist(vendorPersonalDetails)
        return vendorPersonalDetails.username
    }

    @Transactional
    suspend fun updateVendorProfile(vendorPersonalDetails: VendorPersonalDetails): String {
        var existingVendorDetails = vendorPersonalDetails.username.let { findByVendorUsername(it) } ?: throw Exception("Vendor details does not exist for vendorId: ${vendorPersonalDetails.username} and username: ${vendorPersonalDetails.username}")
        existingVendorDetails = updateRecords(existingVendorDetails, vendorPersonalDetails)
        persist(existingVendorDetails)
        return vendorPersonalDetails.username
    }

    suspend fun findByVendorUsername(username: String): VendorPersonalDetails? {
        return find("vendorId = ?1", username).firstResult()
    }

    suspend fun updateRecords(existingVendorDetail: VendorPersonalDetails, newVendorDetail: VendorPersonalDetails): VendorPersonalDetails {
        existingVendorDetail.lastName = newVendorDetail.lastName
        existingVendorDetail.firstName = newVendorDetail.firstName
        existingVendorDetail.bio = newVendorDetail.bio
        existingVendorDetail.location= newVendorDetail.location
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
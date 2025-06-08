package org.app.Repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.app.Controller.VendorGraphQLController
import org.app.Entity.VendorPersonalDetails
import java.util.UUID

@ApplicationScoped
class VendorPersonRepository : PanacheRepository<VendorPersonalDetails>{

    private val logger: Logger = LogManager.getLogger(VendorPersonRepository::class.java)

    suspend fun findByName(firstName: String, lastName: String): VendorPersonalDetails? {
        return find("firstName = ?1 and lastName = ?2", firstName, lastName).firstResult()
    }

    suspend fun findByVendorId(vendorId: UUID): VendorPersonalDetails? {
        return find("vendorId = ?1", vendorId).firstResult()
    }

    @Transactional
    suspend fun createProfile(vendorPersonalDetails: VendorPersonalDetails): UUID?{
        persist(vendorPersonalDetails)
        return vendorPersonalDetails.vendorId
    }

    @Transactional
    suspend fun updateVendorProfile(vendorPersonalDetails: VendorPersonalDetails){
        if(vendorPersonalDetails.vendorId == null){throw Exception("Vendor Id doesn't exist while updating record")}
        var existingVendorDetails = vendorPersonalDetails.vendorId?.let { findByVendorId(it) } ?: throw Exception("Vendor details does not exist for vendorId: ${vendorPersonalDetails.vendorId} and username: ${vendorPersonalDetails.username}")
        existingVendorDetails = updateRecords(existingVendorDetails, vendorPersonalDetails)
        persist(existingVendorDetails)
    }

//    @Transactional(rollbackOn = [Exception::class])
//    fun twoUpdates(username: String, exception: Boolean){
//        val vendorPersonalDetails = find("username = ?1", username).firstResult() ?: throw Exception("Vendor details does not exist for username: $username")
//        vendorPersonalDetails.firstName = "first"
//        persist(vendorPersonalDetails)
//        logger.info("Update once for username: $username")
//        if(exception){
//            throw Exception("testing transaction")
//        }
//        vendorPersonalDetails.firstName = "second"
//        persist(vendorPersonalDetails)
//        logger.info("Updated twice for username: $username")
//    }

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
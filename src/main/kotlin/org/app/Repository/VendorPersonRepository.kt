package org.app.Repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.app.Entity.VendorPersonalDetails
import java.util.UUID

@ApplicationScoped
class VendorPersonRepository : PanacheRepository<VendorPersonalDetails>{
    fun findByName(firstName: String, lastName: String): VendorPersonalDetails? {
        return find("firstName = ?1 and lastName = ?2", firstName, lastName).firstResult()
    }

    @Transactional
    fun createProfile(vendorPersonalDetails: VendorPersonalDetails): UUID?{
        persist(vendorPersonalDetails)
        return vendorPersonalDetails.vendorId
    }
}
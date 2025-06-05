package org.app

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class VendorPersonRepository : PanacheRepository<VendorPersonalDetails>{
    fun findByName(firstName: String, lastName: String): VendorPersonalDetails? {
        return find("firstName = ?1 and lastName = ?2", firstName, lastName).firstResult()
    }
}
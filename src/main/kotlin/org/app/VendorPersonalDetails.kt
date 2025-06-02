package org.app

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import java.util.UUID

@Entity
class VendorPersonalDetails: PanacheEntity() {
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var bio: String
    lateinit var city: String
    lateinit var country: String
    lateinit var profilePicture: String
    lateinit var coverPhoto: String
    lateinit var profession: String
    lateinit var vendorId: UUID
}
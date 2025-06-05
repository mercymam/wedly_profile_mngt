package org.app

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.UUID

@Entity
class VendorPersonalDetails: PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var vendorId: Long? = null
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
}
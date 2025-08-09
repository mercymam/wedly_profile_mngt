package org.app.Entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "vendor_details")
class VendorPersonalDetails: PanacheEntityBase{
    @Column(name = "first_name")
    lateinit var firstName: String

    @Column(name = "bio")
    lateinit var bio: String

    @Column(name = "location")
    lateinit var city: String

    @Column(name = "profile_picture")
    lateinit var profilePicture: String

    @Column(name = "cover_photo")
    lateinit var coverPhoto: String

    @Column(name = "profession")
    lateinit var profession: String

    @Column(name = "phone_number")
    lateinit var phone: String

    @Column(name = "email_address")
    lateinit var email: String

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    var vendorId: Long? = null

    @Column(name = "last_name")
    lateinit var lastName: String

    @Column(name = "password")
    lateinit var password: String

    @Column(name = "username")
    lateinit var username: String
}
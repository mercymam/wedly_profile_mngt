package org.app.Entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "vendor_details")
class VendorPersonalDetails: PanacheEntityBase {
    @Column(name = "first_name")
    var firstName: String? = "Me"

    @Column(name = "bio")
    var bio: String? = null

    @Column(name = "location")
    var city: String? = null

    @Lob
    @Column(name = "profile_picture", columnDefinition = "BYTEA")
    var profilePicture: ByteArray? = null

    @Lob
    @Column(name = "cover_photo", columnDefinition = "BYTEA")
    var coverPhoto: ByteArray? = null

    @Column(name = "profession")
    var profession: String? = null

    @Column(name = "phone_number")
    var phone: String? = null

    @Column(name = "email_address")
    var email: String? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    var vendorId: Long? = null

    @Column(name = "last_name")
    var lastName: String? = null
}
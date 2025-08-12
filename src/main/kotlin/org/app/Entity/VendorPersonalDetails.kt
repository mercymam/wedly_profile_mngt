package org.app.Entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*

@Entity
@Table(name = "vendor_details")
class VendorPersonalDetails: PanacheEntityBase{
    @Column(name = "first_name")
    lateinit var firstName: String

    @Column(name = "bio")
    lateinit var bio: String

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    lateinit var location: Location

    @Column(name = "profile_picture")
    var profilePicture: String? = null

    @Column(name = "cover_photo")
    var coverPhoto: String? = null

    @Column(name = "profession")
    lateinit var profession: String

    @Column(name = "phone_number")
    var phone: String? = null

    @Column(name = "email_address")
    lateinit var email: String

    @Column(name = "last_name")
    lateinit var lastName: String

    @Column(name = "password")
    lateinit var password: String

    @Id
    @Column(name = "username")
    lateinit var username: String
}
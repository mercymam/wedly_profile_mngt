package org.app.Entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "customer_details")
class CustomerPersonalDetails: PanacheEntityBase {
    @Column(name = "first_name")
    lateinit var firstName: String

    @Column(name = "bio")
    var bio: String? = null

    @Column(name = "location")
    lateinit var location: String

    @Column(name = "profile_picture")
    var profilePicture: String? = null

    @Column(name = "cover_photo")
    var coverPhoto: String? = null

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
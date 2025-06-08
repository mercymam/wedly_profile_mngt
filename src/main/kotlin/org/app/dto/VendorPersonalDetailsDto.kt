package org.app.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.json.bind.annotation.JsonbCreator
import org.app.Entity.VendorPersonalDetails
import org.eclipse.microprofile.graphql.Input
import java.util.*

@Input("VendorPersonalDetailsDto")
data class VendorPersonalDetailsDto @JsonbCreator constructor(
    var firstName: String? = null,
    var bio: String? = null,
    var city: String? = null,
    var profilePicture: String? = null,
    var coverPhoto: String? = null,
    var profession: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var vendorId: UUID? = null,
    var lastName: String? = null,
    var password: String? = null,
    @JsonProperty("username")
    var username: String
){

    fun convertToEntity() = VendorPersonalDetails().apply {
        firstName = this@VendorPersonalDetailsDto.firstName
        bio = this@VendorPersonalDetailsDto.bio
        city = this@VendorPersonalDetailsDto.city
        profilePicture = this@VendorPersonalDetailsDto.profilePicture
        coverPhoto = this@VendorPersonalDetailsDto.coverPhoto
        profession = this@VendorPersonalDetailsDto.profession
        phone = this@VendorPersonalDetailsDto.phone
        email = this@VendorPersonalDetailsDto.email
        vendorId = this@VendorPersonalDetailsDto.vendorId
        lastName = this@VendorPersonalDetailsDto.lastName
        password = this@VendorPersonalDetailsDto.password
        username = this@VendorPersonalDetailsDto.username
    }
}
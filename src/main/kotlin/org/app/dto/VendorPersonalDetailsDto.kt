package org.app.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.json.bind.annotation.JsonbCreator
import org.app.Entity.VendorPersonalDetails
import org.eclipse.microprofile.graphql.Input
import java.util.*

//TODO: consider for dto to entity MapStruct, ModelMapper, or simple Kotlin extension functions
@Input("VendorPersonalDetailsDto")
data class VendorPersonalDetailsDto @JsonbCreator constructor(
    var firstName: String,
    var bio: String? = null,
    var city: String? = null,
    var profilePicture: String? = null,
    var coverPhoto: String? = null,
    var profession: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var lastName: String,
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
        lastName = this@VendorPersonalDetailsDto.lastName
        password = this@VendorPersonalDetailsDto.password
        username = this@VendorPersonalDetailsDto.username
    }
}
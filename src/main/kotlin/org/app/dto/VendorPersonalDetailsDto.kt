package org.app.dto

import org.app.Entity.VendorPersonalDetails
import org.eclipse.microprofile.graphql.Input
import java.util.*

@Input("VendorPersonalDetailsDto")
data class VendorPersonalDetailsDto (
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
    }
}
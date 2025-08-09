package org.app.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.json.bind.annotation.JsonbCreator
import org.eclipse.microprofile.graphql.Input


@Input("VendorPersonalDetailsInput")
data class VendorPersonalDetailsDto @JsonbCreator constructor(
    var firstName: String,
    var bio: String,
    var city: String,
    var profilePicture: String? = null,
    var coverPhoto: String? = null,
    var profession: String,
    var phone: String? = null,
    var email: String,
    var lastName: String,
    var password: String,
    @JsonProperty("username")
    var username: String
)
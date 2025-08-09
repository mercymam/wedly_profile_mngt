package org.app.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.json.bind.annotation.JsonbCreator
import org.eclipse.microprofile.graphql.Input


@Input("VendorPersonalDetailsInput")
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
)
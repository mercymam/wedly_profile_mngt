package org.app.dto

import jakarta.json.bind.annotation.JsonbCreator
import org.eclipse.microprofile.graphql.Input
import org.eclipse.microprofile.graphql.Name

@Input("LocationDataObject")
data class LocationDto @JsonbCreator constructor(
    @field:Name("postcode")
    val postcode: String ?= null,

    @field:Name("city")
    val city: String,

    @field:Name("country")
    val country: String,

    @field:Name("addressLine1")
    val addressLine1: String ?= null,

    @field:Name("addressLine2")
    val addressLine2: String ?= null
)
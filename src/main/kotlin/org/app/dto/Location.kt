package org.app.dto

data class LocationDto (
    val postcode: String,
    val city: String,
    val country: String,
    val addressLine1: String,
    val addressLine2: String
)
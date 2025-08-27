package org.app.dto

import jakarta.json.bind.annotation.JsonbCreator
import org.eclipse.microprofile.graphql.Input
import org.eclipse.microprofile.graphql.Name
import java.util.*

//TODO: validate all amount and budget value to 2dp
@Input("WeddingRequestInput")
data class WeddingRequestDto @JsonbCreator constructor(
    @field:Name("postId")
    @get:Name("postId")
    val postId: Long? = null,

    @field:Name("offers")
    val offers: List<Long>? = mutableListOf(),

    @field:Name("customerId")
    val customerId: Long,

    @field:Name("weddingType")
    val weddingType: WeddingType,

    @field:Name("eventDate")
    val eventDate: Date,

    @field:Name("location")
    val location: LocationDto,

    @field:Name("serviceNeeded")
    val serviceNeeded: String,

    @field:Name("startBudgetRange")
    val startBudgetRange: Float? = null,

    @field:Name("endBudgetRange")
    val endBudgetRange: Float? = null,

    @field:Name("description")
    val description: String,

    @field:Name("inspirationPhotos")
    val inspirationPhotos: String? = null
)

enum class WeddingType {
    WHITE_WEDDING, TRADITIONAL, DESTINATION
}

@Input("OfferDtoInput")
data class OfferDto @JsonbCreator constructor(
    @field:Name("offerId")
    val offerId: Long? = null,
    @field:Name("weddingRequest")
    val weddingRequest: Long,
    @field:Name("vendorId")
    val vendorId: String,
    @field:Name("offerDescription")
    val offerDescription: String,
    @field:Name("amount")
    val amount: Float
)
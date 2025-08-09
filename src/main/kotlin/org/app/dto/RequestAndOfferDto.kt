package org.app.dto

import jakarta.json.bind.annotation.JsonbCreator
import org.eclipse.microprofile.graphql.Input
import org.eclipse.microprofile.graphql.Name
import java.util.*

@Input("WeddingRequestInput")
data class WeddingRequestDto @JsonbCreator constructor(
    @field:Name("postId")
    @get:Name("postId")
    val postId: Long? = null,

    @field:Name("offerId")
    val offerId: List<UUID>? = null,

    @field:Name("username")
    val username: String? = null,

    @field:Name("weddingType")
    val weddingType: WeddingType,

    @field:Name("eventDate")
    val eventDate: Date,

    @field:Name("location")
    val location: String? = null,

    @field:Name("serviceNeeded")
    val serviceNeeded: String,

    @field:Name("budgetRange")
    val budgetRange: Float? = null,

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
    val offerId: Long,
    @field:Name("postId")
    val postId: UUID,
    @field:Name("username")
    val username: String,
    @field:Name("offerDescription")
    val offerDescription: String,
    @field:Name("amount")
    val amount: Float
)
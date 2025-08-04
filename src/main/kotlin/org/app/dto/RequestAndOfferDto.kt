package org.app.dto

import jakarta.json.bind.annotation.JsonbCreator
import jakarta.json.bind.annotation.JsonbProperty
import org.app.Entity.OfferEntity
import org.app.Entity.WeddingRequestEntity
import org.eclipse.microprofile.graphql.Input
import org.eclipse.microprofile.graphql.Name
import java.util.*

@Input("WeddingRequestInput")
data class WeddingRequestDto @JsonbCreator constructor(
    @field:Name("postId")
    @get:Name("postId")
    val postId: UUID? = null,

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
) {
    fun convertToEntity(): WeddingRequestEntity {
        return WeddingRequestEntity().apply {
            username = this.username
            weddingType = this.weddingType
            eventDate = this.eventDate
            location = this.location
            serviceNeeded = this.serviceNeeded
            startBudgetRange = this.startBudgetRange
            endBudgetRange = this.endBudgetRange
            description = this.description
            inspirationPhotos = this.inspirationPhotos
//            offers = offerId //TODO: how do i map it???
        }
    }
}

enum class WeddingType {
    WHITE_WEDDING, TRADITIONAL, DESTINATION
}

@Input("OfferDtoInput")
data class OfferDto @JsonbCreator constructor(
    @field:Name("offerId")
    val offerId: UUID,
    @field:Name("postId")
    val postId: UUID,
    @field:Name("username")
    val username: String,
    @field:Name("offerDescription")
    val offerDescription: String,
    @field:Name("amount")
    val amount: Float
) : WeddingDetails {
    fun convertToEntity(): OfferEntity {
        return OfferEntity().apply {
            offerId = this.offerId
            offerDescription = this.offerDescription
            amount = this.amount
            username = this.username
        }
    }
}
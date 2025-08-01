package org.app.dto

import jakarta.json.bind.annotation.JsonbCreator
import jakarta.json.bind.annotation.JsonbProperty
import org.app.Entity.OfferEntity
import org.app.Entity.WeddingRequestEntity
import java.awt.Image
import java.util.*

data class WeddingRequestDto @JsonbCreator constructor(
    @JsonbProperty("postId")
    val postId: UUID?= null,
    @JsonbProperty("offerId")
    val offerId: List<UUID>?=null,
    @JsonbProperty("username")
    val username: String?=null,
    @JsonbProperty("weddingType")
    val weddingType: WeddingType ?=null, //TODO: maybe change to enum for the types of wedding supported
    @JsonbProperty("eventDate")
    val eventDate: Date?=null,
    @JsonbProperty("location")
    val location: String?=null,
    @JsonbProperty("serviceNeeded")
    val serviceNeeded: String?=null,
    @JsonbProperty("budgetRange")
    val budgetRange: Float?=null,
    @JsonbProperty("description")
    val description: String?=null,
    @JsonbProperty("inspirationPhotos")
    val inspirationPhotos: Image?=null
): WeddingDetails{
    fun convertToEntity(): WeddingRequestEntity {
        return WeddingRequestEntity().apply {
            username = this.username
            weddingType = this.weddingType
            eventDate = this.eventDate
            location = this.location
            serviceNeeded = this.serviceNeeded
            budgetRange = this.budgetRange
            description = this.description
            inspirationPhotos = this.inspirationPhotos
//            offers = offerId //TODO: how do i map it???
        }
    }
}
enum class WeddingType{
    WHITE_WEDDING, TRADITIONAL, DESTINATION
}

data class OfferDto @JsonbCreator constructor(
    @JsonbProperty("offerId")
    val offerId: UUID,
    @JsonbProperty("postId")
    val postId: UUID,
    @JsonbProperty("username")
    val username: String,
    @JsonbProperty("offerDescription")
    val offerDescription: String,
    @JsonbProperty("amount")
    val amount: Float
): WeddingDetails {
    fun convertToEntity(): OfferEntity {
        return OfferEntity().apply {
            offerId = this.offerId
            offerDescription = this.offerDescription
            amount = this.amount
            username = this.username
        }
    }
}
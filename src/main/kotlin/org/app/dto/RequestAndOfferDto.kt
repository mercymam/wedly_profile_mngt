package org.app.dto

import org.app.Entity.OfferEntity
import org.app.Entity.WeddingRequestEntity
import java.awt.Image
import java.util.*

data class WeddingRequestDto (
    val postId: UUID?=null,
    val offerId: List<UUID>?=null,
    val username: String?=null,
    val weddingType: String?=null, //TODO: maybe change to enum for the types of wedding supported
    val eventDate: Date?=null,
    val location: String?=null,
    val serviceNeeded: String?=null,
    val budgetRange: Float?=null,
    val description: String?=null,
    val inspirationPhotos: Image?=null
){
    fun convertToEntity(): WeddingRequestEntity {
        return WeddingRequestEntity().apply {
            postId = this.postId
            username = this.username
            weddingType = this.weddingType
            eventDate = this.eventDate
            location = this.location
            serviceNeeded = this.serviceNeeded
            budgetRange = this.budgetRange
            description = this.description
            inspirationPhotos = this.inspirationPhotos
            offers = offers.toMutableList()
        }
    }
}

data class OfferDto(
    val offerId: UUID,
    val postId: UUID,
    val username: String,
    val offerDescription: String,
    val amount: Float

){
    fun convertToEntity(): OfferEntity {
        return OfferEntity().apply {
            offerId = this.offerId
            offerDescription = this.offerDescription
            amount = this.amount
            username = this.username
        }
    }
}
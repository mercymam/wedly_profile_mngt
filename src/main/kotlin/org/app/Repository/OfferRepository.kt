package org.app.Repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.app.Entity.OfferEntity
import java.util.UUID

@ApplicationScoped
class OfferRepository: PanacheRepository<OfferEntity> {

    @Transactional
    suspend fun createOffer(offer: OfferEntity): Long? {
        persist(offer)
        return offer.offerId
    }

    suspend fun updateOffer(offer: OfferEntity): Long? {
        var existingOfferDetails = offer.offerId?.let { findById(it) } ?: throw Exception("Offer details does not exist for offerId: ${offer.offerId} and vendorId: ${offer.vendorId}")
        existingOfferDetails = updateRecords(existingOfferDetails, offer)
        persist(existingOfferDetails)
        return existingOfferDetails.offerId
    }

    fun updateRecords(existingOffer: OfferEntity, newOffer: OfferEntity): OfferEntity{
        existingOffer.offerDescription = newOffer.offerDescription
        existingOffer.amount = newOffer.amount
        return existingOffer
    }
}
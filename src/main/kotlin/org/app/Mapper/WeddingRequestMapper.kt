package org.app.Mapper

import org.app.Entity.CustomerPersonalDetails
import org.app.Entity.OfferEntity
import org.app.Entity.WeddingRequestEntity
import org.app.dto.WeddingRequestDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "cdi")
abstract class WeddingRequestMapper {

    // --- Map from Entity to DTO ---
    @Mapping(target = "offers", source = "offers", qualifiedByName = ["mapOfferListToIdList"])
    @Mapping(target = "username", source = "username", qualifiedByName = ["mapCustomerToUsername"])
    abstract fun toDto(weddingRequestEntity: WeddingRequestEntity): WeddingRequestDto

    // --- Map from DTO to Entity ---
    @Mapping(target = "offers", source = "offers", qualifiedByName = ["mapIdListToOfferList"])
    @Mapping(target = "username", source = "username", qualifiedByName = ["mapUsernameToUser"])
    abstract fun toEntity(weddingRequestDto: WeddingRequestDto): WeddingRequestEntity

    // ---- LIST MAPPERS ----
    @Named("mapIdListToOfferList")
    open fun mapIdListToOfferList(offerIds: List<Long>?): List<OfferEntity> {
        return offerIds?.map { mapOfferIdToOffer(it) } ?: emptyList()
    }

    @Named("mapOfferListToIdList")
    open fun mapOfferListToIdList(offers: List<OfferEntity>?): List<Long?> {
        return offers?.map { mapOfferToId(it) } ?: emptyList()
    }

    // ---- ELEMENT MAPPERS ----
    @Named("mapOfferIdToOffer")
    open fun mapOfferIdToOffer(id: Long?): OfferEntity {
        val entity = OfferEntity()
        entity.offerId = id
        return entity
    }

    @Named("mapOfferToId")
    open fun mapOfferToId(offer: OfferEntity?): Long? {
        return offer?.offerId
    }

    // ---- USERNAME MAPPERS ----
    @Named("mapUsernameToUser")
    open fun mapUsernameToUser(username: String?): CustomerPersonalDetails {
        val customerPersonalDetails = CustomerPersonalDetails()
        if (username != null) {
            customerPersonalDetails.username = username
        }
        return customerPersonalDetails
    }

    @Named("mapCustomerToUsername")
    open fun mapCustomerToUsername(customer: CustomerPersonalDetails?): String? {
        return customer?.username
    }
}

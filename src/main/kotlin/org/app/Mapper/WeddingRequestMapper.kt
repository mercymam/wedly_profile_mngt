package org.app.Mapper

import org.app.Entity.CustomerPersonalDetails
import org.app.Entity.Location
import org.app.Entity.OfferEntity
import org.app.Entity.WeddingRequestEntity
import org.app.dto.LocationDto
import org.app.dto.WeddingRequestDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "cdi")
abstract class WeddingRequestMapper {

    @Mapping(target = "offers", source = "offers", qualifiedByName = ["mapOfferListToIdList"])
    @Mapping(target = "location", source = "location", qualifiedByName = ["mapLocationToDto"])
    @Mapping(target = "customerId", source = "customerId", qualifiedByName = ["mapCustomerToId"])
    abstract fun toDto(weddingRequestEntity: WeddingRequestEntity): WeddingRequestDto

    @Mapping(target = "offers", source = "offers", qualifiedByName = ["mapIdListToOfferList"])
    @Mapping(target = "location", source = "location", qualifiedByName = ["mapLocationToEntity"])
    @Mapping(target = "customerId", source = "customerId", qualifiedByName = ["mapIdToCustomer"])
    abstract fun toEntity(weddingRequestDto: WeddingRequestDto): WeddingRequestEntity

    @Named("mapIdListToOfferList")
    open fun mapIdListToOfferList(offerIds: List<Long>?): List<OfferEntity> {
        return offerIds?.map { mapOfferIdToOffer(it) } ?: emptyList()
    }

    @Named("mapOfferListToIdList")
    open fun mapOfferListToIdList(offers: List<OfferEntity>?): List<Long?> {
        return offers?.map { mapOfferToId(it) } ?: emptyList()
    }

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

    @Named("mapIdToCustomer")
    open fun mapIdToCustomer(id: Long?): CustomerPersonalDetails {
        val customerPersonalDetails = CustomerPersonalDetails()
        if (id != null) {
            customerPersonalDetails.id = id
        }
        return customerPersonalDetails
    }

    @Named("mapCustomerToId")
    open fun mapCustomerToId(customer: CustomerPersonalDetails?): Long? {
        return customer?.id
    }

    @Named("mapLocationToDto")
    open fun mapLocationToDto(location: Location?): LocationDto? =
        location?.let {
            LocationDto(
                postcode = location.postcode,
                city = it.city,
                country = location.country,
                addressLine1 = location.addressLine1,
                addressLine2 = location.addressLine2,
            )
        }

    @Named("mapLocationToEntity")
    open fun mapLocationToEntity(locationDto: LocationDto?): Location {
        val location = Location()
        if(locationDto != null) {
            location.apply {
                postcode = locationDto.postcode
                city = locationDto.city
                country = locationDto.country
                addressLine1 = locationDto.addressLine1
                addressLine2 = locationDto.addressLine2
            }
        }
        return location

    }
}

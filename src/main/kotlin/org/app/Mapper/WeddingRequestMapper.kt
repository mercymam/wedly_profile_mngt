package org.app.Mapper

import jakarta.inject.Inject
import org.app.Entity.CustomerPersonalDetails
import org.app.Entity.Location
import org.app.Entity.OfferEntity
import org.app.Entity.WeddingRequestEntity
import org.app.Repository.CustomerRepository
import org.app.Repository.OfferRepository
import org.app.dto.LocationDto
import org.app.dto.WeddingRequestDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "cdi")
abstract class WeddingRequestMapper {

    @Inject
    lateinit var offerRepository: OfferRepository

    @Inject
    lateinit var customerRepository: CustomerRepository

    @Mapping(target = "offers", source = "offers", qualifiedByName = ["mapOfferListToIdList"])
    @Mapping(target = "location", source = "location", qualifiedByName = ["mapLocationToDto"])
    @Mapping(target = "customerId", source = "customerId", qualifiedByName = ["mapCustomerToId"])
    abstract fun toDto(weddingRequestEntity: WeddingRequestEntity): WeddingRequestDto

    @Mapping(target = "offers", source = "offers", qualifiedByName = ["mapOfferIdsToOffer"])
    @Mapping(target = "location", source = "location", qualifiedByName = ["mapLocationToEntity"])
    @Mapping(target = "customerId", source = "customerId", qualifiedByName = ["mapCustomerIdToCustomer"])
    abstract fun toEntity(weddingRequestDto: WeddingRequestDto): WeddingRequestEntity

    @Named("mapOfferIdsToOffer")
    open fun mapOfferIdsToOffer(offerIds: List<Long>?): List<OfferEntity> {
        return offerIds?.map { mapOfferIdToOffer(it) } ?: emptyList()
    }

    @Named("mapOfferListToIdList")
    open fun mapOfferListToIdList(offers: List<OfferEntity>?): List<Long?> {
        return offers?.map { mapOfferToId(it) } ?: emptyList()
    }

    @Named("mapOfferIdToOffer")
    open fun mapOfferIdToOffer(id: Long?): OfferEntity {
        return id?.let {offerRepository.findById(id)} ?: throw IllegalArgumentException("Offer id $id cannot be found when mapping request")
    }

    @Named("mapOfferToId")
    open fun mapOfferToId(offer: OfferEntity?): Long? {
        return offer?.offerId
    }

    @Named("mapCustomerIdToCustomer")
    open fun mapCustomerIdToCustomer(id: Long?): CustomerPersonalDetails {
        return id?.let {customerRepository.findById(id)} ?: throw IllegalArgumentException("Customer id $id cannot be found when mapping request")
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
        return locationDto?.let {
            Location().apply {
                postcode = locationDto.postcode
                city = locationDto.city
                country = locationDto.country
                addressLine1 = locationDto.addressLine1
                addressLine2 = locationDto.addressLine2
            }
        } ?: Location()

    }
}

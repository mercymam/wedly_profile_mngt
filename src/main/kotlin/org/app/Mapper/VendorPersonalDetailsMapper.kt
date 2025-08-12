package org.app.Mapper

import jakarta.enterprise.context.ApplicationScoped
import org.app.Entity.Location
import org.app.Entity.VendorPersonalDetails
import org.app.dto.LocationDto
import org.app.dto.VendorPersonalDetailsDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named


@Mapper(componentModel = "cdi")
abstract class VendorPersonalDetailsMapper {

    @Mapping(target = "location", source = "location", qualifiedByName = ["mapLocationToEntity"])
    abstract fun toEntity(vendorPersonalDetailsDto: VendorPersonalDetailsDto): VendorPersonalDetails

    @Mapping(target = "location", source = "location", qualifiedByName = ["mapLocationToDto"])
    abstract fun toDto(vendorPersonalDetails: VendorPersonalDetails): VendorPersonalDetailsDto

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
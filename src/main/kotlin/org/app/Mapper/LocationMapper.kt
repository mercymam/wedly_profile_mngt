package org.app.Mapper

import org.app.Entity.Location
import org.app.dto.LocationDto
import org.mapstruct.Mapper

@Mapper(componentModel = "cdi")
abstract class LocationMapper {

    abstract fun toDto(entity: Location): LocationDto
    abstract fun toEntity(dto: LocationDto): Location
}
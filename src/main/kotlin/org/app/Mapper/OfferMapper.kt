package org.app.Mapper

import org.app.Entity.OfferEntity
import org.app.dto.OfferDto
import org.mapstruct.Mapper

@Mapper(componentModel = "cdi")
interface OfferMapper {
    fun toEntity(dto: OfferDto): OfferEntity
    fun toDto(entity: OfferEntity): OfferDto
}

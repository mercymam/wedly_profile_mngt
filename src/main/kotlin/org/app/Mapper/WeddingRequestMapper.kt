package org.app.Mapper

import org.app.Entity.WeddingRequestEntity
import org.app.dto.WeddingRequestDto
import org.mapstruct.Mapper

@Mapper(componentModel = "cdi")
interface WeddingRequestMapper {
    fun toDto(weddingRequestEntity: WeddingRequestEntity ): WeddingRequestDto
    fun toEntity(weddingRequestDto: WeddingRequestDto) : WeddingRequestEntity
}
package org.app.Mapper

import jakarta.enterprise.context.ApplicationScoped
import org.app.Entity.VendorPersonalDetails
import org.app.dto.VendorPersonalDetailsDto
import org.mapstruct.Mapper


@Mapper(componentModel = "cdi")
interface VendorPersonalDetailsMapper {
    fun toEntity(vendorPersonalDetailsDto: VendorPersonalDetailsDto): VendorPersonalDetails
    fun toDto(vendorPersonalDetails: VendorPersonalDetails): VendorPersonalDetailsDto
}
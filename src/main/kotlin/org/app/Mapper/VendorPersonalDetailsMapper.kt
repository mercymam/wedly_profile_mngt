package org.app.Mapper

import org.app.Entity.VendorPersonalDetails
import org.app.dto.VendorPersonalDetailsDto

interface VendorPersonalDetailsMapper {
    fun toEntity(vendorPersonalDetailsDto: VendorPersonalDetailsDto): VendorPersonalDetails
    fun toDto(vendorPersonalDetails: VendorPersonalDetails): VendorPersonalDetailsDto
}
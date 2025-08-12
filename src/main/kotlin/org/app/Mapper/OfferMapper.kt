package org.app.Mapper

import org.app.Entity.OfferEntity
import org.app.Entity.VendorPersonalDetails
import org.app.Entity.WeddingRequestEntity
import org.app.dto.OfferDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "cdi")
abstract class OfferMapper {

    @Mapping(target = "weddingRequest", source = "weddingRequest", qualifiedByName = ["mapIdToWeddingRequest"])
    @Mapping(target = "username", source = "username", qualifiedByName = ["mapUsernameToVendor"])
    abstract fun toEntity(dto: OfferDto): OfferEntity

    @Mapping(target = "weddingRequest", source = "weddingRequest", qualifiedByName = ["mapRequestToId"])
    @Mapping(target = "username", source = "username", qualifiedByName = ["mapVendorToUsername"])
    abstract fun toDto(entity: OfferEntity): OfferDto

    @Named("mapIdToWeddingRequest")
    open fun mapIdToWeddingRequest(id: Long?): WeddingRequestEntity {
        val entity = WeddingRequestEntity()
        entity.postId = id
        return entity
    }

    @Named("mapRequestToId")
    open fun mapRequestToId(request: WeddingRequestEntity?): Long? {
        return request?.postId
    }

    @Named("mapUsernameToVendor")
    open fun mapUsernameToVendor(username: String?): VendorPersonalDetails {
        val entity = VendorPersonalDetails()
        if (username != null) {
            entity.username = username
        }
        return entity
    }

    @Named("mapVendorToUsername")
    open fun mapVendorToUsername(vendor: VendorPersonalDetails?): String? {
        return vendor?.username
    }
}

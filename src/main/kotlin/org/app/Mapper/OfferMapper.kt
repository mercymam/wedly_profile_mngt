package org.app.Mapper

import jakarta.inject.Inject
import org.app.Entity.OfferEntity
import org.app.Entity.VendorPersonalDetails
import org.app.Entity.WeddingRequestEntity
import org.app.Repository.VendorPersonRepository
import org.app.Repository.WeddingRequestRepository
import org.app.dto.OfferDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "cdi")
abstract class OfferMapper {

    @Inject
    lateinit var vendorPersonRepository: VendorPersonRepository

    @Inject
    lateinit var weddingRequestRepository: WeddingRequestRepository

    @Mapping(target = "weddingRequest", source = "weddingRequest", qualifiedByName = ["mapRequestIdToWeddingRequest"])
    @Mapping(target = "vendorId", source = "vendorId", qualifiedByName = ["mapVendorIdToVendor"])
    abstract fun toEntity(dto: OfferDto): OfferEntity

    @Mapping(target = "weddingRequest", source = "weddingRequest", qualifiedByName = ["mapRequestToRequestId"])
    @Mapping(target = "vendorId", source = "vendorId", qualifiedByName = ["mapVendorToVendorId"])
    abstract fun toDto(entity: OfferEntity): OfferDto

    @Named("mapRequestIdToWeddingRequest")
    open fun mapRequestIdToWeddingRequest(id: Long?): WeddingRequestEntity {
        return id?.let {weddingRequestRepository.findById(id)} ?: throw IllegalArgumentException("request id $id not found when mapping offer")
    }

    @Named("mapRequestToRequestId")
    open fun mapRequestToRequestId(request: WeddingRequestEntity?): Long? {
        return request?.postId
    }

    @Named("mapVendorIdToVendor")
    open fun mapVendorIdToVendor(vendorId: Long?): VendorPersonalDetails {
        return vendorId?.let { vendorPersonRepository.findById(vendorId)} ?: throw IllegalArgumentException("vendor id $vendorId not found when mapping offer")
    }

    @Named("mapVendorToVendorId")
    open fun mapVendorToVendorId(vendor: VendorPersonalDetails?): Long? {
        return vendor?.vendorId
    }
}

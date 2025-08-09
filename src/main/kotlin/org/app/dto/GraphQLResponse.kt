package org.app.dto

import org.eclipse.microprofile.graphql.Interface
import java.util.*

@Interface
interface WeddingDetails {}

data class GraphQLResponse (
    val id: Long? =null,
    val message: String? =null,
    val status: Status
)

data class VendorListResponse (
    val id: Long? =null,
    val message: String? =null,
    val status: Status,
    val weddingDetails: List<VendorPersonalDetailsDto>? = null
)

data class OfferResponse (
    val id: Long? =null,
    val message: String? =null,
    val status: Status,
    val weddingDetails: OfferDto? = null
)

data class WeddingRequestResponse (
    val id: UUID? =null,
    val message: String? =null,
    val status: Status,
    val weddingDetails: WeddingRequestDto? = null
)

data class WeddingRequestListResponse (
    val id: UUID? =null,
    val message: String? =null,
    val status: Status,
    val weddingDetails: List<WeddingRequestDto>? = null
)

enum class Status {
    OK,
    NO_CONTENT,
    INTERNAL_SERVER_ERROR,
    ERROR,
    UNAUTHORIZED,
    NOT_FOUND
}
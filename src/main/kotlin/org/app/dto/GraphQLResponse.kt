package org.app.dto

import java.util.*

interface WeddingDetails {}

data class GraphQLResponse<T> (
    val id: UUID? =null,
    val message: String? =null,
    val status: Status,
    val weddingDetails: T? = null
)

enum class Status {
    OK,
    NO_CONTENT,
    INTERNAL_SERVER_ERROR,
    ERROR,
    UNAUTHORIZED,
    NOT_FOUND
}
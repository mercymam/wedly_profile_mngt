package org.app.dto

import jakarta.ws.rs.core.Response
import java.util.*

interface WeddingDetails {}

data class GraphQLResponse (
    val id: UUID? =null,
    val message: String? =null,
    val status: Response.Status,
    val weddingDetails: WeddingDetails? = null
)
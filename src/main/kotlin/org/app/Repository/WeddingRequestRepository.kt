package org.app.Repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.app.Entity.WeddingRequestEntity
import java.util.*

@ApplicationScoped
class WeddingRequestRepository: PanacheRepository<WeddingRequestEntity> {

    suspend fun findById(id: UUID): WeddingRequestEntity? {
        return find("postId = ?1", id).firstResult()
    }

    suspend fun createRequest(request: WeddingRequestEntity): UUID {
        persist(request)
        return request.postId!!
    }

    suspend fun updateRequest(request: WeddingRequestEntity): UUID {
        var existingWeddingRequest = request.postId?.let { findById(it) } ?: throw Exception("Vendor details does not exist for vendorId: ${request.postId} and username: ${request.username}")
        existingWeddingRequest = updateRecords(existingWeddingRequest, request)
        persist(existingWeddingRequest)
        return existingWeddingRequest.postId!!
    }

    fun updateRecords(existingRequest: WeddingRequestEntity, newRequest: WeddingRequestEntity): WeddingRequestEntity {
        existingRequest.weddingType = newRequest.weddingType
        existingRequest.username = newRequest.username
        existingRequest.eventDate = newRequest.eventDate
        existingRequest.location = newRequest.location
        existingRequest.serviceNeeded = newRequest.serviceNeeded
        existingRequest.budgetRange = newRequest.budgetRange
        existingRequest.description = newRequest.description
        existingRequest.inspirationPhotos = newRequest.inspirationPhotos
        return existingRequest
    }
}
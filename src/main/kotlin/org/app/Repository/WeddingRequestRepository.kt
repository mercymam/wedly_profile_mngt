package org.app.Repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.app.Entity.WeddingRequestEntity
import org.app.dto.WeddingType
import java.util.*
import kotlin.collections.List

@ApplicationScoped
class WeddingRequestRepository : PanacheRepository<WeddingRequestEntity> {

    suspend fun findById(id: UUID): WeddingRequestEntity? {
        return find("postId = ?1", id).firstResult()
    }

    suspend fun findByUsername(username: String, offset: Int, limit: Int): List<WeddingRequestEntity> {
        return find("username = ?1", username).range(offset, offset + limit - 1).list()
    }

    suspend fun filterRequest(
        startDate: Date? = null,
        endDate: Date? = null,
        location: String? = null,
        weddingType: WeddingType? = null,
        serviceNeeded: String,
        startBudgetRange: Float,
        endBudgetRange: Float,
        offset: Int,
        limit: Int,
    ): List<WeddingRequestEntity> {
        val query = StringBuilder("1 = 1")
        val parameter = mutableMapOf<String, Any>()
        appendToQueryWhenGreater(query,parameter, "eventDate", startDate)
        appendToQueryWhenLesser(query,parameter, "eventDate", endDate)
        appendToQueryWhenEqual(query,parameter, "location", location)
        appendToQueryWhenEqual(query,parameter, "weddingType", weddingType)
        appendToQueryWhenEqual(query,parameter, "serviceNeeded", serviceNeeded)
        appendToQueryWhenGreater(query,parameter, "startBudgetRange", startBudgetRange)
        appendToQueryWhenLesser(query,parameter, "endBudgetRange", endBudgetRange)
        return find(query.toString(), parameter).range(offset, offset + limit - 1).list()
    }

    suspend fun createRequest(request: WeddingRequestEntity): UUID {
        persist(request)
        return request.postId!!
    }

    suspend fun updateRequest(request: WeddingRequestEntity): UUID {
        var existingWeddingRequest = request.postId?.let { findById(it) }
            ?: throw Exception("Vendor details does not exist for vendorId: ${request.postId} and username: ${request.username}")
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
        existingRequest.startBudgetRange = newRequest.startBudgetRange
        existingRequest.endBudgetRange = newRequest.endBudgetRange
        existingRequest.description = newRequest.description
        existingRequest.inspirationPhotos = newRequest.inspirationPhotos
        return existingRequest
    }

    fun appendToQueryWhenEqual(query: StringBuilder, parameter: MutableMap<String, Any>, columnName:String, value: Any? ) {
        if(value==null) return
        query.append(" AND $columnName = :$columnName")
        parameter[columnName] = value
    }

    fun appendToQueryWhenLesser(query: StringBuilder, parameter: MutableMap<String, Any>, columnName:String, value: Any?) {
        if(value==null) return
        query.append(" AND $columnName <= :$columnName")
        parameter[columnName] = value
    }

    fun appendToQueryWhenGreater(query: StringBuilder, parameter: MutableMap<String, Any>, columnName:String, value: Any?) {
        if(value==null) return
        query.append(" AND $columnName >= :$columnName")
        parameter[columnName] = value
    }
}
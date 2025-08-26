package org.app.Repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.app.Entity.WeddingRequestEntity
import org.app.Utility.DateUtility
import org.app.dto.WeddingType
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.List

@ApplicationScoped
class WeddingRequestRepository : PanacheRepository<WeddingRequestEntity>{

    @Inject
    lateinit var dateUtility: DateUtility

    suspend fun findById(id: UUID): WeddingRequestEntity? {
        return find("postId = ?1", id).firstResult()
    }

    suspend fun findByUsername(username: String, offset: Int, limit: Int): List<WeddingRequestEntity> {
        return find("customerId.username = ?1", username).range(offset, offset + limit - 1).list()
    }

    suspend fun filterRequest(
        startDate: Date? = null,
        endDate: Date? = null,
        city: String? = null,
        weddingType: WeddingType? = null,
        serviceNeeded: String,
        startBudgetRange: Float? = 0.00F,
        endBudgetRange: Float? = Float.MAX_VALUE,
        offset: Int,
        limit: Int,
    ): List<WeddingRequestEntity> {
        val query = StringBuilder("1 = 1")
        val parameter = mutableMapOf<String, Any>()
        appendToQueryWhenGreaterOrEqualsTo(query,parameter, "eventDate", dateUtility.convertToUTC(startDate), "startEventDate")
        appendToQueryWhenLesserOrEqualsTo(query,parameter, "eventDate", dateUtility.convertToUTC(endDate), "endEventDate")
        appendToQueryWhenEqual(query,parameter, "location.city", city, "city")
        appendToQueryWhenEqual(query,parameter, "weddingType", weddingType)
        appendToQueryWhenEqual(query,parameter, "serviceNeeded", serviceNeeded)
        appendToQueryWhenGreaterOrEqualsTo(query,parameter, "startBudgetRange", startBudgetRange)
        appendToQueryWhenLesserOrEqualsTo(query,parameter, "endBudgetRange", endBudgetRange)
        println("Query is $query and parameter is $parameter")
        return find(query.toString(), parameter).range(offset, offset + limit - 1).list()
    }

    suspend fun createRequest(request: WeddingRequestEntity): Long? {
        persist(request)
        return request.postId
    }

    suspend fun updateRequest(request: WeddingRequestEntity): Long? {
        var existingWeddingRequest = request.postId?.let { findById(it) }
            ?: throw Exception("Vendor details does not exist for postId: ${request.postId} and username: ${request.customerId}")
        existingWeddingRequest = updateRecords(existingWeddingRequest, request)
        persist(existingWeddingRequest)
        return existingWeddingRequest.postId
    }

    fun updateRecords(existingRequest: WeddingRequestEntity, newRequest: WeddingRequestEntity): WeddingRequestEntity {
        existingRequest.weddingType = newRequest.weddingType
        existingRequest.customerId = newRequest.customerId
        existingRequest.eventDate = newRequest.eventDate
        existingRequest.location = newRequest.location
        existingRequest.serviceNeeded = newRequest.serviceNeeded
        existingRequest.startBudgetRange = newRequest.startBudgetRange
        existingRequest.endBudgetRange = newRequest.endBudgetRange
        existingRequest.description = newRequest.description
        existingRequest.inspirationPhotos = newRequest.inspirationPhotos
        return existingRequest
    }

    fun appendToQueryWhenEqual(query: StringBuilder, parameter: MutableMap<String, Any>, columnName:String, value: Any?, parameterName: String?=null ) {
        if(value==null) return
        if(parameterName != null) {
            query.append(" AND $columnName = :$parameterName")
            parameter[parameterName] = value
            return
        }
        query.append(" AND $columnName = :$columnName")
        parameter[columnName] = value
    }

    fun appendToQueryWhenLesserOrEqualsTo(query: StringBuilder, parameter: MutableMap<String, Any>, columnName:String, value: Any?, parameterName: String?=null) {
        if(value==null) return
        if(parameterName != null) {
            query.append(" AND $columnName <= :$parameterName")
            parameter[parameterName] = value
            return
        }

        query.append(" AND $columnName <= :$columnName")
        parameter[columnName] = value
    }

    fun appendToQueryWhenGreaterOrEqualsTo(query: StringBuilder, parameter: MutableMap<String, Any>, columnName:String, value: Any?, parameterName: String?=null) {
        if(value==null) return
        if(parameterName != null) {
            query.append(" AND $columnName >= :$parameterName")
            parameter[parameterName] = value
            return
        }
        query.append(" AND $columnName >= :$columnName")
        parameter[columnName] = value
    }
}
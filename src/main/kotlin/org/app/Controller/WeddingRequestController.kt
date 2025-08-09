package org.app.Controller

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.app.Repository.OfferRepository
import org.app.Repository.WeddingRequestRepository
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import org.apache.logging.log4j.Logger
import org.app.Mapper.OfferMapper
import org.app.Mapper.WeddingRequestMapper
import org.app.dto.*
import java.util.*

@GraphQLApi
class WeddingRequestController(
    private val weddingRequestRepository: WeddingRequestRepository,
    private val offerRepository: OfferRepository,
    private val offerMapper: OfferMapper,
    private val weddingRequestMapper: WeddingRequestMapper,
) {

    private val logger: Logger = LogManager.getLogger(WeddingRequestController::class.java)


    @Query("usersWeddingRequest")
    fun getUserWeddingRequests(
        username: String,
        offset: Int = 0,
        limit: Int = 10
    ): WeddingRequestListResponse {
        try {
            logger.info("Finding user: {} wedding request with limit: {}", username, limit)

            val weddingRequests = runBlocking { weddingRequestRepository.findByUsername(username, offset, limit) }
            val weddingRequestsDto = weddingRequests.map { weddingRequestMapper.toDto(it) }

            logger.info("Found wedding request by user {} of size {}", username, weddingRequests.size)
            return WeddingRequestListResponse(weddingDetails = weddingRequestsDto, status = Status.OK)
        } catch (e: Exception) {
            logger.error("Error occurred while getting wedding request with username: $username", e)
            return WeddingRequestListResponse(message = e.message, status = Status.INTERNAL_SERVER_ERROR)
        }
    }

    @Query("suggestedWeddingRequest")
    fun getSuggestedWeddingRequests(
        startDate: Date? = null,
        endDate: Date? = null,
        location: String? = null,
        weddingType: WeddingType? = null,
        serviceNeeded: String,
        startBudgetRange: Float,
        endBudgetRange: Float,
        offset: Int = 0,
        limit: Int = 50
    ): WeddingRequestListResponse {
        val filter = "startDate: {}, endDate: {}, location: {}, weddingType: {}, serviceNeeded: {}, startBudgetRange: {}, endBudgetRange: {}, offset: {} limit: {}"
        try {
            logger.info(
                "Suggesting wedding request with with $filter",
                startDate,
                endDate,
                location,
                weddingType,
                serviceNeeded,
                startBudgetRange,
                endBudgetRange,
                offset,
                limit
            )

            val weddingRequests = runBlocking {
                weddingRequestRepository.filterRequest(
                    startDate = startDate,
                    endDate = endDate,
                    location = location,
                    weddingType = weddingType,
                    serviceNeeded = serviceNeeded,
                    startBudgetRange = startBudgetRange,
                    endBudgetRange = endBudgetRange,
                    offset = offset,
                    limit = limit
                )
            }
            val weddingRequestsDto = weddingRequests.map { weddingRequestMapper.toDto(it) }

            logger.info("Found wedding of size {} for filter $filter",
                weddingRequestsDto.size,
                startDate,
                endDate,
                location,
                weddingType,
                serviceNeeded,
                startBudgetRange,
                endBudgetRange,
                offset,
                limit
            )
            return WeddingRequestListResponse(weddingDetails = weddingRequestsDto, status = Status.OK)
        } catch (e: Exception) {
            logger.error("Error occurred while getting wedding request with filter: $filter",
                startDate,
                endDate,
                location,
                weddingType,
                serviceNeeded,
                startBudgetRange,
                endBudgetRange,
                offset,
                limit,
                e)
            return WeddingRequestListResponse(message = e.message, status = Status.INTERNAL_SERVER_ERROR)
        }
    }


    @Query("getWeddingRequest")
    fun getWeddingRequest(id: UUID): WeddingRequestResponse {
        try {
            logger.info("Finding wedding request by Id: {}", id)

            val weddingRequest = runBlocking { weddingRequestRepository.findById(id) }

            weddingRequest?.let {
                logger.info(
                    "Found wedding request by Id: {}, user {} and service {}",
                    id,
                    weddingRequest.username,
                    weddingRequest.serviceNeeded
                )
                return WeddingRequestResponse(
                    weddingDetails = weddingRequestMapper.toDto(weddingRequest),
                    status = Status.OK
                )
            }

            logger.info("No wedding request found for id: {}", id)
            return WeddingRequestResponse(status = Status.NO_CONTENT)
        } catch (e: Exception) {
            logger.error("Error occurred while getting wedding request with id: $id", e)
            return WeddingRequestResponse(message = e.message, status = Status.INTERNAL_SERVER_ERROR)
        }
    }

    @Query("getWeddingOffer")
    fun getWeddingOffer(id: UUID): OfferResponse {
        try {
            logger.info("Finding wedding offer by Id: {}", id)
            val weddingOffer = runBlocking { offerRepository.findById(id) }

            weddingOffer?.let {
                logger.info(
                    "Found wedding offer by Id: {}, user {} and amount {}",
                    id,
                    weddingOffer.username,
                    weddingOffer.amount
                )
                return OfferResponse(
                    weddingDetails = offerMapper.toDto(weddingOffer),
                    status = Status.OK,
                )
            }

            logger.info("No wedding offer found for id: {}", id)
            return OfferResponse(status = Status.NO_CONTENT)
        } catch (e: Exception) {
            return OfferResponse(message = e.message, status = Status.INTERNAL_SERVER_ERROR)
        }
    }

    @Mutation("createWeddingRequest")
    fun postWeddingRequest(request: WeddingRequestDto): GraphQLResponse {
        try {
            logger.info("Creating new wedding request by Id: ${request.postId}")
            val entity = weddingRequestMapper.toEntity(request)

            val weddingRequestId = runBlocking { weddingRequestRepository.createRequest(entity) }
            val successMessage =
                "Successfully created wedding request for user: ${request.username}, service: ${request.serviceNeeded} with id: $weddingRequestId"
            logger.info(successMessage)

            return GraphQLResponse(
                id = weddingRequestId,
                message = successMessage,
                status = Status.OK
            )
        } catch (e: Exception) {
            return GraphQLResponse(
                message = e.toString(),
                status = Status.INTERNAL_SERVER_ERROR
            )
        }
    }

    @Mutation("createWeddingOffer")
    fun postWeddingOffer(weddingOffer: OfferDto): GraphQLResponse {
        try {
            val entity = offerMapper.toEntity(weddingOffer)
            logger.info("Creating new wedding offer by offerId: ${weddingOffer.offerId} for user: ${weddingOffer.username} and postId: ${weddingOffer.postId}")

            val offerId = runBlocking { offerRepository.createOffer(entity) }
            val successMessage =
                "Successfully posted wedding offer for user ${weddingOffer.username} and amount ${weddingOffer.amount} for postId ${weddingOffer.postId}"
            logger.info(successMessage)

            return GraphQLResponse(
                id = offerId,
                message = successMessage,
                status = Status.OK
            )
        } catch (e: Exception) {
            return GraphQLResponse(
                message = e.message.toString(),
                status = Status.INTERNAL_SERVER_ERROR
            )
        }
    }

}
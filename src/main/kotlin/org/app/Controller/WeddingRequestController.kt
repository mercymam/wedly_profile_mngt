package org.app.Controller

import jakarta.ws.rs.core.Response.Status
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.app.Repository.OfferRepository
import org.app.Repository.WeddingRequestRepository
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import java.util.UUID
import org.apache.logging.log4j.Logger
import org.app.Mapper.OfferMapper
import org.app.Mapper.WeddingRequestMapper
import org.app.dto.*

@GraphQLApi
class WeddingRequestController(
    private val weddingRequestRepository: WeddingRequestRepository,
    private val offerRepository: OfferRepository,
    private val offerMapper: OfferMapper,
    private val weddingRequestMapper: WeddingRequestMapper,
) {

    //TODO: get wedding request by username for client profile with limit
    // TODO: get wedding request random with limit
    // TODO: get wedding request with filters

    private val logger: Logger = LogManager.getLogger(WeddingRequestController::class.java)

    @Query("request")
    fun getWeddingRequest(id: UUID): GraphQLResponse{
        try {
            logger.info("Finding wedding request by Id: {}", id)

            val weddingRequest = runBlocking { weddingRequestRepository.findById(id) }

            weddingRequest?.let {
                logger.info("Found wedding request by Id: {}, user {} and service {}", id, weddingRequest.username, weddingRequest.serviceNeeded)
                return GraphQLResponse(
                    weddingDetails = weddingRequestMapper.toDto(weddingRequest),
                    status = Status.OK
                )
            }

            logger.info("No wedding request found for id: {}", id)
            return GraphQLResponse(status = Status.NO_CONTENT)
        }catch (e:Exception){
            logger.error("Error occurred while getting wedding request with id: $id", e)
            return GraphQLResponse(message = e.message, status = Status.INTERNAL_SERVER_ERROR)
        }
    }

    @Query("offer")
    fun getWeddingOffer(id: UUID): GraphQLResponse{
        try {
            logger.info("Finding wedding offer by Id: {}", id)
            val weddingOffer = runBlocking { offerRepository.findById(id) }

            weddingOffer?.let {
                logger.info("Found wedding offer by Id: {}, user {} and amount {}", id, weddingOffer.username, weddingOffer.amount)
                return GraphQLResponse(
                     weddingDetails = offerMapper.toDto(weddingOffer),
                     status = Status.OK,
                 )
             }

            logger.info("No wedding offer found for id: {}", id)
            return GraphQLResponse(status = Status.NO_CONTENT)
        }catch (e:Exception){
            return GraphQLResponse(message = e.message, status = Status.INTERNAL_SERVER_ERROR)
        }
    }

    @Mutation("request")
    fun postWeddingRequest(request: WeddingRequestDto): GraphQLResponse{
        try {
            val entity = request.convertToEntity()
            logger.info("Creating new wedding request by Id: ${request.postId}")

            val weddingRequestId = runBlocking { weddingRequestRepository.createRequest(entity) }
            val successMessage = "Successfully created wedding request for user: ${request.username}, service: ${request.serviceNeeded} with id: $weddingRequestId"
            logger.info(successMessage)

            return GraphQLResponse(
                id = weddingRequestId,
                message = successMessage,
                status = Status.OK
            )
        }catch (e:Exception){
            return GraphQLResponse(
                message = e.toString(),
                status = Status.INTERNAL_SERVER_ERROR)
        }
    }

    @Mutation("offer")
    fun postWeddingOffer(weddingOffer: OfferDto): GraphQLResponse{
        try {
            val entity = weddingOffer.convertToEntity()
            logger.info("Creating new wedding offer by offerId: ${weddingOffer.offerId} for user: ${weddingOffer.username} and postId: ${weddingOffer.postId}")

            val offerId = runBlocking { offerRepository.createOffer(entity) }
            val successMessage = "Successfully posted wedding offer for user ${weddingOffer.username} and amount ${weddingOffer.amount} for postId ${weddingOffer.postId}"
            logger.info(successMessage)

            return GraphQLResponse(
                id = offerId,
                message = successMessage,
                status = Status.OK
            )
        }catch (e:Exception){
            return GraphQLResponse(
                message = e.message.toString(),
                status = Status.INTERNAL_SERVER_ERROR)
        }
    }

}
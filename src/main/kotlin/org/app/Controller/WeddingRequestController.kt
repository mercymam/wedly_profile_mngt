package org.app.Controller

import jakarta.ws.rs.core.Response
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.app.Repository.OfferRepository
import org.app.Repository.WeddingRequestRepository
import org.app.dto.OfferDto
import org.app.dto.WeddingRequestDto
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import java.util.UUID
import org.apache.logging.log4j.Logger

@GraphQLApi
class WeddingRequestController(
    private val weddingRequestRepository: WeddingRequestRepository,
    private val offerRepository: OfferRepository
) {

    //TODO: get wedding request by username for client profile with limit
    // TODO: get wedding request random with limit
    // TODO: get wedding request with filters

    private val logger: Logger = LogManager.getLogger(WeddingRequestController::class.java)

    @Query("request")
    fun getWeddingRequest(id: UUID): Response{
        try {
            logger.info("Finding wedding request by Id: $id")
            val weddingRequest = runBlocking { weddingRequestRepository.findById(id) }
            return Response.ok().entity(weddingRequest).build()
        }catch (e:Exception){
            return Response.serverError().entity(e.message).build()
        }
    }

    @Query("offer")
    fun getWeddingOffer(id: UUID): Response{
        try {
            logger.info("Finding wedding offer by Id: $id")
            val weddingOffer = runBlocking { offerRepository.findById(id) }
            return Response.ok().entity(weddingOffer).build()
        }catch (e:Exception){
            return Response.serverError().entity(e.message).build()
        }
    }

    @Mutation("request")
    fun postWeddingRequest(request: WeddingRequestDto): Response{
        try {
            //TODO: where do we generate the id
            logger.info("Creating wedding request by Id: ${request.postId}")
            val entity = request.convertToEntity()
            val weddingRequest = runBlocking { weddingRequestRepository.createRequest(entity) }
            return Response.ok().entity(weddingRequest).build()
        }catch (e:Exception){
            return Response.serverError().entity(e.message).build()
        }
    }

    @Mutation("offer")
    fun postWeddingOffer(weddingOffer: OfferDto): Response{
        try {
            logger.info("Finding wedding request by Id: ${weddingOffer.offerId}")
            val entity = weddingOffer.convertToEntity()
            val offer = runBlocking { offerRepository.createOffer(entity) }
            return Response.ok().entity(offer).build()
        }catch (e:Exception){
            return Response.serverError().entity(e.message).build()
        }
    }

}
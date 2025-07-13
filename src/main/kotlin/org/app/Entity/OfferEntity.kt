package org.app.Entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "offers")
class OfferEntity {


    @Id
    @GeneratedValue
    @Column(name = "offer_id")
    lateinit var offerId: UUID

    @ManyToOne
    @JoinColumn(name = "wedding_request_id", nullable = false)
    var weddingRequest: WeddingRequestEntity? = null

    @Column(name = "username", nullable = false)
    lateinit var username: String

    @Column(name = "offer_description", nullable = false)
    lateinit var offerDescription: String

    @Column(name = "amount", nullable = false)
    var amount: Double ?= null
}
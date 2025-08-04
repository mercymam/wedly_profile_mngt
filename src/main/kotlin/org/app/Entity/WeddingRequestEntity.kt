package org.app.Entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "request_details")
class WeddingRequestEntity: PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    var postId: UUID? = null

    @OneToMany(mappedBy = "weddingRequest", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var offers: MutableList<OfferEntity> = mutableListOf()

    @Column(name = "username", nullable = false)
    lateinit var username: String

    @Column(name = "wedding_type", nullable = false)
    lateinit var  weddingType: String

    @Column(name = "event_date", nullable = false)
    lateinit var  eventDate: Date

    @Column(name = "location", nullable = false)
    lateinit var  location: String

    @Column(name = "service_needed", nullable = false)
    lateinit var  serviceNeeded: String

    @Column(name = "start_budget_range", nullable = false)
    lateinit var  startBudgetRange: Integer

    @Column(name = "end_budget_range", nullable = false)
    lateinit var  endBudgetRange: Integer

    @Column(name = "description", nullable = false)
    lateinit var  description: String

    @Column(name = "inspiration_photos", nullable = false)
    lateinit var  inspirationPhotos: String
}
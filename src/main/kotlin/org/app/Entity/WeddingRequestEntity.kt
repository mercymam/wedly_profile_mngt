package org.app.Entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.app.dto.WeddingType
import java.util.*

@Entity
@Table(name = "request_details")
class WeddingRequestEntity: PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    var postId: Long? = null

    @OneToMany(mappedBy = "weddingRequest", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var offers: MutableList<OfferEntity> = mutableListOf()

    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    lateinit var customerId: CustomerPersonalDetails

    @Enumerated(EnumType.STRING)
    @Column(name = "wedding_type", nullable = false)
    lateinit var  weddingType: WeddingType

    @Column(name = "event_date", nullable = false)
    lateinit var  eventDate: Date

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    lateinit var  location: Location

    @Column(name = "service_needed", nullable = false)
    lateinit var  serviceNeeded: String

    @Column(name = "start_budget_range", nullable = false)
    var  startBudgetRange: Float?= null

    @Column(name = "end_budget_range", nullable = false)
    var  endBudgetRange: Float?= null

    @Column(name = "description", nullable = false)
    lateinit var  description: String

    @Column(name = "inspiration_photos", nullable = true)
    var  inspirationPhotos: String? = null
}
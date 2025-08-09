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

    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    lateinit var username: CustomerPersonalDetails

    @Column(name = "wedding_type", nullable = false)
    lateinit var  weddingType: WeddingType

    @Column(name = "event_date", nullable = false)
    lateinit var  eventDate: Date

    //TODO: change location to be its own type with its own table
    @Column(name = "location", nullable = false)
    lateinit var  location: String

    @Column(name = "service_needed", nullable = false)
    lateinit var  serviceNeeded: String

    @Column(name = "start_budget_range", nullable = false)
    var  startBudgetRange: Int?= null

    @Column(name = "end_budget_range", nullable = false)
    var  endBudgetRange: Int?= null

    @Column(name = "description", nullable = false)
    lateinit var  description: String

    @Column(name = "inspiration_photos", nullable = true)
    var  inspirationPhotos: String? = null
}
package org.app.Entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*

@Entity
@Table(name = "location")
class Location: PanacheEntity(){

    @Column(name = "postcode", nullable = true)
    var postcode: String? = null

    @Column(name = "city", nullable = false)
    lateinit var city: String

    @Column(name = "country", nullable = false)
    lateinit var country: String

    @Column(name = "address_line_1", nullable = false)
    var addressLine1: String? = null

    @Column(name = "address_line_2", nullable = true)
    var addressLine2: String? = null
}
package org.app.Repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.app.Entity.CustomerPersonalDetails

@ApplicationScoped
class CustomerRepository: PanacheRepository<CustomerPersonalDetails> {
}
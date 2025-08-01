package org.app.Utility

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.context.Dependent
import jakarta.enterprise.inject.spi.InjectionPoint
import jakarta.ws.rs.Produces
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
class LoggerProducer {

    @Produces
    fun produceLogger(injectionPoint: InjectionPoint): Logger {
        val declaringClass = injectionPoint.member.declaringClass
        return LoggerFactory.getLogger(declaringClass)
    }
}
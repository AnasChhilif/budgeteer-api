package com.api.budgeteer.core.aspects

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.slf4j.Logger;

@Aspect
@Component
class ControllerLogger {

    private val LOG: Logger = LoggerFactory.getLogger(ControllerLogger::class.java)

    @Pointcut("execution(public * com.api.budgeteer.features..controllers..*(..))")
    private fun allControllerMethods() {
    } // This enables to attach the pointcut to a method name we can reuse below


    @Before("allControllerMethods()")
    fun logMethodNameAndParametersAtEntry(joinPoint: JoinPoint) {
        LOG.info("Budgeteer:Rest-Controller: {}:Called {} {}", joinPoint.getThis(), joinPoint.signature.name, joinPoint.args)
    }

    @AfterReturning(pointcut = "allControllerMethods()", returning = "resultVal")
    fun logMethodReturningProperly(joinPoint: JoinPoint, resultVal: Any?) {
        LOG.info(
            "Budgeteer:Rest-Controller: {}:Returned {} with value {}",
            joinPoint.getThis(),
            joinPoint.signature.name,
            resultVal
        )
    }

    @AfterThrowing(pointcut = "allControllerMethods()", throwing = "exception")
    fun logMethodException(joinPoint: JoinPoint, exception: Exception) {
        LOG.warn(
            "Budgeteer:Rest-Controller: {}:Exception from {} with exception {}",
            joinPoint.getThis(),
            joinPoint.signature.name,
            exception.message
        )
    }

}
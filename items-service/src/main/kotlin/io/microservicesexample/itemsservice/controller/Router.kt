package io.microservicesexample.itemsservice.controller

import io.microservicesexample.itemsservice.service.ItemController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class Router {

    @Bean
    fun itemsRouter(controller: ItemController) = router {
        path("/items").nest {
            GET("/", controller::getAll)
            POST("/", controller::add)
            GET("/{id}", controller::getOne)
            PUT("/{id}", controller::update)
        }
    }
}

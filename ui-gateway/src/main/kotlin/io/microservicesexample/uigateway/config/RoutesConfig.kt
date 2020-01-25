package io.microservicesexample.uigateway.config

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoutesConfig {

//    @Bean
//    fun routes() = router {
//        GET("/login") { ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("login") }
//    }

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder) = builder.routes {
        route("/get"){
            path("/")
//            filters { addRequestHeader("Hello", "World") }
            uri("http://items-ui/items")
        }
        route("eureka-gui") {
            path("/eureka")
            filters {
                rewritePath("/eureka", "/")
            }
            uri("lb:http://eureka-server")
        }
        route("eureka-internals") {
            path("/eureka/**")
            uri("lb:http://eureka-server")
        }
    }
}

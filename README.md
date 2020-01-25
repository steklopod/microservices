# Microservices example ![Java CI](https://github.com/steklopod/microservices/workflows/TEST/badge.svg)

There are articles with more detailed explanation about this project: in 
[Russian](https://habr.com/post/431474/).

This is an example of microservice architecture pattern implemented with following stack of Java-technologies:
* JDK 13
* Kotlin
* Spring Cloud
  * Spring Cloud Gateway
  * Spring Cloud Config
  * Spring Cloud Sleuth
  * Spring Cloud OpenFeign
  * Spring Cloud Netflix
    * Eureka
    * Hystrix
    * Ribbon
* Gradle with Kotlin DSL
* JUnit
* Other: Thymeleaf, Bootstrap, Webjars

### Diagram of components
![-](/etc/images/diagram.png)

### Config server
Stores [configs](/config-server/src/main/resources/config) of all microservices.

### Service discovery server
Performs Service registry function. Spring Cloud Netflix Eureka is used which is [Client-side service discovery](https://microservices.io/patterns/client-side-discovery.html). 
Each application sends heartbeats to Eureka server after registration.

### Items service
This is an example of back-end with [REST API](/items-service/src/main/kotlin/io/microservicesexample/itemsservice/RestApi.kt) 
implemented with Spring WebFlux. HTTP requests are processed with 
[handler](/items-service/src/main/kotlin/io/microservicesexample/itemsservice/ItemHandler.kt). Also microservice demonstrates 
example of [sending additional metadata](/items-service/src/main/kotlin/io/microservicesexample/itemsservice/EurekaAdditionalMetadataReporter.kt).

### Items UI
This is an example of front-end which uses Thymeleaf for HTML-pages rendering and Bootstrap CSS framework (plugs in with 
help of Webjars). Application contains a few examples of interacting with back-end (Items service) by using different API:
* [using](items-ui/src/main/kotlin/io/microservicesexample/itemsui/service/ItemsServiceClient.kt) RestTemplate
* [using](items-ui/src/main/kotlin/io/microservicesexample/itemsui/service/ItemsServiceClient.kt) WebClient
* [using](items-ui/src/main/kotlin/io/microservicesexample/itemsui/service/ItemsServiceFeignClient.kt) FeignClient and 
Hystrix

You can test all of these approaches by request to `http://localhost:8081/example`.

You can test Hystrix fallback working by request to `http://localhost:8081/hystrix-fallback`.

### UI gateway
Performs authentication and routing to UI microservices (only Items UI in this project). Routing implemented with Spring 
Cloud Gateway which settings as well as other settings are stored in [YAML-config](microservices-example/config-server/src/main/resources/config/ui-gateway.yml). 
Also application contains [route to Eureka](ui-gateway/src/main/kotlin/io/microservicesexample/uigateway/config/RoutesConfig.kt) 
accessed by `https://localhost/eureka`. All routes you can see by request to `https://localhost/actuator/gateway/routes`. Access to 
login page [implements](ui-gateway/src/main/kotlin/io/microservicesexample/uigateway/config/RoutesConfig.kt) using WebFlux. 
You can perform authentication with one of [hardcoded](ui-gateway/src/main/kotlin/io/microservicesexample/uigateway/config/SecurityConfig.kt) 
users. Application passes user's login and roles by [adding](ui-gateway/src/main/kotlin/io/microservicesexample/uigateway/misc/AddCredentialsGlobalFilter.kt) 
it to HTTP request headers. You can request to `https://localhost/items-ui/greeting` to see passed credentials. Sure you 
can access all other endpoints provided by Items UI. As well as Items UI this application uses Thymeleaf and Bootstrap for UI purposes.

##### Distributed tracing
![-](/etc/images/sleuth_tracing.png)

This schema is simple, see [here](https://spring.io/projects/spring-cloud-sleuth) for details.


![-](/etc/images/run_dashboard.png)

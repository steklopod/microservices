package io.microservicesexample.eurekaserver

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.cloud.config.enabled:false",
        "eureka.client.register-with-eureka:false",
        "eureka.client.fetch-registry:false"
    ]
)
class EurekaServerApplicationTests {

    @Test
    fun contextLoads() {
    }

}

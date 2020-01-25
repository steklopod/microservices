package io.microservicesexample.itemsservice.web

import io.microservicesexample.itemsservice.model.Item
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import org.springframework.web.reactive.function.BodyInserters

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.cloud.config.enabled:false",
        "eureka.client.register-with-eureka:false",
        "eureka.client.fetch-registry:false"
    ]
)
internal class RouterTest(@Autowired private var webTestClient: WebTestClient) {

    @Test
    fun testGetAllItems() {
        webTestClient
            .get().uri("/items/")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList<Item>()
            .hasSize(3)
            .returnResult().apply {
                assertNotNull(responseBody)
            }
    }

    @Test
    fun testCreateItem() {
        webTestClient
            .post().uri("/items/")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(Item(null, "third")))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(Item::class.java)
            .returnResult().apply {
                assertNotNull(responseBody)
            }
    }

    @Test
    @Disabled
    fun testGetOneItems() {
        val responseBody = webTestClient
            .get().uri("/items/2")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(Item::class.java)
            .returnResult().responseBody
        assertNotNull(responseBody)

    }

    @Test
    @DirtiesContext
    fun testUpdateItem() {
        webTestClient
            .put().uri("/items/2")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(Item(2, "more than 2")))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(Item::class.java)
            .returnResult().apply {
                assertThat(
                    this.responseBody, equalTo(
                        Item(
                            2,
                            "more than 2"
                        )
                    )
                )
            }
    }
}

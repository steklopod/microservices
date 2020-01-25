package io.microservicesexample.itemsservice.service

import io.microservicesexample.itemsservice.config.exception.ApiServiceException
import io.microservicesexample.itemsservice.model.Item
import io.microservicesexample.itemsservice.repository.ItemRepository
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class ItemController(private val itemRepository: ItemRepository) {

    fun getAll(request: ServerRequest) = ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(fromValue(itemRepository.findAll()))

    fun getOne(request: ServerRequest) = ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(
            fromValue(
                itemRepository.findById(request.pathVariable("id").toLong())
                    ?: ApiServiceException("Request doesn't contain id")
            )
        )

    fun update(request: ServerRequest) = request.bodyToMono(Item::class.java).flatMap {
        if (it.id != request.pathVariable("id").toLong()) {
            throw ApiServiceException("Item.id not equals path variable id")
        }
        ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(fromValue(itemRepository.save(it)))
    }

    fun add(request: ServerRequest) = request.bodyToMono(Item::class.java).flatMap {
        ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(fromValue(itemRepository.save(it)))
    }
}

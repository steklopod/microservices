package io.microservicesexample.itemsui.service

import org.springframework.stereotype.Service

@Service
class ItemsServiceClient(
    private val feignClient: ItemsServiceFeignClient
)
{
    fun requestWithRestTemplate(id: Long): String = feignClient.getItem(id)

//    fun requestWithWebClient(id: Long): Mono<String> =
//        webClient.get().uri("http://items-service/items/$id").retrieve().bodyToMono(String::class.java)
}

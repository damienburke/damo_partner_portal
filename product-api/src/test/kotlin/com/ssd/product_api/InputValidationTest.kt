package com.ssd.product_api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.server.LocalServerPort
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.StandardCharsets

class InputValidationTest : BaseIT() {

    @LocalServerPort
    var port: Int = 0

    @Test
    fun `attempt to save invalid product input`() {

        val formParameters = listOf(
            "artist" to "Loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo Artist",
            "albumTitle" to "White Album",
            "releaseYear" to "1968",
            "price" to "19.99",
            "promoCode" to "XYZ"
        ).joinToString("&") { (key, value) ->
            "${URLEncoder.encode(key, StandardCharsets.UTF_8)}=${URLEncoder.encode(value, StandardCharsets.UTF_8)}"
        }

        val request = HttpRequest.newBuilder()
            .POST(
                BodyPublishers.ofString(
                    formParameters
                )
            )
            .uri(URI.create("$baseUrl:$port/add-product"))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()

        val response = httpClient.send(request, BodyHandlers.ofString())

        println("response.body() ${response.body()}")

        assertThat(response.statusCode()).isEqualTo(200)
        assertThat(response.body()).contains("Artist must be between 5 and 50 characters")
    }
}

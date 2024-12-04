package com.ssd.integrationTest

import com.ssd.persistance.entities.ProductEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.server.LocalServerPort
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

class EscapeHtmlIT : BaseIT() {

    @LocalServerPort
    var port: Int = 0

    @Test
    fun `load products`() {

        // Pre-condition: <script> tag saved in the dB
        productRepository.save(
            ProductEntity(
                releaseYear = 1999,
                promoCode = "xxx",
                albumTitle = "No Code",
                artist = "<script>alert('XSS Attack!');</script>"
            )
        )

        val request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("$baseUrl:$port/products"))
            .build()

        val response = httpClient.send(request, BodyHandlers.ofString())
        assertThat(response.statusCode()).isEqualTo(200)
        // Verify that the html response is escaped
        assertThat(response.body()).contains("<td>&lt;script&gt;alert(&#39;XSS Attack!&#39;);&lt;/script&gt;</td>")
    }
}

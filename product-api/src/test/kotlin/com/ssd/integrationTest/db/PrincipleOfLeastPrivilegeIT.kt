package com.ssd.integrationTest.db

import com.ssd.integrationTest.BaseIT
import com.ssd.persistance.entities.ProductEntity
import org.junit.jupiter.api.Test
import org.springframework.dao.InvalidDataAccessResourceUsageException
import java.math.BigDecimal
import kotlin.test.assertFailsWith

class PrincipleOfLeastPrivilegeIT : BaseIT() {

    /**
     * Select, Insert, and update allowed.
     * Delete not allowed
     */
    @Test
    fun `attempt all crud operations`() {

        productRepository.findAll()
        val createdRecord = productRepository.save(
            ProductEntity(
                artist = "James Brown",
                albumTitle = "Gold",
                releaseYear = 1997,
                price = BigDecimal.valueOf(30L)
            )
        )

        productRepository.save(createdRecord.copy(releaseYear = 1977))

        val x = productRepository.save(createdRecord.copy(artist = "<td><script>alert('XSS Attack!');</script></td>"))

        assertFailsWith<InvalidDataAccessResourceUsageException>(
            block = {
                productRepository.deleteAll()
            }
        )
    }
}
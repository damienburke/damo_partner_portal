package com.ssd.integrationTest.db

import com.ssd.integrationTest.BaseIT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SQLInjectionIT : BaseIT() {

    @Test
    fun `attempt to inject SQL - Native Query`() {

        // Pre-condition - this product exists
        val existingProducts = productRepository.findProductsByName("Beastie Boys")
        assertThat(existingProducts.size).isEqualTo(1)

        // Simulate a SQL injection attempt using malicious input
        assertThat(productRepository.findProductsByName(sqlInjectionAttempt).isEmpty()).isTrue

        // Post-condition - this product still exists
        // Validate the database integrity is preserved after the injection attempt
        val allProductsAfterAttempt = productRepository.findProductsByName("Beastie Boys")
        assertThat(allProductsAfterAttempt.size).isEqualTo(1)
        assertThat(allProductsAfterAttempt.first().id).isEqualTo(existingProducts.first().id)
    }

    @Test
    fun `attempt to inject SQL - Query Method`() {

        // Pre-condition - this product exists
        val existingProducts = productRepository.findByArtistContainingIgnoreCase("Beastie Boys")
        assertThat(existingProducts.size).isEqualTo(1)

        // Simulate a SQL injection attempt using malicious input
        assertThat(productRepository.findProductsByName(sqlInjectionAttempt).isEmpty()).isTrue

        // Post-condition - this product still exists
        // Validate the database integrity is preserved after the injection attempt
        val allProductsAfterAttempt = productRepository.findProductsByName("Beastie Boys")
        assertThat(allProductsAfterAttempt.size).isEqualTo(1)
        assertThat(allProductsAfterAttempt.first().id).isEqualTo(existingProducts.first().id)
    }
}

const val sqlInjectionAttempt = "laptop'; DROP TABLE product; --"
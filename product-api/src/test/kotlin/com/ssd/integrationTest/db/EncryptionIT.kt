package com.ssd.integrationTest.db

import com.ssd.integrationTest.BaseIT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EncryptionIT : BaseIT() {

    @Test
    fun `verify decryption`() {
        val existingProducts = productRepository.findProductsByName("Beastie Boys")
        assertThat(existingProducts.first().promoCode).isNotEqualTo("12345") // encrypted
        println("encrypted: ${existingProducts.first().promoCode}")

        val existingProduct = productRepository.findById(existingProducts.first().id)
        assertThat(existingProduct.get().promoCode).isEqualTo("12345")
    }
}

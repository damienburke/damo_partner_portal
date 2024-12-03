package com.ssd.persistance.entities

import com.ssd.extensions.kotlinEquals
import com.ssd.extensions.kotlinHashCode
import com.ssd.extensions.kotlinToString
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnTransformer
import java.math.BigDecimal

@Entity
@Table(name = "product")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @get:Size(max = 50, message = "Artist must be between 5 and 50 characters")
    @NotBlank
    val artist: String = "",

    @get:Size(max = 100, message = "Album title must be between 5 and 100 characters")
    @NotBlank
    val albumTitle: String = "",

    @Min(1900)
    @Positive
    val releaseYear: Int = 0,

    @Positive
    val price: BigDecimal = BigDecimal.ZERO,

    @ColumnTransformer(
        read = "pgp_sym_decrypt(" + "promo_code, " + "current_setting('encryption.key')" + ")",
        write = "pgp_sym_encrypt(" + "?::text," + "current_setting('encryption.key')" + ")"
    )
    @Column(columnDefinition = "bytea")
    @Size(max = 3, message = "Promo Code title not be longer than 3 characters")
    val promoCode: String = ""
) {

    companion object {
        private val properties = arrayOf(
            ProductEntity::id,
            ProductEntity::artist,
            ProductEntity::price,
            ProductEntity::albumTitle,
            ProductEntity::releaseYear,
            ProductEntity::promoCode
        )
    }

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = properties)

    override fun toString() = kotlinToString(properties = properties)

    override fun hashCode(): Int {
        return kotlinHashCode(properties = properties)
    }

}
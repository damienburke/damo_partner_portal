package com.ssd.web

import com.ssd.persistance.entities.ProductEntity
import com.ssd.service.ProductService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ProductController(val productService: ProductService) {

    @GetMapping("/products")
    fun showProducts(model: Model): String {
        model.addAttribute("products", productService.getProducts());
        return "products"
    }

    @GetMapping("/add-product")
    fun showAddProductForm(model: Model): String {
        model.addAttribute("productEntity", ProductEntity())
        return "add-product"
    }

    @PostMapping("/add-product")
    fun addProduct(@Valid product: ProductEntity, bindingResult: BindingResult): String {

        if (bindingResult.hasErrors()) {
            println("Model attributes: ${bindingResult.model}")
            return "add-product"
        }


        try {
            productService.addProduct(product)
        } catch (e: Exception) {
            return "add-product"
        }
        return "redirect:https://damoportal:6867/portal/products"
    }

    @GetMapping("/product/{id}")
    fun getProduct(@PathVariable id: Int, model: Model): String {
        val product = productService.findProductById(id)

        if (product.isEmpty)
            return "redirect:https://damoportal:6867/portal/products"
        else {
            model.addAttribute("product", product.get())
            return "product-detail"
        }
    }
}
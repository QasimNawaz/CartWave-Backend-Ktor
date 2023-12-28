package store.cartwave.service.product

import store.cartwave.models.AddProductParams
import store.cartwave.models.Categories
import store.cartwave.models.Product
import store.cartwave.models.ProductsByCategory

interface ProductService {
    suspend fun addProducts(params: List<AddProductParams>): String
    suspend fun addProduct(params: AddProductParams): String
    suspend fun getProduct(userId: Int, productId: Int): Product
    suspend fun getProductsWithPaging(userId: Int, pageNumber: Int, pageSize: Int): List<Product>
    suspend fun getProductsByCategory(
        userId: Int, category: String, pageNumber: Int, pageSize: Int
    ): List<Product>

    suspend fun getProductsGroupBySubCategory(userId: Int, category: String): List<ProductsByCategory>
    suspend fun getAllCategories(): List<Categories>
}
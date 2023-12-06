package store.cartwave.service.product

import store.cartwave.models.AddProductParams
import store.cartwave.models.Product

interface ProductService {
    suspend fun addProducts(params: List<AddProductParams>): String
    suspend fun addProduct(params: AddProductParams): String
    suspend fun getProductsWithPaging(pageNumber: Int, pageSize: Int): List<Product>
}
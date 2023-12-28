package store.cartwave.repository.product

import store.cartwave.models.AddProductParams
import store.cartwave.models.Product
import store.cartwave.utils.BaseResponse
import store.cartwave.utils.PagingBaseResponse

interface ProductRepository {
    suspend fun addProducts(params: List<AddProductParams>): BaseResponse<Any>
    suspend fun addProduct(params: AddProductParams): BaseResponse<Any>
    suspend fun getProduct(userId: Int, productId: Int): BaseResponse<Any>
    suspend fun getProductsWithPaging(userId: Int, pageNumber: Int, pageSize: Int): PagingBaseResponse<Any>
    suspend fun getProductsGroupBySubCategory(userId: Int, category: String): BaseResponse<Any>
    suspend fun getProductsByCategory(userId: Int, category: String,  pageNumber: Int, pageSize: Int): BaseResponse<Any>
}
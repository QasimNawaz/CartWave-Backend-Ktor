package store.cartwave.repository.product

import store.cartwave.models.AddProductParams
import store.cartwave.models.Product
import store.cartwave.utils.BaseResponse
import store.cartwave.utils.PagingBaseResponse

interface ProductRepository {
    suspend fun addProducts(params: List<AddProductParams>): BaseResponse<Any>
    suspend fun addProduct(params: AddProductParams): BaseResponse<Any>
    suspend fun getProductsWithPaging(pageNumber: Int, pageSize: Int): PagingBaseResponse<Any>
}
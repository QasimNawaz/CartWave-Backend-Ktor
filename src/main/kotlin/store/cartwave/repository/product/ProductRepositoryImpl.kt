package store.cartwave.repository.product

import org.jetbrains.exposed.sql.selectAll
import store.cartwave.config.*
import store.cartwave.db.DatabaseFactory.dbQuery
import store.cartwave.db.ProductTable
import store.cartwave.models.AddProductParams
import store.cartwave.service.product.ProductService
import store.cartwave.utils.BaseResponse
import store.cartwave.utils.PagingBaseResponse

class ProductRepositoryImpl(private val productService: ProductService) : ProductRepository {
    override suspend fun addProducts(params: List<AddProductParams>): BaseResponse<Any> {
        return if (params.isEmpty()) {
            BaseResponse.ErrorResponse(message = NO_DATA_TO_INSERT)
        } else {
            val response = productService.addProducts(params)
            if (response.isEmpty()) {
                BaseResponse.SuccessResponse(data = response, message = DATA_INSERTED)
            } else {
                BaseResponse.ErrorResponse(message = GENERIC_ERROR)
            }
        }
    }

    override suspend fun addProduct(params: AddProductParams): BaseResponse<Any> {
        val response = productService.addProduct(params)
        return if (response.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = response, message = DATA_INSERTED)
        } else {
            BaseResponse.ErrorResponse(message = GENERIC_ERROR)
        }
    }

    override suspend fun getProduct(userId: Int, productId: Int): BaseResponse<Any> {
        val response = productService.getProduct(userId, productId)
        return BaseResponse.SuccessResponse(data = response, message = SUCCESS)
    }

    override suspend fun getProductsWithPaging(userId: Int, pageNumber: Int, pageSize: Int): PagingBaseResponse<Any> {
        return if (pageNumber < 1) {
            PagingBaseResponse.ErrorResponse(message = PAGE_NUMBER_ERROR, pageNumber = pageNumber, pageSize = pageSize)
        } else if (pageSize < 5) {
            PagingBaseResponse.ErrorResponse(message = PAGE_SIZE_ERROR, pageNumber = pageNumber, pageSize = pageSize)
        } else {
            val response =
                productService.getProductsWithPaging(userId = userId, pageNumber = pageNumber, pageSize = pageSize)
            if (response.isNotEmpty()) {
                val totalCount = dbQuery {
                    ProductTable.selectAll().count()
                }
                PagingBaseResponse.SuccessResponse(
                    data = response,
                    message = SUCCESS,
                    pageNumber = pageNumber,
                    pageSize = pageSize,
                    totalCount = totalCount
                )
            } else {
                PagingBaseResponse.ErrorResponse(message = NO_DATA_FOUND, pageNumber = pageNumber, pageSize = pageSize)
            }
        }
    }

    override suspend fun getProductsGroupBySubCategory(userId: Int, category: String): BaseResponse<Any> {
        val response = productService.getProductsGroupBySubCategory(userId, category)
        return if (response.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = response, message = SUCCESS)
        } else {
            BaseResponse.ErrorResponse(message = NO_DATA_FOUND)
        }
    }

    override suspend fun getProductsByCategory(
        userId: Int,
        category: String,
        pageNumber: Int,
        pageSize: Int
    ): BaseResponse<Any> {
        val response = productService.getProductsByCategory(
            userId = userId,
            category = category,
            pageNumber = pageNumber,
            pageSize = pageSize
        )
        return if (response.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = response, message = SUCCESS)
        } else {
            BaseResponse.ErrorResponse(message = NO_DATA_FOUND)
        }
    }


}
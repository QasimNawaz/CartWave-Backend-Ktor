package store.cartwave.utils

import io.ktor.http.*

sealed class PagingBaseResponse<T>(
    val statusCode: HttpStatusCode = HttpStatusCode.OK
) {
    data class SuccessResponse<T>(
        val success: Boolean = true,
        val data: T? = null,
        val message: String? = null,
        val pageSize: Int = 0,
        val pageNumber: Int = 0,
        val totalCount: Long = 0
    ) : PagingBaseResponse<T>()

    data class ErrorResponse<T>(
        val success: Boolean = false,
        val data: T? = null,
        val message: String? = null,
        val pageSize: Int = 0,
        val pageNumber: Int = 0,
        val totalCount: Long = 0
    ) : PagingBaseResponse<T>()
}
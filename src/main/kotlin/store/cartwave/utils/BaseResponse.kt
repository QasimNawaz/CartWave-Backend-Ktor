package store.cartwave.utils


sealed class BaseResponse<T> {

    data class SuccessResponse<T>(
        val success: Boolean = true,
        val data: T? = null,
        val message: String? = null
    ) : BaseResponse<T>()

    data class ErrorResponse<T>(
        val success: Boolean = false,
        val data: T? = null,
        val message: String? = null
    ) : BaseResponse<T>()
}

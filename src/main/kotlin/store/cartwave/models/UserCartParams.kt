package store.cartwave.models

data class UserCartParams(
    val userId: Int,
    val productId: Int,
    val cartQty: Int
)

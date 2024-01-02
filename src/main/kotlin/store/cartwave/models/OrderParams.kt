package store.cartwave.models

data class OrderParams(
    val userId: Int,
    val orderDate: String,
    val shippingAddress: String,
    val promoCode: String,
    val orderStatus: String,
    val totalAmount: Int,
    val paymentMethod: String,
    val products: List<OrderProductsParams>
)

data class OrderProductsParams(
    val productId: Int,
    val title: String,
    val price: Int,
    val quantity: Int
)
package store.cartwave.models

data class Order(
    val id: Int,
    val userId: Int,
    val orderDate: String,
    val shippingAddress: String,
    val promoCode: String,
    val orderStatus: String,
    val totalAmount: Int,
    val paymentMethod: String,
    val products: List<OrderProduct>
)

data class OrderProduct(
    val productId: Int,
    val title: String,
    val price: Int,
    val quantity: Int
)

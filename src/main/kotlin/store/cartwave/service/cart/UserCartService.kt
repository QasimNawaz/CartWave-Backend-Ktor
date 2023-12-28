package store.cartwave.service.cart

import store.cartwave.models.Product
import store.cartwave.models.UserCartParams

interface UserCartService {
    suspend fun getUserCart(userId: Int): List<Product>
    suspend fun addToCart(params: UserCartParams): String
    suspend fun removeFromCart(params: UserCartParams): String
}
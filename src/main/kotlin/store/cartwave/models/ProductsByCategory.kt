package store.cartwave.models

data class ProductsByCategory(
    val category: String, val products: List<Product>
)

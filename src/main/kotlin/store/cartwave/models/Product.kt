package store.cartwave.models

data class Product(
    val id: Int,
    val actualPrice: String,
    val averageRating: String,
    val brand: String,
    val category: String,
    val crawledAt: String,
    val description: String,
    val discount: String,
    val images: List<String>,
    val outOfStock: Boolean,
    val pid: String,
    val productDetails: List<Map<String, String>>,
    val seller: String,
    val sellingPrice: String,
    val subCategory: String,
    val title: String,
    val url: String,
    val isFavourite: Boolean,
    val cartQty: Int
)

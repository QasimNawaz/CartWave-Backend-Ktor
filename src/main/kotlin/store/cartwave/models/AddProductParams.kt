package store.cartwave.models

data class AddProductParams(
    val _id: String,
    val actual_price: String,
    val average_rating: String,
    val brand: String,
    val category: String,
    val crawled_at: String,
    val description: String,
    val discount: String,
    val images: List<String>,
    val out_of_stock: Boolean,
    val pid: String,
    val product_details: List<Map<String, String>>,
    val seller: String,
    val selling_price: String,
    val sub_category: String,
    val title: String,
    val url: String
)
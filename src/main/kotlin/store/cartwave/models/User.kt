package store.cartwave.models

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val email: String,
    var authToken: String? = null,
    val createdAt: String,
)

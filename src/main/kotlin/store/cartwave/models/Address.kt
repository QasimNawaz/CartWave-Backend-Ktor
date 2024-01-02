package store.cartwave.models

data class Address(
    val id: Int,
    val userId: Int,
    val address: String,
    val isPrimary: Boolean
)

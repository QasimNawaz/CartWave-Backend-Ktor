package store.cartwave.models

data class CreateUserParams(
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val email: String,
    val password: String,
)

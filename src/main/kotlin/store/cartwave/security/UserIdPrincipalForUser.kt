package store.cartwave.security

import io.ktor.server.auth.*

data class UserIdPrincipalForUser(val id: Int): Principal

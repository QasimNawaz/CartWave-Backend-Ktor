package store.cartwave.security

import store.cartwave.config.INVALID_AUTHENTICATION_TOKEN
import store.cartwave.utils.BaseResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    JwtConfig.initialize("cartwave-app")
    install(Authentication) {
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asInt()
                if (claim != null) {
                    UserIdPrincipalForUser(claim)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = BaseResponse.ErrorResponse(INVALID_AUTHENTICATION_TOKEN)
                )
            }
        }
    }
}
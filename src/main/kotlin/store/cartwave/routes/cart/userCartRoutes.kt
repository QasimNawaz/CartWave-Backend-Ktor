package store.cartwave.routes.cart

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import store.cartwave.models.UserCartParams
import store.cartwave.repository.cart.UserCartRepository

fun Application.userCartRoutes(repository: UserCartRepository) {
    routing {
        authenticate {
            route("cart") {
                get("/getUserCart") {
                    val userId = call.request.queryParameters["userId"]?.toIntOrNull() ?: -1
                    val result = repository.getUserCartCart(userId = userId)
                    call.respond(result)
                }
                post("/addToCart") {
                    val params = call.receive<UserCartParams>()
                    val result = repository.addToCart(params)
                    call.respond(result)
                }
                post("/removeFromCart") {
                    val params = call.receive<UserCartParams>()
                    val result = repository.removeFromCart(params)
                    call.respond(result)
                }
            }
        }
    }
}
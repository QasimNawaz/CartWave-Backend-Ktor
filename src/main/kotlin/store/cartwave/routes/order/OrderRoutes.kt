package store.cartwave.routes.order

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import store.cartwave.models.OrderParams
import store.cartwave.repository.order.OrderRepository

fun Application.orderRoutes(repository: OrderRepository) {
    routing {
        authenticate {
            route("/order") {
                post("/placeOrder") {
                    val params = call.receive<OrderParams>()
                    val result = repository.placeOrder(params)
                    call.respond(result)
                }
                get("/getMyOrders") {
                    val userId = call.request.queryParameters["userId"]?.toIntOrNull() ?: -1
                    val result =
                        repository.getMyOrders(userId = userId)
                    call.respond(result)
                }
            }
        }
    }
}
package store.cartwave.routes.address

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import store.cartwave.models.AddressParams
import store.cartwave.models.UpdatePrimaryAddressParams
import store.cartwave.repository.address.AddressRepository

fun Application.addressRoutes(repository: AddressRepository) {
    routing {
        authenticate {
            route("/address") {
                post("/addAddress") {
                    val params = call.receive<AddressParams>()
                    val result = repository.addAddress(params)
                    call.respond(result)
                }
                post("/updatePrimaryAddress") {
                    val params = call.receive<UpdatePrimaryAddressParams>()
                    val result = repository.updatePrimaryAddress(params)
                    call.respond(result)
                }
                get("/getPrimaryAddress") {
                    val userId = call.request.queryParameters["userId"]?.toIntOrNull() ?: -1
                    val result =
                        repository.getPrimaryAddress(userId = userId)
                    call.respond(result)
                }
                get("/getAddress") {
                    val userId = call.request.queryParameters["userId"]?.toIntOrNull() ?: -1
                    val result =
                        repository.getAllAddresses(userId = userId)
                    call.respond(result)
                }
            }
        }
    }
}
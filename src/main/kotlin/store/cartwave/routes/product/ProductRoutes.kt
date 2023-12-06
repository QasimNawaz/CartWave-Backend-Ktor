package store.cartwave.routes.product

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import store.cartwave.models.AddProductParams
import store.cartwave.repository.product.ProductRepository

fun Application.productRoutes(repository: ProductRepository) {
    routing {
        authenticate {
            route("/product") {
                post("/addProducts") {
                    val params = call.receive<List<AddProductParams>>()
                    val result = repository.addProducts(params)
                    call.respond(result)
                }
                post("/addProduct") {
                    val params = call.receive<AddProductParams>()
                    val result = repository.addProduct(params)
                    call.respond(result)
                }
                get("/getProducts") {
                    val pageNumber =
                        call.request.queryParameters["pageNumber"]?.toIntOrNull() ?: 1 // Default to page 1 if not provided
                    val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull()
                        ?: 10 // Default to page size 10 if not provided
                    val result = repository.getProductsWithPaging(pageSize = pageSize, pageNumber = pageNumber)
                    call.respond(result)
                }
            }
        }
    }
}
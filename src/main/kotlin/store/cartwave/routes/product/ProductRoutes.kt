package store.cartwave.routes.product

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.selectAll
import store.cartwave.db.DatabaseFactory
import store.cartwave.db.ProductTable
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
                get("/getProduct") {
                    val userId = call.request.queryParameters["userId"]?.toIntOrNull() ?: -1
                    val productId = call.request.queryParameters["productId"]?.toIntOrNull() ?: -1
                    val result =
                        repository.getProduct(userId = userId, productId = productId)
                    call.respond(result)
                }
                get("/getProducts") {
                    val userId = call.request.queryParameters["userId"]?.toIntOrNull() ?: -1
                    val pageNumber =
                        call.request.queryParameters["pageNumber"]?.toIntOrNull()
                            ?: 1 // Default to page 1 if not provided
                    val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull()
                        ?: 10 // Default to page size 10 if not provided
                    val result =
                        repository.getProductsWithPaging(userId = userId, pageSize = pageSize, pageNumber = pageNumber)
                    call.respond(result)
                }
                get("/getProductsByCategory") {
                    val userId = call.request.queryParameters["userId"]?.toIntOrNull() ?: -1
                    val pageNumber =
                        call.request.queryParameters["pageNumber"]?.toIntOrNull()
                            ?: 1 // Default to page 1 if not provided
                    val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull()
                        ?: 10 // Default to page size 10 if not provided
                    val category = call.request.queryParameters["category"] ?: "All"
                    val result = repository.getProductsByCategory(userId, category, pageNumber, pageSize)
                    call.respond(result)
                }
                get("/getProductsGroupByCategory") {
                    val userId = call.request.queryParameters["userId"]?.toIntOrNull() ?: -1
                    val category = call.request.queryParameters["category"] ?: "All"
                    val result = repository.getProductsGroupBySubCategory(userId, category)
                    call.respond(result)
                }
                get("/getCategories") {
                    val categoriesWithSubcategories = DatabaseFactory.dbQuery {
                        ProductTable
                            .slice(ProductTable.category, ProductTable.subCategory)
                            .selectAll()
                            .groupBy(ProductTable.category, ProductTable.subCategory)
                            .map {
                                it[ProductTable.category] to it[ProductTable.subCategory]
                            }
                    }
                    val categoriesMap = categoriesWithSubcategories.groupBy({ it.first }, { it.second })
                    call.respond(categoriesMap)
                }
            }
        }
    }
}
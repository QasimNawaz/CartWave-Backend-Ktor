package store.cartwave.routes.favourite

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import store.cartwave.models.FavouriteParams
import store.cartwave.repository.favourite.FavouriteRepository

fun Application.favouriteRoutes(repository: FavouriteRepository) {
    routing {
        authenticate {
            route("/favourite") {
                post("/addToFavourite") {
                    val params = call.receive<FavouriteParams>()
                    val result = repository.addToFavourite(params)
                    call.respond(result)
                }
                post("/removeFromFavourite") {
                    val params = call.receive<FavouriteParams>()
                    val result = repository.removeFromFavourite(params)
                    call.respond(result)
                }
                get("/getFavourites") {
                    val userId = call.request.queryParameters["userId"]?.toIntOrNull() ?: -1
                    val pageNumber =
                        call.request.queryParameters["pageNumber"]?.toIntOrNull()
                            ?: 1 // Default to page 1 if not provided
                    val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull()
                        ?: 10 // Default to page size 10 if not provided
                    val result =
                        repository.getAllFavourites(userId = userId, pageSize = pageSize, pageNumber = pageNumber)
                    call.respond(result)
                }
            }
        }
    }
}
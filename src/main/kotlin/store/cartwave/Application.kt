package store.cartwave

import store.cartwave.db.DatabaseFactory
import store.cartwave.repository.auth.AuthRepository
import store.cartwave.repository.auth.AuthRepositoryImpl
import store.cartwave.routes.auth.authRoutes
import store.cartwave.service.auth.AuthService
import store.cartwave.service.auth.AuthServiceImpl
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import store.cartwave.repository.cart.UserCartRepository
import store.cartwave.repository.cart.UserCartRepositoryImpl
import store.cartwave.repository.favourite.FavouriteRepository
import store.cartwave.repository.favourite.FavouriteRepositoryImpl
import store.cartwave.repository.product.ProductRepository
import store.cartwave.repository.product.ProductRepositoryImpl
import store.cartwave.routes.cart.userCartRoutes
import store.cartwave.routes.favourite.favouriteRoutes
import store.cartwave.routes.product.productRoutes
import store.cartwave.security.configureSecurity
import store.cartwave.service.cart.UserCartService
import store.cartwave.service.cart.UserCartServiceImpl
import store.cartwave.service.favourite.FavouriteService
import store.cartwave.service.favourite.FavouriteServiceImpl
import store.cartwave.service.product.ProductService
import store.cartwave.service.product.ProductServiceImpl

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {

    DatabaseFactory.init()

    install(ContentNegotiation) {
        jackson()
    }

    configureSecurity()

    val authService: AuthService = AuthServiceImpl()
    val authRepository: AuthRepository = AuthRepositoryImpl(authService)
    authRoutes(authRepository)

    val productService: ProductService = ProductServiceImpl()
    val productRepository: ProductRepository = ProductRepositoryImpl(productService)
    productRoutes(productRepository)

    val favouriteService: FavouriteService = FavouriteServiceImpl()
    val favouriteRepository: FavouriteRepository = FavouriteRepositoryImpl(favouriteService)
    favouriteRoutes(favouriteRepository)

    val userCartService: UserCartService = UserCartServiceImpl()
    val userCartRepository: UserCartRepository = UserCartRepositoryImpl(userCartService)
    userCartRoutes(userCartRepository)
}


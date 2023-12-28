package store.cartwave.service.favourite

import store.cartwave.models.FavouriteParams
import store.cartwave.models.Product

interface FavouriteService {
    suspend fun addToFavourite(params: FavouriteParams): String
    suspend fun removeFromFavourite(params: FavouriteParams): String
    suspend fun getAllFavourites(userId: Int, pageNumber: Int, pageSize: Int): List<Product>
}
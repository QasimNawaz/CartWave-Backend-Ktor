package store.cartwave.repository.favourite

import store.cartwave.models.FavouriteParams
import store.cartwave.utils.BaseResponse
import store.cartwave.utils.PagingBaseResponse

interface FavouriteRepository {
    suspend fun addToFavourite(params: FavouriteParams): BaseResponse<Any>
    suspend fun removeFromFavourite(params: FavouriteParams): BaseResponse<Any>
    suspend fun getAllFavourites(userId: Int, pageNumber: Int, pageSize: Int): PagingBaseResponse<Any>
}
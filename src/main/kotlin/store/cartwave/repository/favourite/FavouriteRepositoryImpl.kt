package store.cartwave.repository.favourite

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import store.cartwave.config.*
import store.cartwave.db.DatabaseFactory
import store.cartwave.db.DatabaseFactory.dbQuery
import store.cartwave.db.FavouriteTable
import store.cartwave.db.ProductTable
import store.cartwave.models.FavouriteParams
import store.cartwave.service.favourite.FavouriteService
import store.cartwave.utils.BaseResponse
import store.cartwave.utils.PagingBaseResponse

class FavouriteRepositoryImpl(private val favouriteService: FavouriteService) : FavouriteRepository {
    override suspend fun addToFavourite(params: FavouriteParams): BaseResponse<Any> {
        val response = favouriteService.addToFavourite(params)
        return if (response == PRODUCT_ALREADY_ADDED_IN_FAVOURITE) {
            BaseResponse.ErrorResponse(message = response)
        } else {
            BaseResponse.SuccessResponse(data = response, message = response)
        }
    }

    override suspend fun removeFromFavourite(params: FavouriteParams): BaseResponse<Any> {
        val response = favouriteService.removeFromFavourite(params)
        return if (response == PRODUCT_NOT_FOUND_IN_FAVOURITES) {
            BaseResponse.ErrorResponse(message = response)
        } else {
            BaseResponse.SuccessResponse(data = response, message = response)
        }
    }

    override suspend fun getAllFavourites(userId: Int, pageNumber: Int, pageSize: Int): PagingBaseResponse<Any> {
        return if (pageNumber < 1) {
            PagingBaseResponse.ErrorResponse(message = PAGE_NUMBER_ERROR, pageNumber = pageNumber, pageSize = pageSize)
        } else if (pageSize < 5) {
            PagingBaseResponse.ErrorResponse(message = PAGE_SIZE_ERROR, pageNumber = pageNumber, pageSize = pageSize)
        } else {
            val response =
                favouriteService.getAllFavourites(userId = userId, pageNumber = pageNumber, pageSize = pageSize)
            if (response.isNotEmpty()) {
                val totalCount = dbQuery {
                    FavouriteTable.select { FavouriteTable.userId eq userId }.count()
                }
                PagingBaseResponse.SuccessResponse(
                    data = response, pageNumber = pageNumber, pageSize = pageSize, totalCount = totalCount
                )
            } else {
                PagingBaseResponse.ErrorResponse(message = NO_DATA_FOUND, pageNumber = pageNumber, pageSize = pageSize)
            }
        }
    }
}
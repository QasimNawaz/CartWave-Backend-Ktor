package store.cartwave.repository.address

import store.cartwave.config.DATA_INSERTED
import store.cartwave.config.DATA_UPDATED
import store.cartwave.config.NO_DATA_FOUND
import store.cartwave.config.SUCCESS
import store.cartwave.models.AddressParams
import store.cartwave.models.UpdatePrimaryAddressParams
import store.cartwave.service.address.AddressService
import store.cartwave.utils.BaseResponse

class AddressRepositoryImpl(private val addressService: AddressService) : AddressRepository {
    override suspend fun addAddress(params: AddressParams): BaseResponse<Any> {
        val response = addressService.addAddress(params)
        return if (response == DATA_INSERTED) {
            BaseResponse.SuccessResponse(data = response, message = response)
        } else {
            BaseResponse.ErrorResponse(message = response)
        }
    }

    override suspend fun updatePrimaryAddress(params: UpdatePrimaryAddressParams): BaseResponse<Any> {
        val response = addressService.updatePrimaryAddress(params)
        return if (response == DATA_UPDATED) {
            BaseResponse.SuccessResponse(data = response, message = response)
        } else {
            BaseResponse.ErrorResponse(message = response)
        }
    }

    override suspend fun getPrimaryAddress(userId: Int): BaseResponse<Any> {
        val response = addressService.getPrimaryAddress(userId)
        return if (response != null) {
            BaseResponse.SuccessResponse(data = response, message = SUCCESS)
        } else {
            BaseResponse.ErrorResponse(message = NO_DATA_FOUND)
        }
    }

    override suspend fun getAllAddresses(userId: Int): BaseResponse<Any> {
        val response = addressService.getAllAddresses(userId)
        return if (response.isEmpty()) {
            BaseResponse.ErrorResponse(message = NO_DATA_FOUND)
        } else {
            BaseResponse.SuccessResponse(data = response, message = SUCCESS)
        }
    }
}
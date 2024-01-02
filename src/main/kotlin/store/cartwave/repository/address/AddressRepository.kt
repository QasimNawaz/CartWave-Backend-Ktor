package store.cartwave.repository.address

import store.cartwave.models.AddressParams
import store.cartwave.models.UpdatePrimaryAddressParams
import store.cartwave.utils.BaseResponse

interface AddressRepository {
    suspend fun addAddress(params: AddressParams): BaseResponse<Any>
    suspend fun updatePrimaryAddress(params: UpdatePrimaryAddressParams): BaseResponse<Any>
    suspend fun getPrimaryAddress(userId: Int): BaseResponse<Any>
    suspend fun getAllAddresses(userId: Int): BaseResponse<Any>
}
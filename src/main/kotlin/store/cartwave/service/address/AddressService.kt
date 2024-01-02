package store.cartwave.service.address

import store.cartwave.models.Address
import store.cartwave.models.AddressParams
import store.cartwave.models.UpdatePrimaryAddressParams

interface AddressService {
    suspend fun addAddress(params: AddressParams): String
    suspend fun updatePrimaryAddress(params: UpdatePrimaryAddressParams): String
    suspend fun getPrimaryAddress(userId: Int): Address?
    suspend fun getAllAddresses(userId: Int): List<Address>
}
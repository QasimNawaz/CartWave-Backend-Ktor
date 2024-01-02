package store.cartwave.service.address

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import store.cartwave.config.DATA_INSERTED
import store.cartwave.config.DATA_UPDATED
import store.cartwave.db.AddressTable
import store.cartwave.db.DatabaseFactory.dbQuery
import store.cartwave.db.rowToAddress
import store.cartwave.models.Address
import store.cartwave.models.AddressParams
import store.cartwave.models.UpdatePrimaryAddressParams

class AddressServiceImpl : AddressService {
    override suspend fun addAddress(params: AddressParams): String {
        return runCatching {
            dbQuery {
                AddressTable.update({ AddressTable.userId eq params.userId }) {
                    it[isPrimary] = false
                }
                AddressTable.insert {
                    it[userId] = params.userId
                    it[address] = params.address
                    it[isPrimary] = true
                }
            }
            DATA_INSERTED
        }.getOrElse {
            it.message.toString()
        }
    }

    override suspend fun updatePrimaryAddress(params: UpdatePrimaryAddressParams): String {
        return runCatching {
            dbQuery {
                AddressTable.update({ (AddressTable.userId eq params.userId) and (AddressTable.id neq params.addressId) }) {
                    it[isPrimary] = false
                }
                AddressTable.update({ (AddressTable.userId eq params.userId) and (AddressTable.id eq params.addressId) }) {
                    it[isPrimary] = true
                }
            }
            DATA_UPDATED
        }.getOrElse {
            it.message.toString()
        }
    }

    override suspend fun getPrimaryAddress(userId: Int): Address? {
        return runCatching {
            dbQuery {
                AddressTable.select {
                    (AddressTable.userId eq userId) and (AddressTable.isPrimary eq true)
                }.map { row ->
                    row.rowToAddress()
                }
            }.firstOrNull()
        }.getOrNull()
    }

    override suspend fun getAllAddresses(userId: Int): List<Address> {
        return runCatching {
            dbQuery {
                AddressTable.select {
                    AddressTable.userId eq userId
                }.map { row ->
                    row.rowToAddress()
                }
            }
        }.getOrElse {
            emptyList<Address>()
        }
    }
}
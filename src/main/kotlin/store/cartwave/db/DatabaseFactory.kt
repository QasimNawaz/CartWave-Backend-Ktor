package store.cartwave.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(
                UserTable,
                ProductTable,
                FavouriteTable,
                UserCartTable,
                OrderTable,
                OrderProductsTable,
                AddressTable
            )
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = System.getenv("DRIVER")
        config.jdbcUrl = System.getenv("LOCAL_DB_URL")
        config.username = System.getenv("LOCAL_DB_USER_NAME")
        config.password = System.getenv("LOCAL_DB_PASSWORD")

//        config.jdbcUrl = System.getenv("REMOTE_DB_URL")
//        config.username = System.getenv("REMOTE_DB_USER_NAME")
//        config.password = System.getenv("REMOTE_DB_PASSWORD")

        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction {
            block()
        }
    }
}
package store.cartwave.db

import org.jetbrains.exposed.sql.Table

object AddressTable : Table("address") {

    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val address = varchar("address", 10000)
    val isPrimary = bool("is_primary")

    override val primaryKey = PrimaryKey(id)
}
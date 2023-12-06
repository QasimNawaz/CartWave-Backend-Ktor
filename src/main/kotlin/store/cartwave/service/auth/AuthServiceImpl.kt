package store.cartwave.service.auth

import store.cartwave.db.DatabaseFactory.dbQuery
import store.cartwave.db.UserTable
import store.cartwave.models.CreateUserParams
import store.cartwave.models.User
import store.cartwave.security.hash
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class AuthServiceImpl : AuthService {
    override suspend fun registerUser(params: CreateUserParams): User? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = UserTable.insert {
                it[firstName] = params.firstName
                it[lastName] = params.lastName
                it[avatar] = params.avatar
                it[email] = params.email
                it[password] = hash(params.password)
            }
        }
        return rowToUser(statement?.resultedValues?.get(0))
    }

    override suspend fun loginUser(email: String, password: String): User? {
        val hashedPassword = hash(password)
        val userRow = dbQuery {
            UserTable.select { UserTable.email eq email and (UserTable.password eq hashedPassword) }.firstOrNull()
        }
        return rowToUser(userRow)
    }

    override suspend fun findUserByEmail(email: String): User? {
        return dbQuery {
            UserTable.select { UserTable.email.eq(email) }.map { rowToUser(it) }.singleOrNull()
        }
    }

    private fun rowToUser(row: ResultRow?): User? {
        return if (row == null) null else User(
            id = row[UserTable.id],
            firstName = row[UserTable.firstName],
            lastName = row[UserTable.lastName],
            avatar = row[UserTable.avatar],
            email = row[UserTable.email],
            createdAt = row[UserTable.createdAt].toString()
        )
    }
}
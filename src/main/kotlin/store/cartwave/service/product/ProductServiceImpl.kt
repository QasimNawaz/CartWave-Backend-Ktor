package store.cartwave.service.product

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import store.cartwave.db.DatabaseFactory.dbQuery
import store.cartwave.db.ProductTable
import store.cartwave.models.AddProductParams
import store.cartwave.models.Product

class ProductServiceImpl : ProductService {
    override suspend fun addProducts(params: List<AddProductParams>): String {
        var statement: InsertStatement<Number>? = null
        val objectMapper = ObjectMapper()
        dbQuery {
            params.forEach { productParams ->
                statement = ProductTable.insert {
                    it[actualPrice] = productParams.actual_price
                    it[averageRating] = productParams.average_rating
                    it[brand] = productParams.brand
                    it[category] = productParams.category
                    it[crawledAt] = productParams.crawled_at
                    it[description] = productParams.description
                    it[discount] = productParams.discount
                    it[images] = objectMapper.writeValueAsString(productParams.images) // Convert list to JSON string
                    it[outOfStock] = productParams.out_of_stock
                    it[pid] = productParams.pid
                    it[productDetails] =
                        objectMapper.writeValueAsString(productParams.product_details) // Convert map to JSON string
                    it[seller] = productParams.seller
                    it[sellingPrice] = productParams.selling_price
                    it[subCategory] = productParams.sub_category
                    it[title] = productParams.title
                    it[url] = productParams.url
                }
            }
        }
        return "Added: ${statement?.insertedCount}"
    }

    override suspend fun addProduct(params: AddProductParams): String {
        var statement: InsertStatement<Number>? = null
        val objectMapper = ObjectMapper()
        dbQuery {
            statement = ProductTable.insert {
                it[actualPrice] = params.actual_price
                it[averageRating] = params.average_rating
                it[brand] = params.brand
                it[category] = params.category
                it[crawledAt] = params.crawled_at
                it[description] = params.description
                it[discount] = params.discount
                it[images] = objectMapper.writeValueAsString(params.images) // Convert list to JSON string
                it[outOfStock] = params.out_of_stock
                it[pid] = params.pid
                it[productDetails] =
                    objectMapper.writeValueAsString(params.product_details) // Convert map to JSON string
                it[seller] = params.seller
                it[sellingPrice] = params.selling_price
                it[subCategory] = params.sub_category
                it[title] = params.title
                it[url] = params.url
            }
        }
        return "Added: ${statement?.insertedCount}"
    }

    override suspend fun getProductsWithPaging(pageNumber: Int, pageSize: Int): List<Product> {
        return dbQuery {
            ProductTable.slice(
                ProductTable.id,
                ProductTable.actualPrice,
                ProductTable.averageRating,
                ProductTable.brand,
                ProductTable.category,
                ProductTable.crawledAt,
                ProductTable.description,
                ProductTable.discount,
                ProductTable.images,
                ProductTable.outOfStock,
                ProductTable.pid,
                ProductTable.productDetails,
                ProductTable.seller,
                ProductTable.sellingPrice,
                ProductTable.subCategory,
                ProductTable.title,
                ProductTable.url,
            )
                .selectAll()
                .orderBy(ProductTable.id)
                .limit(pageSize, offset = ((pageNumber - 1) * pageSize).toLong())
                .map { row ->
                    rowToProduct(row)
                }
        }
    }

    private fun rowToProduct(row: ResultRow): Product {
        val objectMapper = jacksonObjectMapper()
        return Product(
            id = row[ProductTable.id],
            actualPrice = row[ProductTable.actualPrice],
            averageRating = row[ProductTable.averageRating],
            brand = row[ProductTable.brand],
            category = row[ProductTable.category],
            crawledAt = row[ProductTable.crawledAt],
            description = row[ProductTable.description],
            discount = row[ProductTable.discount],
            images = objectMapper.readValue(row[ProductTable.images].toString()),
            outOfStock = row[ProductTable.outOfStock],
            pid = row[ProductTable.pid],
            productDetails = objectMapper.readValue(row[ProductTable.productDetails].toString()),
            seller = row[ProductTable.seller],
            sellingPrice = row[ProductTable.sellingPrice],
            subCategory = row[ProductTable.subCategory],
            title = row[ProductTable.title],
            url = row[ProductTable.url]
        )
    }
}
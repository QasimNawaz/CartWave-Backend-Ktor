package store.cartwave.service.product

import com.fasterxml.jackson.databind.ObjectMapper
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import store.cartwave.config.DATA_EXIST
import store.cartwave.config.DATA_INSERTED
import store.cartwave.db.DatabaseFactory.dbQuery
import store.cartwave.db.FavouriteTable
import store.cartwave.db.ProductTable
import store.cartwave.db.rowToProduct
import store.cartwave.db.rowToProductWithQty
import store.cartwave.models.AddProductParams
import store.cartwave.models.Categories
import store.cartwave.models.Product
import store.cartwave.models.ProductsByCategory

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
            val alreadyExist = ProductTable.select {
                (ProductTable.pid eq params.pid)
            }.count() > 0
            if (!alreadyExist) {
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
        }
        return if (statement?.insertedCount != null) DATA_INSERTED else DATA_EXIST
    }

    override suspend fun getProduct(userId: Int, productId: Int): Product {
        return dbQuery {
            ProductTable.select {
                ProductTable.id eq productId
            }.map { row ->
                row.rowToProductWithQty(userId)
            }.first()
        }
    }

    override suspend fun getProductsWithPaging(userId: Int, pageNumber: Int, pageSize: Int): List<Product> {
        return dbQuery {

            ProductTable.selectAll().orderBy(ProductTable.id)
                .limit(pageSize, offset = ((pageNumber - 1) * pageSize).toLong()).map { row ->
                    row.rowToProduct(userId)
                }
        }
    }

    override suspend fun getProductsByCategory(
        userId: Int, category: String, pageNumber: Int, pageSize: Int
    ): List<Product> {
        return dbQuery {
            val query = if (category == "All") {
                ProductTable.selectAll()
            } else {
                ProductTable.select {
                    (ProductTable.category eq category)
                }
            }
            query.orderBy(ProductTable.id).limit(pageSize, offset = ((pageNumber - 1) * pageSize).toLong()).map { row ->
                row.rowToProduct(userId)
            }
        }
    }

    override suspend fun getProductsGroupBySubCategory(userId: Int, category: String): List<ProductsByCategory> {
        val productsByCategory = mutableListOf<ProductsByCategory>()
        dbQuery {
            val query = if (category == "All") {
                ProductTable.slice(ProductTable.subCategory).selectAll()
            } else {
                ProductTable.slice(ProductTable.subCategory).select { (ProductTable.category eq category) }
            }
            val categories = query.map { it[ProductTable.subCategory] }.distinct()
            println("categoriesCount: ${categories.size}")
            for (category in categories) {
                val products = ProductTable.select { ProductTable.subCategory eq category }.limit(6).map { row ->
                    row.rowToProduct(userId)
                }
                productsByCategory.add(ProductsByCategory(category = category, products = products))
            }
        }
        return productsByCategory
    }

    override suspend fun getAllCategories(): List<Categories> {
        TODO("Not yet implemented")
    }
}
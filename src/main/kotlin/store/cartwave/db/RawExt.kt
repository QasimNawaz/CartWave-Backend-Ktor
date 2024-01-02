package store.cartwave.db

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import store.cartwave.models.*

fun ResultRow.rowToProduct(userId: Int): Product {
    val objectMapper = jacksonObjectMapper()
    val productId = this[ProductTable.id]
    val isFavourite = FavouriteTable.select {
        (FavouriteTable.userId eq userId) and (FavouriteTable.productId eq productId)
    }.count() > 0
    val cartQty = UserCartTable.select {
        (UserCartTable.userId eq userId) and (UserCartTable.productId eq productId)
    }.map {
        it[UserCartTable.cartQty]
    }.firstOrNull()
    return Product(
        id = this[ProductTable.id],
        actualPrice = this[ProductTable.actualPrice],
        averageRating = this[ProductTable.averageRating],
        brand = this[ProductTable.brand],
        category = this[ProductTable.category],
        crawledAt = this[ProductTable.crawledAt],
        description = this[ProductTable.description],
        discount = this[ProductTable.discount],
        images = objectMapper.readValue(this[ProductTable.images].toString()),
        outOfStock = this[ProductTable.outOfStock],
        pid = this[ProductTable.pid],
        productDetails = objectMapper.readValue(this[ProductTable.productDetails].toString()),
        seller = this[ProductTable.seller],
        sellingPrice = this[ProductTable.sellingPrice],
        subCategory = this[ProductTable.subCategory],
        title = this[ProductTable.title],
        url = this[ProductTable.url],
        isFavourite = isFavourite,
        cartQty = 0
    )
}

fun ResultRow.rowToProductWithQty(userId: Int): Product {
    val objectMapper = jacksonObjectMapper()
    val productId = this[ProductTable.id]
    val isFavourite = FavouriteTable.select {
        (FavouriteTable.userId eq userId) and (FavouriteTable.productId eq productId)
    }.count() > 0
    val cartQty = UserCartTable.select {
        (UserCartTable.userId eq userId) and (UserCartTable.productId eq productId)
    }.map {
        it[UserCartTable.cartQty]
    }.firstOrNull()
    return Product(
        id = this[ProductTable.id],
        actualPrice = this[ProductTable.actualPrice],
        averageRating = this[ProductTable.averageRating],
        brand = this[ProductTable.brand],
        category = this[ProductTable.category],
        crawledAt = this[ProductTable.crawledAt],
        description = this[ProductTable.description],
        discount = this[ProductTable.discount],
        images = objectMapper.readValue(this[ProductTable.images].toString()),
        outOfStock = this[ProductTable.outOfStock],
        pid = this[ProductTable.pid],
        productDetails = objectMapper.readValue(this[ProductTable.productDetails].toString()),
        seller = this[ProductTable.seller],
        sellingPrice = this[ProductTable.sellingPrice],
        subCategory = this[ProductTable.subCategory],
        title = this[ProductTable.title],
        url = this[ProductTable.url],
        isFavourite = isFavourite,
        cartQty = cartQty ?: 0
    )
}

fun ResultRow.rowToProduct(): Product {
    val objectMapper = jacksonObjectMapper()
    return Product(
        id = this[ProductTable.id],
        actualPrice = this[ProductTable.actualPrice],
        averageRating = this[ProductTable.averageRating],
        brand = this[ProductTable.brand],
        category = this[ProductTable.category],
        crawledAt = this[ProductTable.crawledAt],
        description = this[ProductTable.description],
        discount = this[ProductTable.discount],
        images = objectMapper.readValue(this[ProductTable.images].toString()),
        outOfStock = this[ProductTable.outOfStock],
        pid = this[ProductTable.pid],
        productDetails = objectMapper.readValue(this[ProductTable.productDetails].toString()),
        seller = this[ProductTable.seller],
        sellingPrice = this[ProductTable.sellingPrice],
        subCategory = this[ProductTable.subCategory],
        title = this[ProductTable.title],
        url = this[ProductTable.url],
        isFavourite = true,
        cartQty = 0
    )
}

fun ResultRow.rowToAddress(): Address {
    return Address(
        id = this[AddressTable.id],
        userId = this[AddressTable.userId],
        address = this[AddressTable.address],
        isPrimary = this[AddressTable.isPrimary]
    )
}

fun ResultRow.rowToOrder(): Order {
    val orderId = this[OrderTable.id]
    val orderProducts = OrderProductsTable.select {
        OrderProductsTable.orderId eq orderId
    }.map { row ->
        row.rowToOrderProduct()
    }
    return Order(
        id = this[OrderTable.id],
        userId = this[OrderTable.userId],
        orderDate = this[OrderTable.orderDate],
        shippingAddress = this[OrderTable.shippingAddress],
        promoCode = this[OrderTable.promoCode],
        orderStatus = this[OrderTable.orderStatus],
        totalAmount = this[OrderTable.totalAmount],
        paymentMethod = this[OrderTable.paymentMethod],
        products = orderProducts
    )
}

fun ResultRow.rowToOrderProduct(): OrderProduct {
    return OrderProduct(
        productId = this[OrderProductsTable.productId],
        title = this[OrderProductsTable.title],
        price = this[OrderProductsTable.price],
        quantity = this[OrderProductsTable.quantity]
    )
}
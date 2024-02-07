package ru.akumakeito.effectivemobile_test.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.akumakeito.effectivemobile_test.data.dto.ProductDTO
import ru.akumakeito.effectivemobile_test.domain.model.Feedback
import ru.akumakeito.effectivemobile_test.domain.model.Price
import ru.akumakeito.effectivemobile_test.domain.model.Product
import ru.akumakeito.effectivemobile_test.domain.model.ProductInfo
import ru.akumakeito.effectivemobile_test.domain.model.Tags

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val title : String,
    val subtitle :String,
    @Embedded
    val price : Price,
    @Embedded
    val feedback : Feedback?,
    val tags : List<Tags>,
    val available : Int,
    val description : String,
    val info : List<ProductInfo>,
    val ingredients : String,
    val imageList : List<Int> = emptyList(),
    val isFavorite : Boolean = false
) {

    companion object {
        fun fromDto(productDTO: ProductDTO) = ProductEntity(
            id = productDTO.id,
            title = productDTO.title,
            subtitle = productDTO.subtitle,
            price = productDTO.price,
            feedback = productDTO.feedback,
            tags = productDTO.tags,
            available = productDTO.available,
            description = productDTO.description,
            info = productDTO.info,
            ingredients = productDTO.ingredients,
            imageList = emptyList(),
            isFavorite = false
        )
    }

    fun toProduct() = Product(
        id = id,
        title = title,
        subtitle = subtitle,
        price = price,
        feedback = feedback,
        tags = tags,
        available = available,
        description = description,
        info = info,
        ingredients = ingredients,
        imageList = imageList,
        isFavorite = isFavorite
    )

}

fun List<ProductEntity>.toProduct() : List<Product> = map(ProductEntity::toProduct)
fun Array<ProductDTO>.toEntity() : List<ProductEntity> = map(ProductEntity::fromDto)


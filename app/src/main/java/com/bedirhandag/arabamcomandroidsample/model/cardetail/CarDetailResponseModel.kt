package com.bedirhandag.arabamcomandroidsample.model.cardetail

import com.bedirhandag.arabamcomandroidsample.model.Category
import com.bedirhandag.arabamcomandroidsample.model.Location
import com.bedirhandag.arabamcomandroidsample.model.Property

data class CarDetailResponseModel(
    val category: Category? = null,
    val date: String? = null,
    val dateFormatted: String? = null,
    val id: Int? = null,
    val location: Location? = null,
    val modelName: String? = null,
    val photos: List<String>? = null,
    val price: Int? = null,
    val priceFormatted: String? = null,
    val properties: List<Property>? = null,
    val text: String? = null,
    val title: String? = null,
    val userInfo: UserInfo? = null
)
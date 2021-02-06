package com.bedirhandag.arabamcomandroidsample.api

import com.bedirhandag.arabamcomandroidsample.model.carlist.CarListResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("listing")
    fun getCarList(
        @Query("sort") sort: Int,
        @Query("sortDirection") sortDirection: Int,
        @Query("take") take: Int
    ): Call<CarListResponseModel>


}
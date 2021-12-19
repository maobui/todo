package com.bui.todoapplication.remote

import com.bui.todoapplication.model.Product
import com.bui.todoapplication.model.User
import retrofit2.Call
import retrofit2.http.GET

interface TodoApi {

    @GET("call")
    suspend fun callList(): List<User>

    @GET("buy")
    suspend fun buyList(): List<Product>
}
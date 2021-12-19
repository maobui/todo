package com.bui.todoapplication.remote

import com.bui.todoapplication.model.Product
import com.bui.todoapplication.model.User
import retrofit2.Call
import retrofit2.http.GET

interface AppApi {

    @GET("call")
    fun callList(): Call<List<User>>

    @GET("buy")
    fun buyList(): Call<List<Product>>
}
package com.samuelsihombing.kelompok8.app

import com.samuelsihombing.kelompok8.model.Checkout
import com.samuelsihombing.kelompok8.model.ResponModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("phone") nomortlp : String,
        @Field("password") password : String,
    ):Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String,
    ):Call<ResponModel>


    @GET("produk")
    fun getProduk(

    ):Call<ResponModel>

    @GET("produkKategori1")
    fun getProduk1(

    ):Call<ResponModel>

    @GET("produkKategori2")
    fun getProduk2(

    ):Call<ResponModel>
    @POST("chekout")
    fun chekout(
        @Body data: Checkout
    ): Call<ResponModel>

    @GET("checkout/user/{id}")
    fun getRiwayat(
        @Path("id") id: Int
    ): Call<ResponModel>
}
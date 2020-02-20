package com.gleam.kiwi.net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST


interface KiwiServiceInterFace {

    @GET("timetable")
    fun getUsersTimeTable(@Field("id") id: String, @Field("token") token: String): Call<TimeTableInfo?>

    @GET("tasks")
    fun getUsersTaskInfo(@Field("id") id: String, @Field("token") token: String): Call<TaskInfo?>

    @POST("user")
    fun getUserInfo(@Field("id") id: String, @Field("hashed_password") hashedPassword: String): Call<UserInfo?>
}

class KiwiService {
    lateinit var retrofit: Retrofit
    private val httpBuilder = OkHttpClient.Builder()


    fun create(serviceClass: Class<KiwiServiceInterFace>): KiwiServiceInterFace {
        val baseUrl = "API's URL"
        val gson = GsonBuilder()
            .serializeNulls()
            .create()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(httpBuilder.build())
            .build()

        return retrofit.create(serviceClass)
    }
}
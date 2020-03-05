package com.gleam.kiwi.net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface KiwiServiceInterFace {

    @POST("timetable")
    fun getUsersTimeTable(@Body userInfo: UserInfo): Call<TimeTableInfo?>

    @POST("tasks")
    fun getUsersTaskInfo(@Body userInfo: UserInfo): Call<TaskInfo?>

    @POST("user")
    fun getUserInfo(@Body userInfo: UserInfo): Call<UserInfo?>
}

class KiwiService {
    lateinit var retrofit: Retrofit
    private val httpBuilder = OkHttpClient.Builder()


    fun create(serviceClass: Class<KiwiServiceInterFace>): KiwiServiceInterFace {
        val baseUrl = "URL"
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
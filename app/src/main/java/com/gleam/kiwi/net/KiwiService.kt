package com.gleam.kiwi.net

import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.User
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface KiwiServiceInterFace {
    @POST("users")
    fun signUp(@Body user: User): Call<Unit>

    @DELETE("users")
    fun deleteUser(@Body user: User): Call<Unit>

    @POST("tokens")
    fun getNewToken(@Body user: User): Call<String?>

    @DELETE("tokens")
    fun deleteToken(@Header("token") token: String): Call<Unit>

    @POST("timetables")
    fun registerTimetable(@Header("token") token: String, @Body timetable: Timetable): Call<Unit>

    @GET("timetables")
    fun getTimetable(@Header("token") token: String): Call<Timetable?>

    @POST("tasks")
    fun registerTasks(@Header("token") token: String, @Body task: Tasks): Call<Unit>

    @GET("tasks")
    fun getTasks(@Header("token") token: String): Call<Tasks?>

}

class KiwiService {
    lateinit var retrofit: Retrofit
    private val httpBuilder = OkHttpClient.Builder()


    fun create(serviceClass: Class<KiwiServiceInterFace>): KiwiServiceInterFace {
        val baseUrl = "http://10.0.2.2:3000"
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
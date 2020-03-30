package com.gleam.kiwi.net

import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.User
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface KiwiServiceInterFace {
    @POST("users")
    suspend fun signUp(@Body user: User)

    @DELETE("users")
    suspend fun revokeUser(@Body user: User)

    @POST("tokens")
    suspend fun getNewToken(@Body user: User): String?

    @POST("timetables")
    suspend fun registerTimetable(@Header("token") token: String, @Body timetable: Timetable)

    @GET("timetables")
    suspend fun getTimetable(@Header("token") token: String): Timetable?

    @POST("tasks")
    suspend fun registerTask(@Header("token") token: String, @Body task: Task)

    @GET("tasks")
    suspend fun getTasks(@Header("token") token: String): Tasks?

    @DELETE("tasks")
    suspend fun removeTask(@Header("token") token: String, @Body id: Int)
}

class KiwiService {
    //private val API_URL = "https://gleam.works:10080"
    private val API_URL = "http://10.0.2.2:3000"
    private lateinit var retrofit: Retrofit
    private val httpBuilder = OkHttpClient.Builder()


    fun create(serviceClass: Class<KiwiServiceInterFace>): KiwiServiceInterFace {
        val gson = GsonBuilder()
            .serializeNulls()
            .create()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(API_URL)
            .client(httpBuilder.build())
            .build()

        return retrofit.create(serviceClass)
    }
}

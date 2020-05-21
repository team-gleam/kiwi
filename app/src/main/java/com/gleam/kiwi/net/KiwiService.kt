package com.gleam.kiwi.net

import com.gleam.kiwi.data.TimetableEntity
import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.User
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface KiwiServiceInterFace {
    @POST("users")
    suspend fun signUp(@Body user: User): Response<Unit>

    @HTTP(method = "DELETE", path = "users", hasBody = true)
    suspend fun revokeUser(@Body user: User): Response<Unit>

    @POST("tokens")
    suspend fun getNewToken(@Body user: User): Response<String?>

    @POST("timetables")
    suspend fun registerTimetable(
        @Header("token") token: String,
        @Body timetable: TimetableEntity
    ): Response<Unit>

    @GET("timetables")
    suspend fun getTimetable(@Header("token") token: String): Response<TimetableEntity?>

    @POST("tasks")
    suspend fun registerTask(@Header("token") token: String, @Body task: Task): Response<Unit>

    @GET("tasks")
    suspend fun getTasks(@Header("token") token: String): Response<Tasks?>

    @HTTP(method = "DELETE", path = "tasks", hasBody = true)
    suspend fun removeTask(@Header("token") token: String, @Body id: Int): Response<Unit>
}

class KiwiService {
    private val API_URL = "https://gleam.works:10080"
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

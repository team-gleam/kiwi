package com.gleam.kiwi.net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.*


interface KiwiServiceInterFace {
    @POST("users")
    fun registsUserInfo(@Body userInfo: UserInfo): Call<Unit>
    @DELETE("users")
    fun deleteUserInfo(@Body userInfo: UserInfo): Call<Unit>

    @POST("tokens")
    fun getNewToken(@Body userInfo: UserInfo): Call<String?>
    @DELETE("tokens")
    fun deleteToken(@Header("token") token: String): Call<Unit>

    @POST("timetables")
    fun registUsersTimeTable(@Header("token") token: String, @Body timetable: TimeTableInfo): Call<Unit>
    @GET("timetables")
    fun getUsersTimeTable(@Header("token") token: String): Call<TimeTableInfo?>

    @POST("tasks")
    fun registUsersTaskInfo(@Header("token") token: String, @Body taskInfo: TaskInfo): Call<Unit>
    @GET("tasks")
    fun getUsersTaskInfo(@Header("token") token: String): Call<TaskInfo?>

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
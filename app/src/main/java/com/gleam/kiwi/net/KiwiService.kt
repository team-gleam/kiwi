package com.gleam.kiwi.net

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


interface KiwiServiceInterFace {
    @GET("timetable")
    fun getUsersTimeTable(@Field("id") id: String, @Field("token") token: String): Call<TimeTableInfo>

    @GET("tasks")
    fun getUsersTaskInfo(@Field("id") id: String, @Field("token") token: String): Call<TaskInfo>

    @POST("user")
    fun getUserInfo(@Field("id") id: String, @Field("hashed_password") hashedPassword: String): Call<UserInfo>
}

class KiwiService {

    val service: KiwiServiceInterFace = create(
        KiwiServiceInterFace::class.java
    )
    lateinit var retrofit: Retrofit
    val httpBuilder: OkHttpClient.Builder
        get() {
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val original = chain.request()
                    val request = original
                        .newBuilder()
                        .method(original.method(), original.body())
                        .build()
                    return@Interceptor chain.proceed(request)
                })
                .readTimeout(20, TimeUnit.SECONDS)

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            httpClient.addInterceptor(loggingInterceptor)

            return httpClient
        }

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
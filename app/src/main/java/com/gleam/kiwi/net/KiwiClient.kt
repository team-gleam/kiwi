package com.gleam.kiwi.net

import android.accounts.NetworkErrorException
import java.io.IOException

interface KiwiClientInterface {
    fun getUserTimeTable(id: String, token: String): TimeTableInfo?
    fun getTaskInfo(id: String, token: String): TaskInfo?
    fun getUserInfo(id: String, token: String): UserInfo?
}

class KiwiClient(private val kiwiService: KiwiServiceInterFace) :
    KiwiClientInterface {
    override fun getUserTimeTable(id: String, token: String): TimeTableInfo? {
        return try {
            val response = kiwiService.getUsersTimeTable(UserInfo(id, token)).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                throw NetworkErrorException("Connection Error")
            }
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getTaskInfo(id: String, token: String): TaskInfo? {
        return try {
            val response = kiwiService.getUsersTaskInfo(UserInfo(id, token)).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                throw NetworkErrorException("Connection Error")
            }
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getUserInfo(id: String, token: String): UserInfo? {
        return try {
            val response = kiwiService.getUserInfo(UserInfo(id, token)).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                throw NetworkErrorException("Connection Error")
            }
        } catch (e: IOException) {
            throw e
        }
    }

}
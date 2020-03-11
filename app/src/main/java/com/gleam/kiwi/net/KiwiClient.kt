package com.gleam.kiwi.net

import android.accounts.NetworkErrorException
import retrofit2.Call
import java.io.IOException

interface KiwiClientInterface {
    fun registUserInfo(userInfo: UserInfo)
    fun deleteUserInfo(userInfo: UserInfo)

    fun getNewToken(userInfo: UserInfo): String?
    fun deleteToken(token: String)

    fun registUsersTimeTable(token: String, timetable: TimeTableInfo)
    fun getUsersTimeTable(token: String): TimeTableInfo?

    fun registUsersTaskInfo(token: String, taskInfo: TaskInfo)
    fun getUsersTaskInfo(token: String): TaskInfo?
}

class KiwiClient(private val kiwiService: KiwiServiceInterFace) :
    KiwiClientInterface {
    override fun registUserInfo(userInfo: UserInfo) {
        try {
            val response = kiwiService.registsUserInfo(userInfo).execute()
        } catch (e: IOException) {
            throw e
        }
    }

    override fun deleteUserInfo(userInfo: UserInfo) {
        try {
            val response = kiwiService.deleteUserInfo(userInfo).execute()
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getNewToken(userInfo: UserInfo): String? {
        return try {
            val response = kiwiService.getNewToken(userInfo).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                throw NetworkErrorException("Connection Error")
            }
        } catch (e: IOException) {
            throw e
        }
    }

    override fun deleteToken(token: String) {
        try {
            val response = kiwiService.deleteToken(token)
        } catch (e: IOException) {
            throw e
        }
    }

    override fun registUsersTimeTable(token: String, timetable: TimeTableInfo) {
        try {
            val response = kiwiService.registUsersTimeTable(token, timetable).execute()
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getUsersTimeTable(token: String): TimeTableInfo? {
        return try {
            val response = kiwiService.getUsersTimeTable(token).execute()
        } catch (e: IOException) {
            throw e
        }
    }

    override fun registUsersTaskInfo(token: String, taskInfo: TaskInfo) {
        try {
            val response = kiwiService.registUsersTaskInfo(token, taskInfo).execute()
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getUsersTaskInfo(token: String): TaskInfo? {
        return try {
            val response = kiwiService.getUsersTaskInfo(token).execute()
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
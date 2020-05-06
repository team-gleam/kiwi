package com.gleam.kiwi.net

import android.util.Log
import com.gleam.kiwi.data.DayLessons
import com.gleam.kiwi.data.TimetableEntity
import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.User
import com.google.gson.Gson
import java.net.SocketTimeoutException

interface KiwiClientInterface {
    suspend fun signUp(user: User): NetworkStatus
    suspend fun signIn(user: User): NetworkStatus
    suspend fun revokeUser(user: User): NetworkStatus

    suspend fun registerTimetable(timetable: Timetable): NetworkStatus
    suspend fun getTimetable(): NetworkStatusWithTimeTable

    suspend fun registerTask(task: Task): NetworkStatus
    suspend fun getTasks(): NetworkStatusWithTasks
    suspend fun removeTask(id: Int): NetworkStatus
}

class KiwiClient(private val kiwiService: KiwiServiceInterFace) : KiwiClientInterface {
    private lateinit var token: String
    override suspend fun signUp(user: User): NetworkStatus {
        return try {
            when (kiwiService.signUp(user).code()) {
                200 -> NetworkStatus.Success
                404 -> NetworkStatus.NotFound
                else -> NetworkStatus.Error
            }
        } catch (e: SocketTimeoutException) {
            NetworkStatus.Timeout
        }
    }

    override suspend fun signIn(user: User): NetworkStatus {
        return try {
            val res = kiwiService.getNewToken(user)
            return when (res.code()) {
                200 -> {
                    res.body()?.let {
                        token = it
                        NetworkStatus.Success
                    } ?: NetworkStatus.NotFound
                }
                404 -> NetworkStatus.NotFound
                else -> NetworkStatus.Error
            }
        } catch (e: SocketTimeoutException) {
            NetworkStatus.Timeout
        }
    }

    override suspend fun revokeUser(user: User): NetworkStatus {
        return try {
            when (kiwiService.revokeUser(user).code()) {
                200 -> NetworkStatus.Success
                404 -> NetworkStatus.NotFound
                else -> NetworkStatus.Error
            }
        } catch (e: SocketTimeoutException) {
            NetworkStatus.Timeout
        }
    }

    override suspend fun registerTimetable(timetable: Timetable): NetworkStatus {
        return try {
            when (kiwiService.registerTimetable(token, timetable).code()) {
                200 -> NetworkStatus.Success
                404 -> NetworkStatus.NotFound
                else -> NetworkStatus.Error
            }
        } catch (e: SocketTimeoutException) {
            NetworkStatus.Timeout
        }
    }

    override suspend fun getTimetable(): NetworkStatusWithTimeTable {
        return try {
            val res = kiwiService.getTimetable(token)
            when (res.code()) {
                200 -> NetworkStatusWithTimeTable.Success(res.body())
                404 -> NetworkStatusWithTimeTable.NotFound
                else -> NetworkStatusWithTimeTable.Error
            }
        } catch (e: SocketTimeoutException) {
            NetworkStatusWithTimeTable.Timeout
        }
    }

    override suspend fun registerTask(task: Task): NetworkStatus {
        return try {
            when (kiwiService.registerTask(token, task).code()) {
                200 -> NetworkStatus.Success
                404 -> NetworkStatus.NotFound
                else -> NetworkStatus.Error
            }
        } catch (e: SocketTimeoutException) {
            NetworkStatus.Timeout
        }
    }

    override suspend fun getTasks(): NetworkStatusWithTasks {
        return try {
            val res = kiwiService.getTasks(token)
            when (res.code()) {
                200 -> NetworkStatusWithTasks.Success(res.body())
                404 -> NetworkStatusWithTasks.NotFound
                else -> NetworkStatusWithTasks.Error
            }
        } catch (e: SocketTimeoutException) {
            NetworkStatusWithTasks.Timeout
        }
    }

    override suspend fun removeTask(id: Int): NetworkStatus {
        return try {
            when (kiwiService.removeTask(token, id).code()) {
                200 -> NetworkStatus.Success
                404 -> NetworkStatus.NotFound
                else -> NetworkStatus.Error
            }
        } catch (e: SocketTimeoutException) {
            NetworkStatus.Timeout
        }
    }
}

enum class NetworkStatus {
    Success,
    NotFound,
    Timeout,
    Error
}

sealed class NetworkStatusWithTimeTable {
    object NotFound : NetworkStatusWithTimeTable()
    object Timeout : NetworkStatusWithTimeTable()
    object Error : NetworkStatusWithTimeTable()
    data class Success(val timetable: Timetable?) : NetworkStatusWithTimeTable()
}

sealed class NetworkStatusWithTasks {
    object NotFound : NetworkStatusWithTasks()
    object Timeout : NetworkStatusWithTasks()
    object Error : NetworkStatusWithTasks()
    data class Success(val tasks: Tasks?) : NetworkStatusWithTasks()
}

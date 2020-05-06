package com.gleam.kiwi.net

import com.gleam.kiwi.data.toEntity
import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.User
import java.net.SocketTimeoutException

interface KiwiClientInterface {
    suspend fun signUp(user: User): NetworkStatus
    suspend fun signIn(user: User): NetworkStatus
    suspend fun revokeUser(user: User): NetworkStatus

    suspend fun registerTimetable(timetable: Timetable): FetchResult<Unit>
    suspend fun getTimetable(): FetchResult<Timetable>

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

    override suspend fun registerTimetable(timetable: Timetable): FetchResult<Unit> {
        return try {
            when (kiwiService.registerTimetable(token, timetable.toEntity()).code()) {
                200 -> FetchResult.Success(Unit)
                404 -> FetchResult.NotFound
                else -> FetchResult.Error
            }
        } catch (e: SocketTimeoutException) {
            FetchResult.Timeout
        }
    }

    override suspend fun getTimetable(): FetchResult<Timetable> =
        try {
            val res = kiwiService.getTimetable(token)
            val code = res.code()
            val timetable = res.body()?.toTimetable()

            when {
                code == 200 && timetable != null -> FetchResult.Success(timetable)
                code == 404 -> FetchResult.NotFound
                else -> FetchResult.Error
            }
        } catch (e: SocketTimeoutException) {
            FetchResult.Timeout
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

sealed class FetchResult<out R> {
    object NotFound : FetchResult<Nothing>()
    object Timeout : FetchResult<Nothing>()
    object Error : FetchResult<Nothing>()
    class Success<R : Any>(val result: R) : FetchResult<R>()
}

enum class NetworkStatus {
    Success,
    NotFound,
    Timeout,
    Error
}

sealed class NetworkStatusWithTasks {
    object NotFound : NetworkStatusWithTasks()
    object Timeout : NetworkStatusWithTasks()
    object Error : NetworkStatusWithTasks()
    data class Success(val tasks: Tasks?) : NetworkStatusWithTasks()
}

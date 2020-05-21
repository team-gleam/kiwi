package com.gleam.kiwi.net

import com.gleam.kiwi.data.toEntity
import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.User
import java.net.SocketTimeoutException

interface KiwiClientInterface {
    suspend fun signUp(user: User): FetchResult<Unit>
    suspend fun signIn(user: User): FetchResult<Unit>
    suspend fun revokeUser(user: User): FetchResult<Unit>

    suspend fun registerTimetable(timetable: Timetable): FetchResult<Unit>
    suspend fun getTimetable(): FetchResult<Timetable>

    suspend fun registerTask(task: Task): FetchResult<Unit>
    suspend fun getTasks(): FetchResult<Tasks?>
    suspend fun removeTask(id: Int): FetchResult<Unit>
}

class KiwiClient(private val kiwiService: KiwiServiceInterFace) : KiwiClientInterface {
    private lateinit var token: String
    override suspend fun signUp(user: User): FetchResult<Unit> {
        return try {
            when (kiwiService.signUp(user).code()) {
                200 -> FetchResult.Success(Unit)
                404 -> FetchResult.NotFound
                else -> FetchResult.Unexpected
            }
        } catch (e: SocketTimeoutException) {
            FetchResult.Timeout
        }
    }

    override suspend fun signIn(user: User): FetchResult<Unit> {
        return try {
            val res = kiwiService.getNewToken(user)
            return when (res.code()) {
                200 -> {
                    res.body()?.let {
                        token = it
                        FetchResult.Success(Unit)
                    } ?: FetchResult.NotFound
                }
                404 -> FetchResult.NotFound
                else -> FetchResult.Unexpected
            }
        } catch (e: SocketTimeoutException) {
            FetchResult.Timeout
        }
    }

    override suspend fun revokeUser(user: User): FetchResult<Unit> {
        return try {
            when (kiwiService.revokeUser(user).code()) {
                200 -> FetchResult.Success(Unit)
                404 -> FetchResult.NotFound
                else -> FetchResult.Unexpected
            }
        } catch (e: SocketTimeoutException) {
            FetchResult.Timeout
        }
    }

    override suspend fun registerTimetable(timetable: Timetable): FetchResult<Unit> {
        return try {
            when (kiwiService.registerTimetable(token, timetable.toEntity()).code()) {
                200 -> FetchResult.Success(Unit)
                404 -> FetchResult.NotFound
                else -> FetchResult.Unexpected
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
                else -> FetchResult.Unexpected
            }
        } catch (e: SocketTimeoutException) {
            FetchResult.Timeout
        }

    override suspend fun registerTask(task: Task): FetchResult<Unit> {
        return try {
            when (kiwiService.registerTask(token, task).code()) {
                200 -> FetchResult.Success(Unit)
                404 -> FetchResult.NotFound
                else -> FetchResult.Unexpected
            }
        } catch (e: SocketTimeoutException) {
            FetchResult.Timeout
        }
    }

    override suspend fun getTasks(): FetchResult<Tasks?> {
        return try {
            val res = kiwiService.getTasks(token)
            when (res.code()) {
                200 -> FetchResult.Success(res.body())
                404 -> FetchResult.NotFound
                else -> FetchResult.Unexpected
            }
        } catch (e: SocketTimeoutException) {
            FetchResult.Timeout
        }
    }

    override suspend fun removeTask(id: Int): FetchResult<Unit> {
        return try {
            when (kiwiService.removeTask(token, id).code()) {
                200 -> FetchResult.Success(Unit)
                404 -> FetchResult.NotFound
                else -> FetchResult.Unexpected
            }
        } catch (e: SocketTimeoutException) {
            FetchResult.Timeout
        }
    }
}

sealed class FetchResult<out R> {
    object NotFound : FetchResult<Nothing>()
    object Timeout : FetchResult<Nothing>()
    object Unexpected : FetchResult<Nothing>()
    class Success<R : Any?>(val result: R) : FetchResult<R>()
}

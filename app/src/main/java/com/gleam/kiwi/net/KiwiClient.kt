package com.gleam.kiwi.net

import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface KiwiClientInterface {
    suspend fun signUp(user: User)
    suspend fun signIn(user: User)
    suspend fun revokeUser(user: User)

    suspend fun registerTimetable(token: String, timetable: Timetable)
    suspend fun getTimetable(token: String): Timetable?

    suspend fun registerTasks(token: String, task: Tasks)
    suspend fun getTasks(token: String): Tasks?
}

class KiwiClient(private val kiwiService: KiwiServiceInterFace) : KiwiClientInterface {
    private lateinit var token: String

    override suspend fun signUp(user: User) {
        withContext(Dispatchers.IO) {
            kiwiService.signUp(user).execute()
        }
    }

    override suspend fun signIn(user: User) {
        withContext(Dispatchers.IO) {
            token = kiwiService.getNewToken(user).execute().takeIf { it.isSuccessful }?.body()
                ?: return@withContext
        }
    }

    override suspend fun revokeUser(user: User) {
        withContext(Dispatchers.IO) {
            kiwiService.revokeUser(user).execute()
        }
    }

    override suspend fun registerTimetable(token: String, timetable: Timetable) {
        withContext(Dispatchers.IO) {
            kiwiService.registerTimetable(token, timetable).execute()
        }
    }

    override suspend fun getTimetable(token: String): Timetable? {
        return withContext(Dispatchers.IO) {
            kiwiService.getTimetable(token).execute().takeIf { it.isSuccessful }?.body()
        }
    }

    override suspend fun registerTasks(token: String, task: Tasks) {
        withContext(Dispatchers.IO) {
            kiwiService.registerTasks(token, task).execute()
        }
    }

    override suspend fun getTasks(token: String): Tasks? {
        return withContext(Dispatchers.IO) {
            kiwiService.getTasks(token).execute().takeIf { it.isSuccessful }?.body()
        }
    }
}
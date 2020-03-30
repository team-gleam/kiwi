package com.gleam.kiwi.net

import com.gleam.kiwi.model.Task
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.User

interface KiwiClientInterface {
    suspend fun signUp(user: User)
    suspend fun signIn(user: User)
    suspend fun revokeUser(user: User)

    suspend fun registerTimetable(timetable: Timetable)
    suspend fun getTimetable(): Timetable?

    suspend fun registerTask(task: Task)
    suspend fun getTasks(): Tasks?
    suspend fun removeTask(id: Int)
}

class KiwiClient(private val kiwiService: KiwiServiceInterFace) : KiwiClientInterface {
   // private lateinit var token: String
    private var token = "hoge"
    override suspend fun signUp(user: User) {
        kiwiService.signUp(user)
    }

    override suspend fun signIn(user: User) {
        token = kiwiService.getNewToken(user) ?: return
    }

    override suspend fun revokeUser(user: User) {
        kiwiService.revokeUser(user)
    }

    override suspend fun registerTimetable(timetable: Timetable) {
        return kiwiService.registerTimetable(token, timetable)
    }

    override suspend fun getTimetable(): Timetable? {
        return kiwiService.getTimetable(token)
    }

    override suspend fun registerTask(task: Task) {
        kiwiService.registerTask(token, task)
    }

    override suspend fun getTasks(): Tasks? {
        return kiwiService.getTasks(token)
    }

    override suspend fun removeTask(id: Int) {
        kiwiService.removeTask(token, id)
    }
}

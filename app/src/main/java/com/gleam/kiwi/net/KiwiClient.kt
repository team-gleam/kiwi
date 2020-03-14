package com.gleam.kiwi.net

import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.User

interface KiwiClientInterface {
    fun signUp(user: User)
    fun revokeUser(user: User)

    fun getNewToken(user: User): String?
    fun revokeToken(token: String)

    fun registerTimetable(token: String, timetable: Timetable)
    fun getTimetable(token: String): Timetable?

    fun registerTasks(token: String, task: Tasks)
    fun getTasks(token: String): Tasks?
}

class KiwiClient(private val kiwiService: KiwiServiceInterFace) :
    KiwiClientInterface {
    override fun signUp(user: User) {
        kiwiService.signUp(user).execute()
    }

    override fun revokeUser(user: User) {
        kiwiService.revokeUser(user).execute()
    }

    override fun getNewToken(user: User): String? {
        return kiwiService.getNewToken(user).execute().let {
            if (it.isSuccessful) {
                it.body()
            }
            null
        }
    }

    override fun revokeToken(token: String) {
        kiwiService.revokeToken(token)
    }

    override fun registerTimetable(token: String, timetable: Timetable) {
        kiwiService.registerTimetable(token, timetable).execute()
    }

    override fun getTimetable(token: String): Timetable? {
        return kiwiService.getTimetable(token).execute().let {
            if (it.isSuccessful) {
                it.body()
            }
            null
        }
    }

    override fun registerTasks(token: String, task: Tasks) {
        kiwiService.registerTasks(token, task).execute()
    }

    override fun getTasks(token: String): Tasks? {
        return kiwiService.getTasks(token).execute().let {
            if (it.isSuccessful) {
                it.body()
            }
            null
        }
    }

}
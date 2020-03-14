package com.gleam.kiwi.net

import android.accounts.NetworkErrorException
import com.gleam.kiwi.model.Tasks
import com.gleam.kiwi.model.Timetable
import com.gleam.kiwi.model.User
import java.io.IOException

interface KiwiClientInterface {
    fun signUp(user: User)
    fun deleteUser(user: User)

    fun getNewToken(user: User): String?
    fun deleteToken(token: String)

    fun registerTimetable(token: String, timetable: Timetable)
    fun getTimetable(token: String): Timetable?

    fun registerTasks(token: String, task: Tasks)
    fun getTasks(token: String): Tasks?
}

class KiwiClient(private val kiwiService: KiwiServiceInterFace) :
    KiwiClientInterface {
    override fun signUp(user: User) {
        try {
            kiwiService.signUp(user).execute()
        } catch (e: IOException) {
            throw e
        }
    }

    override fun deleteUser(user: User) {
        try {
            kiwiService.deleteUser(user).execute()
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getNewToken(user: User): String? {
        return try {
            kiwiService.getNewToken(user).execute().let {
                if (it.isSuccessful) {
                    it.body()
                } else {
                    throw NetworkErrorException("Connection Error")
                }
            }
        } catch (e: IOException) {
            throw e
        }
    }

    override fun deleteToken(token: String) {
        try {
            kiwiService.deleteToken(token)
        } catch (e: IOException) {
            throw e
        }
    }

    override fun registerTimetable(token: String, timetable: Timetable) {
        try {
            kiwiService.registerTimetable(token, timetable).execute()
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getTimetable(token: String): Timetable? {
        return try {
            kiwiService.getTimetable(token).execute().let {
                if (it.isSuccessful) {
                    it.body()
                } else {
                    throw NetworkErrorException("Connection Error")
                }
            }
        } catch (e: IOException) {
            throw e
        }
    }

    override fun registerTasks(token: String, task: Tasks) {
        try {
            kiwiService.registerTasks(token, task).execute()
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getTasks(token: String): Tasks? {
        return try {
            kiwiService.getTasks(token).execute().let {
                if (it.isSuccessful) {
                    it.body()
                } else {
                    throw NetworkErrorException("Connection Error")
                }
            }
        } catch (e: IOException) {
            throw e
        }
    }

}
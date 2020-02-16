package com.gleam.kiwi

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gleam.kiwi.net.KiwiClient
import com.gleam.kiwi.net.KiwiService
import com.gleam.kiwi.net.KiwiServiceInterFace
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyAsyncTask().execute()

    }

    inner class MyAsyncTask: AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            val kiwiService = KiwiService().create(KiwiServiceInterFace::class.java)
            val client = KiwiClient(kiwiService)

            return client.getUserTimeTable().toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.i("MainActivityResult", result)
        }
    }

}

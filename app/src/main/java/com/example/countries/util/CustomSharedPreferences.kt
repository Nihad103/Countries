package com.example.countries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {


    companion object {
        var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: CustomSharedPreferences? = null
        private var lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
           instance ?: makeCustomSharedPreferences(context)
        }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    fun saveTime(time: Long) {
        sharedPreferences?.edit(commit = true) {
            putLong("time",time)
        }
    }

    fun getTime() = sharedPreferences?.getLong("time", 0)

}
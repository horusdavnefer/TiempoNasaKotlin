package com.tiempo.nasa.helpers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


class Preferences(private val context: Context) {
    private val mPreferences: SharedPreferences

    init {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun cleanData() {
        val editor = mPreferences.edit()
        editor.clear()
        editor.apply()
    }

    var isData: Boolean
        get() = mPreferences.getBoolean("isData", false)
        set(auth) {
            val editor = mPreferences.edit()
            editor.putBoolean("isData", auth)
            editor.apply()
        }


}

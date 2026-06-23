package com.example.companytime.data

import android.content.Context
import androidx.core.content.edit

class TokenStorage(
    private val context: Context
) {
    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveToken(token: String){
        prefs.edit { putString("token", token) }
    }

    fun getToken(): String?{
        return prefs.getString("token", null)
    }

    fun clearToken() {
        prefs.edit { remove("token") }
    }

    fun saveUserId(id: Long) {
        prefs.edit { putLong("userId", id) }
    }

    fun getUserId(): Long {
        return prefs.getLong("userId", -1)
    }

    fun clear() {
        prefs.edit { this.clear() }
    }
}
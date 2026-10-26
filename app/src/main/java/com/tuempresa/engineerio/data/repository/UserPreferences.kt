package com.tuempresa.engineerio.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.tuempresa.engineerio.domain.model.UserProfile

class UserPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("systego_prefs", Context.MODE_PRIVATE)

    fun getUserProfile(): UserProfile? {
        val fullName = prefs.getString(KEY_FULL_NAME, null) ?: return null
        val nickname = prefs.getString(KEY_NICKNAME, "") ?: ""
        val avatar = prefs.getString(KEY_AVATAR, "⚡") ?: "⚡"
        val role = prefs.getString(KEY_ROLE, "Arquitecto de Sistemas") ?: "Arquitecto de Sistemas"
        val level = prefs.getInt(KEY_LEVEL, 1)

        return UserProfile(
            fullName = fullName,
            nickname = nickname,
            avatarEmoji = avatar,
            titleRole = role,
            level = level
        )
    }

    fun saveUserProfile(profile: UserProfile) {
        prefs.edit().apply {
            putString(KEY_FULL_NAME, profile.fullName)
            putString(KEY_NICKNAME, profile.nickname)
            putString(KEY_AVATAR, profile.avatarEmoji)
            putString(KEY_ROLE, profile.titleRole)
            putInt(KEY_LEVEL, profile.level)
            apply()
        }
    }

    fun clearProfile() {
        prefs.edit().apply {
            remove(KEY_FULL_NAME)
            remove(KEY_NICKNAME)
            remove(KEY_AVATAR)
            remove(KEY_ROLE)
            remove(KEY_LEVEL)
            apply()
        }
    }

    companion object {
        private const val KEY_FULL_NAME = "key_full_name"
        private const val KEY_NICKNAME = "key_nickname"
        private const val KEY_AVATAR = "key_avatar"
        private const val KEY_ROLE = "key_role"
        private const val KEY_LEVEL = "key_level"
    }
}

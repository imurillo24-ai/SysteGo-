package com.tuempresa.engineerio.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.tuempresa.engineerio.domain.model.UserProfile
import com.tuempresa.engineerio.domain.model.UserStats

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("systego_user_prefs", Context.MODE_PRIVATE)

    fun getUserProfile(): UserProfile? {
        val name = prefs.getString("user_name", null) ?: return null
        val nickname = prefs.getString("user_nickname", "@usuario") ?: "@usuario"
        val avatar = prefs.getString("user_avatar", "⚡") ?: "⚡"
        val role = prefs.getString("user_role", "Ingeniero SysteGo") ?: "Ingeniero SysteGo"
        return UserProfile(fullName = name, nickname = nickname, avatarEmoji = avatar, titleRole = role)
    }

    fun saveUserProfile(profile: UserProfile) {
        prefs.edit().apply {
            putString("user_name", profile.fullName)
            putString("user_nickname", profile.nickname)
            putString("user_avatar", profile.avatarEmoji)
            putString("user_role", profile.titleRole)
            apply()
        }
    }

    fun clearProfile() {
        prefs.edit().clear().apply()
    }

    // --- PERSISTENCIA DE ESTADÍSTICAS Y GAMIFICACIÓN ---

    fun getUserStats(): UserStats {
        val xp = prefs.getInt("user_xp", 0)
        val hearts = prefs.getInt("user_hearts", 5)
        val completedNodesString = prefs.getString("completed_nodes", "node-oop") ?: "node-oop"
        val completedNodesList = completedNodesString.split(",").filter { it.isNotBlank() }

        return CourseRepository.initialUserStats.copy(
            xp = xp,
            hearts = hearts,
            completedNodeIds = completedNodesList
        )
    }

    fun saveUserStats(stats: UserStats) {
        prefs.edit().apply {
            putInt("user_xp", stats.xp)
            putInt("user_hearts", stats.hearts)
            putString("completed_nodes", stats.completedNodeIds.joinToString(","))
            apply()
        }
    }
}
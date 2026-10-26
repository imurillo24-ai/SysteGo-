package com.tuempresa.engineerio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tuempresa.engineerio.core.theme.SysteGoTheme
import com.tuempresa.engineerio.data.repository.CourseRepository
import com.tuempresa.engineerio.data.repository.UserPreferences
import com.tuempresa.engineerio.domain.model.UserProfile
import com.tuempresa.engineerio.domain.model.ViewMode
import com.tuempresa.engineerio.presentation.auth.LoginScreen
import com.tuempresa.engineerio.presentation.common.TopNav
import com.tuempresa.engineerio.presentation.map.MapScreen
import com.tuempresa.engineerio.presentation.profile.ProfileScreen
import com.tuempresa.engineerio.presentation.quiz.QuizScreen
import com.tuempresa.engineerio.presentation.quiz.TheoryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPrefs = UserPreferences(applicationContext)

        setContent {
            SysteGoTheme {
                var currentUserProfile by remember { mutableStateOf(userPrefs.getUserProfile()) }
                var viewMode by remember {
                    mutableStateOf(if (currentUserProfile == null) ViewMode.LOGIN else ViewMode.MAP)
                }

                var activeLessonId by remember { mutableStateOf("node-oop") }
                var activeUnitId by remember { mutableStateOf("unit-1") }

                var userStats by remember { mutableStateOf(CourseRepository.initialUserStats) }
                var units by remember { mutableStateOf(CourseRepository.unitsData) }
                var nodes by remember { mutableStateOf(CourseRepository.lessonNodesData) }

                val activeUnit = units.find { it.id == activeUnitId } ?: units.first()
                val currentTheoryModule = CourseRepository.theoryModules[activeLessonId] ?: CourseRepository.theoryModules.values.first()
                val currentQuizQuestions = CourseRepository.quizQuestions[activeLessonId] ?: CourseRepository.quizQuestions["node-oop"]!!

                fun completeLessonAndUnlockNext(lessonId: String, earnedXp: Int) {
                    val completedIndex = nodes.indexOfFirst { it.id == lessonId }
                    val updatedNodes = nodes.mapIndexed { index, node ->
                        if (node.id == lessonId) {
                            node.copy(status = com.tuempresa.engineerio.domain.model.NodeStatus.COMPLETED)
                        } else if (completedIndex >= 0 && index == completedIndex + 1 && node.status == com.tuempresa.engineerio.domain.model.NodeStatus.LOCKED) {
                            node.copy(status = com.tuempresa.engineerio.domain.model.NodeStatus.CURRENT)
                        } else {
                            node
                        }
                    }
                    nodes = updatedNodes

                    val completedCount = updatedNodes.count { it.status == com.tuempresa.engineerio.domain.model.NodeStatus.COMPLETED }
                    val newProgress = ((completedCount.toFloat() / updatedNodes.size) * 100).toInt()

                    units = units.map { u ->
                        if (u.id == activeUnitId) u.copy(progressPercent = newProgress) else u
                    }

                    userStats = userStats.copy(
                        xp = userStats.xp + earnedXp,
                        completedNodeIds = if (lessonId in userStats.completedNodeIds) userStats.completedNodeIds else userStats.completedNodeIds + lessonId
                    )
                }

                Scaffold(
                    topBar = {
                        if (viewMode != ViewMode.LOGIN) {
                            TopNav(
                                viewMode = viewMode.name,
                                xp = userStats.xp,
                                hearts = userStats.hearts,
                                soundEnabled = userStats.soundEnabled,
                                onToggleSound = {
                                    userStats = userStats.copy(soundEnabled = !userStats.soundEnabled)
                                },
                                onCloseLesson = { viewMode = ViewMode.MAP },
                                onOpenDrawer = { viewMode = ViewMode.PROFILE },
                                userProfile = currentUserProfile,
                                onOpenProfile = { viewMode = ViewMode.PROFILE }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(if (viewMode == ViewMode.LOGIN) PaddingValues(0.dp) else innerPadding)
                    ) {
                        when (viewMode) {
                            ViewMode.LOGIN -> {
                                LoginScreen(
                                    onLoginSuccess = { profile ->
                                        userPrefs.saveUserProfile(profile)
                                        currentUserProfile = profile
                                        viewMode = ViewMode.MAP
                                    },
                                    onGuestLogin = {
                                        val guestProfile = UserProfile(
                                            fullName = "Usuario Invitado",
                                            nickname = "@invitado_demo",
                                            avatarEmoji = "⚡",
                                            titleRole = "Ingeniero Cyberpunk"
                                        )
                                        userPrefs.saveUserProfile(guestProfile)
                                        currentUserProfile = guestProfile
                                        viewMode = ViewMode.MAP
                                    }
                                )
                            }
                            ViewMode.MAP -> {
                                MapScreen(
                                    activeUnit = activeUnit,
                                    nodes = nodes,
                                    onStartTheory = { lessonId ->
                                        activeLessonId = lessonId
                                        viewMode = ViewMode.THEORY
                                    },
                                    onStartQuiz = { lessonId ->
                                        activeLessonId = lessonId
                                        viewMode = ViewMode.QUIZ
                                    },
                                    onSelectUnit = { unitId ->
                                        activeUnitId = unitId
                                    },
                                    availableUnits = units
                                )
                            }
                            ViewMode.THEORY -> {
                                TheoryScreen(
                                    module = currentTheoryModule,
                                    onContinue = { viewMode = ViewMode.QUIZ },
                                    onBack = { viewMode = ViewMode.MAP }
                                )
                            }
                            ViewMode.QUIZ -> {
                                QuizScreen(
                                    questions = currentQuizQuestions,
                                    onCompleteQuiz = { earnedXp ->
                                        completeLessonAndUnlockNext(activeLessonId, earnedXp)
                                        viewMode = ViewMode.MAP
                                    },
                                    onExit = { viewMode = ViewMode.MAP },
                                    currentHearts = userStats.hearts,
                                    onDeductHeart = {
                                        userStats = userStats.copy(hearts = maxOf(0, userStats.hearts - 1))
                                    }
                                )
                            }
                            ViewMode.PROFILE -> {
                                currentUserProfile?.let { profile ->
                                    ProfileScreen(
                                        userProfile = profile,
                                        userStats = userStats,
                                        onLogout = {
                                            userPrefs.clearProfile()
                                            currentUserProfile = null
                                            viewMode = ViewMode.LOGIN
                                        },
                                        onBackToMap = {
                                            viewMode = ViewMode.MAP
                                        }
                                    )
                                } ?: run {
                                    viewMode = ViewMode.LOGIN
                                }
                            }
                            else -> {
                                viewMode = ViewMode.MAP
                            }
                        }
                    }
                }
            }
        }
    }
}

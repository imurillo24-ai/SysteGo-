package com.tuempresa.engineerio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.tuempresa.engineerio.core.theme.SysteGoTheme
import com.tuempresa.engineerio.data.repository.CourseRepository
import com.tuempresa.engineerio.domain.model.ViewMode
import com.tuempresa.engineerio.presentation.common.TopNav
import com.tuempresa.engineerio.presentation.map.MapScreen
import com.tuempresa.engineerio.presentation.quiz.QuizScreen
import com.tuempresa.engineerio.presentation.quiz.TheoryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SysteGoTheme {
                // Estados globales de la aplicación migrados desde React
                var viewMode by remember { mutableStateOf(ViewMode.MAP) }
                var activeLessonId by remember { mutableStateOf("node-oop") }
                var activeUnitId by remember { mutableStateOf("unit-1") }

                var userStats by remember { mutableStateOf(CourseRepository.initialUserStats) }
                var units by remember { mutableStateOf(CourseRepository.unitsData) }
                var nodes by remember { mutableStateOf(CourseRepository.lessonNodesData) }

                val activeUnit = units.find { it.id == activeUnitId } ?: units.first()
                val currentTheoryModule = CourseRepository.theoryModules[activeLessonId] ?: CourseRepository.theoryModules.values.first()
                val currentQuizQuestions = CourseRepository.quizQuestions[activeLessonId] ?: CourseRepository.quizQuestions.values.first()

                Scaffold(
                    topBar = {
                        TopNav(
                            viewMode = viewMode.name,
                            xp = userStats.xp,
                            hearts = userStats.hearts,
                            soundEnabled = userStats.soundEnabled,
                            onToggleSound = {
                                userStats = userStats.copy(soundEnabled = !userStats.soundEnabled)
                            },
                            onCloseLesson = { viewMode = ViewMode.MAP },
                            onOpenDrawer = {}
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (viewMode) {
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
                                        userStats = userStats.copy(xp = userStats.xp + earnedXp)
                                        viewMode = ViewMode.MAP
                                    },
                                    onExit = { viewMode = ViewMode.MAP },
                                    currentHearts = userStats.hearts,
                                    onDeductHeart = {
                                        userStats = userStats.copy(hearts = maxOf(0, userStats.hearts - 1))
                                    }
                                )
                            }
                            else -> {
                                // Las demás pantallas (Leaderboard, Profile, Practice)
                                // se pueden enrutar de forma análoga.
                            }
                        }
                    }
                }
            }
        }
    }
}

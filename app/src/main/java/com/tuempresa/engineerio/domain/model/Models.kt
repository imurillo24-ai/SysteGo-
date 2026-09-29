package com.tuempresa.engineerio.domain.model

// ENUMS (Reemplazan los union types de TypeScript)
enum class ViewMode { MAP, THEORY, QUIZ, PRACTICE, LEADERBOARD, PROFILE }
enum class NodeStatus { COMPLETED, CURRENT, LOCKED }
enum class NodeType { THEORY, QUIZ, CHEST }
enum class TrendChange { UP, DOWN, SAME }

// DATA CLASSES (Reemplazan las interfaces de TypeScript)
data class CourseUnit(
    val id: String,
    val unitNumber: Int,
    val title: String,
    val description: String,
    val progressPercent: Int,
    val totalLessons: Int,
    val completedLessons: Int,
    val colorScheme: UnitColorScheme
)

data class UnitColorScheme(
    val primary: String,
    val border: String,
    val glow: String
)

data class LessonNode(
    val id: String,
    val unitId: String,
    val title: String,
    val subtitle: String,
    val status: NodeStatus,
    val type: NodeType,
    val icon: String,
    val xpReward: Int,
    val xOffset: Int
)

data class TheoryModule(
    val id: String,
    val lessonId: String,
    val unitId: String,
    val title: String,
    val moduleType: String,
    val duration: String,
    val progressPercent: Int,
    val figureCaption: String,
    val figureUrl: String,
    val summary: String,
    val keyConcepts: List<KeyConcept>,
    val architecturePoints: List<ArchitecturePoint>
)

data class KeyConcept(
    val id: String,
    val name: String,
    val icon: String,
    val color: String, // Se guarda como String (Hex) para mantener la pureza del dominio
    val explanation: String
)

data class ArchitecturePoint(
    val title: String,
    val detail: String
)

data class QuizOption(
    val id: String,
    val label: String,
    val text: String
)

data class CodeSnippet(
    val language: String,
    val lines: List<CodeLine>
)

data class CodeLine(
    val text: String,
    val highlight: String = "normal" // 'keyword', 'string', 'blank', etc.
)

data class QuizQuestion(
    val id: String,
    val lessonId: String,
    val unitId: String,
    val question: String,
    val codeSnippet: CodeSnippet? = null,
    val options: List<QuizOption>,
    val correctOptionId: String,
    val explanation: String,
    val xpReward: Int
)

data class UserStats(
    val xp: Int,
    val hearts: Int,
    val maxHearts: Int,
    val streakDays: Int,
    val completedNodeIds: List<String>,
    val activeUnitId: String,
    val accuracyRate: Int,
    val questionsAnswered: Int,
    val soundEnabled: Boolean
)

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val role: String,
    val company: String,
    val xp: Int,
    val avatar: String,
    val isCurrentUser: Boolean = false,
    val change: TrendChange
)

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val unlocked: Boolean,
    val unlockedAt: String? = null
)

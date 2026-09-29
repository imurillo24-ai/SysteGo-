package com.tuempresa.engineerio.data.repository

import com.tuempresa.engineerio.domain.model.*

object CourseRepository {

    val unitsData = listOf(
        CourseUnit(
            id = "unit-1",
            unitNumber = 1,
            title = "Unit 1",
            description = "Master the basics of System Architecture.",
            progressPercent = 40,
            totalLessons = 5,
            completedLessons = 2,
            colorScheme = UnitColorScheme(
                primary = "#00F0FF",
                border = "#006970",
                glow = "rgba(0, 240, 255, 0.4)"
            )
        ),
        CourseUnit(
            id = "unit-2",
            unitNumber = 2,
            title = "Unit 2",
            description = "Distributed Consensus & Microservices at Scale.",
            progressPercent = 0,
            totalLessons = 6,
            completedLessons = 0,
            colorScheme = UnitColorScheme(
                primary = "#8B5CF6",
                border = "#4C1D95",
                glow = "rgba(139, 92, 246, 0.4)"
            )
        ),
        CourseUnit(
            id = "unit-3",
            unitNumber = 3,
            title = "Unit 3",
            description = "High-Concurrency Backends & Low-Latency Caching.",
            progressPercent = 0,
            totalLessons = 5,
            completedLessons = 0,
            colorScheme = UnitColorScheme(
                primary = "#00FF94",
                border = "#00723F",
                glow = "rgba(0, 255, 148, 0.4)"
            )
        )
    )

    val lessonNodesData = listOf(
        LessonNode(
            id = "node-fundamentals",
            unitId = "unit-1",
            title = "Fundamentals",
            subtitle = "Distributed Systems & Scaling Intro",
            status = NodeStatus.COMPLETED,
            type = NodeType.THEORY,
            icon = "check_circle",
            xpReward = 15,
            xOffset = -30
        ),
        LessonNode(
            id = "node-oop",
            unitId = "unit-1",
            title = "OOP Principles",
            subtitle = "Inheritance, Polymorphism & Contracts",
            status = NodeStatus.CURRENT,
            type = NodeType.QUIZ,
            icon = "school",
            xpReward = 20,
            xOffset = 40
        ),
        LessonNode(
            id = "node-circuits",
            unitId = "unit-1",
            title = "Hardware & Logic",
            subtitle = "Op-Amps & Inversion Circuits",
            status = NodeStatus.LOCKED,
            type = NodeType.QUIZ,
            icon = "lock",
            xpReward = 20,
            xOffset = -20
        ),
        LessonNode(
            id = "node-scaling",
            unitId = "unit-1",
            title = "Horizontal Scaling",
            subtitle = "Load Balancers & State Management",
            status = NodeStatus.LOCKED,
            type = NodeType.QUIZ,
            icon = "lock",
            xpReward = 25,
            xOffset = 30
        ),
        LessonNode(
            id = "node-chest-1",
            unitId = "unit-1",
            title = "Unit 1 Chest",
            subtitle = "Claim Systems Architect Milestone",
            status = NodeStatus.LOCKED,
            type = NodeType.CHEST,
            icon = "inventory_2",
            xpReward = 50,
            xOffset = 0
        )
    )

    val theoryModules = mapOf(
        "node-fundamentals" to TheoryModule(
            id = "theory-1",
            lessonId = "node-fundamentals",
            unitId = "unit-1",
            title = "Level 1: Fundamentals",
            moduleType = "THEORY MODULE",
            duration = "5 mins",
            progressPercent = 10,
            figureCaption = "Fig. 1.0",
            figureUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCVfN5eF-5w2Yffs5NUP1C71tVR-10pLWvg5aKze8ERC7N4pQAlnY5GS_z8eLircwudxRpQfq4ycrJy8ObYifiwuwVhLNhx3xsJ3YuwMPNes5RJoghsGx3Fa5S0qTppRL-tmGkJRprfP0k2NQsq7QE_LBY1Dqo5MwjQd7ErbHo9LB-eno5JfwdhlFRoSrj0UFfJP8NM3cAeb77mxqJDFTy2LGcD7DdjuVsd3z0xkL0-TsHsjWdoTp18mg",
            summary = "Before we start building, we need to understand the foundational components of distributed systems. This module covers core definitions, the shift from monolithic architectures, and the basic principles of scaling.",
            keyConcepts = listOf(
                KeyConcept(
                    id = "scale",
                    name = "Scalability",
                    icon = "bolt",
                    color = "#FF007A",
                    explanation = "Vertical (Scale-up) vs Horizontal (Scale-out). Distributed systems scale by adding identical compute nodes behind traffic distributors rather than buying exponentially costlier hardware."
                ),
                KeyConcept(
                    id = "cluster",
                    name = "Nodes & Clusters",
                    icon = "dns",
                    color = "#00FF94",
                    explanation = "A cluster consists of independent server nodes cooperating via network RPCs. Failure of a single node must never jeopardize cluster availability."
                )
            ),
            architecturePoints = listOf(
                ArchitecturePoint(
                    title = "Single Point of Failure (SPOF)",
                    detail = "In monolithic systems, a crash in one background worker can freeze entire user flows. Micro-clusters isolate state and fail gracefully."
                ),
                ArchitecturePoint(
                    title = "Stateless Service Tier",
                    detail = "Stateless API replicas allow incoming traffic to be round-robined or hash-routed seamlessly across any healthy node."
                ),
                ArchitecturePoint(
                    title = "Data Consistency vs Availability",
                    detail = "Network partitions occur in real hardware. Systems must intentionally choose consistency (CP) or immediate availability (AP)."
                )
            )
        )
    )

    val quizQuestions = mapOf(
        "node-oop" to listOf(
            QuizQuestion(
                id = "q-oop-1",
                lessonId = "node-oop",
                unitId = "unit-1",
                question = "Which keyword is used for inheritance in Java?",
                codeSnippet = CodeSnippet(
                    language = "java",
                    lines = listOf(
                        CodeLine("class Animal {", "normal"),
                        CodeLine("    void eat() {", "normal"),
                        CodeLine("        System.out.println(\"eating...\");", "string"),
                        CodeLine("    }", "normal"),
                        CodeLine("}", "normal"),
                        CodeLine("// What goes here?", "comment"),
                        CodeLine("class Dog ______ Animal {", "blank"),
                        CodeLine("    void bark() {", "normal"),
                        CodeLine("        System.out.println(\"barking...\");", "string"),
                        CodeLine("    }", "normal"),
                        CodeLine("}", "normal")
                    )
                ),
                options = listOf(
                    QuizOption("opt-a", "A", "extends"),
                    QuizOption("opt-b", "B", "implements"),
                    QuizOption("opt-c", "C", "inherit"),
                    QuizOption("opt-d", "D", "super")
                ),
                correctOptionId = "opt-a",
                explanation = "In Java, the 'extends' keyword establishes class inheritance (single inheritance of state and implementation). The 'implements' keyword is strictly reserved for interfaces, while 'super' refers to the parent instance.",
                xpReward = 10
            )
        )
    )

    val initialUserStats = UserStats(
        xp = 120,
        hearts = 5,
        maxHearts = 5,
        streakDays = 4,
        completedNodeIds = listOf("node-fundamentals"),
        activeUnitId = "unit-1",
        accuracyRate = 92,
        questionsAnswered = 12,
        soundEnabled = true
    )

    val badgesData = listOf(
        Badge("badge-1", "First Principles", "Completed your first Systems Architecture theory module.", "school", true, "Today"),
        Badge("badge-2", "Inheritance Master", "Answered OOP inheritance structure challenges without hints.", "code", true, "Today"),
        Badge("badge-3", "Distributed Pioneer", "Achieve 100% accuracy on CAP Theorem & Consensus protocols.", "hub", false),
        Badge("badge-4", "Diamond League", "Rank among top 3 engineers in weekly XP leaderboards.", "military_tech", false)
    )
}

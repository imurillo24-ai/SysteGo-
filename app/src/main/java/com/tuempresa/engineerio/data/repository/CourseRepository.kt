@file:Suppress("unused", "TrailingComma", "ktlint")

package com.tuempresa.engineerio.data.repository

import com.tuempresa.engineerio.domain.model.*

object CourseRepository {

    val unitsData = listOf(
        CourseUnit(
            id = "unit-1",
            unitNumber = 1,
            title = "Sección 1: Arquitectura de Sistemas y POO",
            description = "Domina los fundamentos de POO, clases, herencia, encapsulamiento y abstracción de software.",
            progressPercent = 20,
            totalLessons = 5,
            completedLessons = 1,
            colorScheme = UnitColorScheme(
                primary = "#00F0FF",
                border = "#006970",
                glow = "rgba(0, 240, 255, 0.4)",
            ),
        ),
        CourseUnit(
            id = "unit-2",
            unitNumber = 2,
            title = "Sección 2: Sistemas Distribuidos y Microservicios",
            description = "Consenso distribuido, balanceo de carga y tolerancia a fallos a gran escala.",
            progressPercent = 0,
            totalLessons = 6,
            completedLessons = 0,
            colorScheme = UnitColorScheme(
                primary = "#8B5CF6",
                border = "#4C1D95",
                glow = "rgba(139, 92, 246, 0.4)",
            ),
        ),
        CourseUnit(
            id = "unit-3",
            unitNumber = 3,
            title = "Sección 3: Caché de Baja Latencia y Alta Concurrencia",
            description = "Estrategias de almacenamiento en caché, memoria distribuida y bases de datos NoSQL.",
            progressPercent = 0,
            totalLessons = 5,
            completedLessons = 0,
            colorScheme = UnitColorScheme(
                primary = "#00FF94",
                border = "#00723F",
                glow = "rgba(0, 255, 148, 0.4)",
            ),
        ),
    )

    val lessonNodesData = listOf(
        LessonNode(
            id = "node-fundamentals",
            unitId = "unit-1",
            title = "Fundamentos",
            subtitle = "Introducción a Sistemas Distribuidos y Escalabilidad",
            status = NodeStatus.COMPLETED,
            type = NodeType.THEORY,
            icon = "check_circle",
            xpReward = 15,
            xOffset = -30
        ),
        LessonNode(
            id = "node-oop",
            unitId = "unit-1",
            title = "Principios POO",
            subtitle = "Herencia, Polimorfismo, Encapsulamiento y Contratos",
            status = NodeStatus.CURRENT,
            type = NodeType.QUIZ,
            icon = "school",
            xpReward = 20,
            xOffset = 40
        ),
        LessonNode(
            id = "node-circuits",
            unitId = "unit-1",
            title = "Hardware y Lógica",
            subtitle = "Compuertas Lógicas, Op-Amps y Frecuencia",
            status = NodeStatus.LOCKED,
            type = NodeType.QUIZ,
            icon = "lock",
            xpReward = 20,
            xOffset = -20
        ),
        LessonNode(
            id = "node-scaling",
            unitId = "unit-1",
            title = "Escalabilidad Horizontal",
            subtitle = "Balanceadores de Carga y Teorema CAP",
            status = NodeStatus.LOCKED,
            type = NodeType.QUIZ,
            icon = "lock",
            xpReward = 25,
            xOffset = 30
        ),
        LessonNode(
            id = "node-chest-1",
            unitId = "unit-1",
            title = "Cofre de la Sección 1",
            subtitle = "Reclamar Título de Arquitecto Jr.",
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
            title = "Módulo 1: Fundamentos de Arquitectura",
            moduleType = "MÓDULO TEÓRICO",
            duration = "5 mins",
            progressPercent = 100,
            figureCaption = "Fig. 1.0",
            figureUrl = "",
            summary = "Antes de empezar a programar grandes sistemas, debemos comprender las piezas fundamentales de los sistemas distribuidos. Este módulo abarca conceptos básicos, la transición desde arquitecturas monolíticas y los principios clave del escalamiento.",
            keyConcepts = listOf(
                KeyConcept(
                    id = "scale",
                    name = "Escalabilidad",
                    icon = "bolt",
                    color = "#FF007A",
                    explanation = "Vertical (Scale-up) vs Horizontal (Scale-out). Los sistemas modernos escalan agregando servidores idénticos detrás de balanceadores de carga."
                ),
                KeyConcept(
                    id = "cluster",
                    name = "Nodos y Clústeres",
                    icon = "dns",
                    color = "#00FF94",
                    explanation = "Un clúster consta de nodos independientes cooperando vía RPC. La falla de un único nodo nunca debe detener todo el sistema."
                )
            ),
            architecturePoints = listOf(
                ArchitecturePoint(
                    title = "Punto Único de Falla (SPOF)",
                    detail = "En arquitecturas monolíticas, un choque en un proceso secundario puede congelar toda la app. Los microservicios aíslan el estado."
                ),
                ArchitecturePoint(
                    title = "Capa Sin Estado (Stateless)",
                    detail = "Servidores API sin estado permiten distribuir tráfico entrante de forma transparente entre cualquier nodo disponible."
                ),
                ArchitecturePoint(
                    title = "Consistencia vs Disponibilidad (CAP)",
                    detail = "Ante particiones de red, los sistemas deben elegir explícitamente entre consistencia estricta (CP) o alta disponibilidad (AP)."
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
                question = "1/5: ¿Qué palabra clave se utiliza para aplicar herencia entre clases en Java y lenguajes similares?",
                codeSnippet = CodeSnippet(
                    language = "java",
                    lines = listOf(
                        CodeLine("class Componente { ... }", "normal"),
                        CodeLine("// ¿Cuál palabra clave completa la relación?", "comment"),
                        CodeLine("class Servidor ______ Componente {", "blank"),
                        CodeLine("    void procesar() { }", "normal"),
                        CodeLine("}", "normal")
                    )
                ),
                options = listOf(
                    QuizOption("opt-a", "A", "extends"),
                    QuizOption("opt-b", "B", "implements"),
                    QuizOption("opt-c", "C", "inherits"),
                    QuizOption("opt-d", "D", "super")
                ),
                correctOptionId = "opt-a",
                explanation = "En Java, la palabra clave 'extends' establece herencia de clases. 'implements' se reserva para interfaces.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-oop-2",
                lessonId = "node-oop",
                unitId = "unit-1",
                question = "2/5: ¿Qué principio de la POO oculta el estado interno y expone solo una interfaz pública segura?",
                options = listOf(
                    QuizOption("opt-a", "A", "Polimorfismo"),
                    QuizOption("opt-b", "B", "Encapsulamiento"),
                    QuizOption("opt-c", "C", "Herencia múltiple"),
                    QuizOption("opt-d", "D", "Inyección de dependencias")
                ),
                correctOptionId = "opt-b",
                explanation = "El encapsulamiento protege los atributos internos declarándolos privados y proporcionando métodos de acceso controlados.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-oop-3",
                lessonId = "node-oop",
                unitId = "unit-1",
                question = "3/5: ¿Qué pilar de POO permite que objetos de distintas clases respondan de manera propia al mismo método?",
                options = listOf(
                    QuizOption("opt-a", "A", "Polimorfismo"),
                    QuizOption("opt-b", "B", "Modularidad"),
                    QuizOption("opt-c", "C", "Compilación dinámica"),
                    QuizOption("opt-d", "D", "Consistencia eventual")
                ),
                correctOptionId = "opt-a",
                explanation = "El polimorfismo permite tratar objetos de diferentes subclases mediante su interfaz común, invocando la implementación correspondiente.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-oop-4",
                lessonId = "node-oop",
                unitId = "unit-1",
                question = "4/5: ¿Cuál es la diferencia principal entre una Interfaz y una Clase Abstracta?",
                options = listOf(
                    QuizOption("opt-a", "A", "Las interfaces pueden ser instanciadas con 'new'"),
                    QuizOption("opt-b", "B", "Una clase puede implementar múltiples interfaces pero extender solo una clase"),
                    QuizOption("opt-c", "C", "Las clases abstractas no pueden contener código ejecutable"),
                    QuizOption("opt-d", "D", "Son exactamente lo mismo sin diferencias")
                ),
                correctOptionId = "opt-b",
                explanation = "En Java/Kotlin se soporta la implementación de múltiples interfaces, mientras que la herencia directa de clases es única.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-oop-5",
                lessonId = "node-oop",
                unitId = "unit-1",
                question = "5/5: ¿Qué principio de diseño recomienda 'favorecer la composición sobre la herencia'?",
                options = listOf(
                    QuizOption("opt-a", "A", "Mayor acoplamiento entre clases padre e hijo"),
                    QuizOption("opt-b", "B", "Mayor flexibilidad y menor fragilidad ante cambios en la jerarquía"),
                    QuizOption("opt-c", "C", "Duplicar todo el código en cada componente"),
                    QuizOption("opt-d", "D", "Eliminar el uso de objetos en el programa")
                ),
                correctOptionId = "opt-b",
                explanation = "La composición permite combinar comportamientos en tiempo de ejecución sin quedar atado a rígidas jerarquías de herencia.",
                xpReward = 10
            )
        ),
        "node-circuits" to listOf(
            QuizQuestion(
                id = "q-circ-1",
                lessonId = "node-circuits",
                unitId = "unit-1",
                question = "1/5: ¿Cuál es la función principal de una compuerta lógica NOT en un circuito digital?",
                options = listOf(
                    QuizOption("opt-a", "A", "Invertir el estado del bit (1 pasa a 0, 0 pasa a 1)"),
                    QuizOption("opt-b", "B", "Sumar dos señales binarias"),
                    QuizOption("opt-c", "C", "Multiplicar el voltaje por 2"),
                    QuizOption("opt-d", "D", "Filtrar el ruido de alta frecuencia")
                ),
                correctOptionId = "opt-a",
                explanation = "La compuerta NOT realiza la inversión lógica (negación), transformando un estado ALTO (1) en BAJO (0) y viceversa.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-circ-2",
                lessonId = "node-circuits",
                unitId = "unit-1",
                question = "2/5: ¿Qué componente electrónico almacena energía temporalmente en un campo eléctrico?",
                options = listOf(
                    QuizOption("opt-a", "A", "Resistor"),
                    QuizOption("opt-b", "B", "Capacitor o Condensador"),
                    QuizOption("opt-c", "C", "Diodo LED"),
                    QuizOption("opt-d", "D", "Fusible")
                ),
                correctOptionId = "opt-b",
                explanation = "Los capacitores almacenan carga eléctrica temporalmente, estabilizando el voltaje en las tarjetas de circuito.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-circ-3",
                lessonId = "node-circuits",
                unitId = "unit-1",
                question = "3/5: En compuertas AND, ¿cuándo es la salida un '1' lógico (ALTO)?",
                options = listOf(
                    QuizOption("opt-a", "A", "Solo cuando TODAS sus entradas sean 1"),
                    QuizOption("opt-b", "B", "Cuando al menos una entrada sea 1"),
                    QuizOption("opt-c", "C", "Cuando todas sus entradas sean 0"),
                    QuizOption("opt-d", "D", "Siempre que haya voltaje")
                ),
                correctOptionId = "opt-a",
                explanation = "La compuerta AND requiere que todas las señales de entrada estén activas (1) para producir un resultado activo (1).",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-circ-4",
                lessonId = "node-circuits",
                unitId = "unit-1",
                question = "4/5: ¿Cómo se mide la velocidad de reloj (clock frequency) de un procesador moderno?",
                options = listOf(
                    QuizOption("opt-a", "A", "GigaBytes (GB)"),
                    QuizOption("opt-b", "B", "GigaHertz (GHz)"),
                    QuizOption("opt-c", "C", "Miliamperios (mA)"),
                    QuizOption("opt-d", "D", "Watts (W)")
                ),
                correctOptionId = "opt-b",
                explanation = "Hertz (Hz) o GigaHertz (GHz) representa miles de millones de ciclos de oscilación de reloj por segundo.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-circ-5",
                lessonId = "node-circuits",
                unitId = "unit-1",
                question = "5/5: ¿Qué componente semiconductores permite el paso de corriente eléctrica en una sola dirección?",
                options = listOf(
                    QuizOption("opt-a", "A", "Diodo"),
                    QuizOption("opt-b", "B", "Transformador"),
                    QuizOption("opt-c", "C", "Interruptor térmico"),
                    QuizOption("opt-d", "D", "Potenciómetro")
                ),
                correctOptionId = "opt-a",
                explanation = "El diodo actúa como una válvula de sentido único para la corriente eléctrica.",
                xpReward = 10
            )
        ),
        "node-scaling" to listOf(
            QuizQuestion(
                id = "q-scale-1",
                lessonId = "node-scaling",
                unitId = "unit-1",
                question = "1/5: ¿Qué significa hacer 'Escalamiento Horizontal' (Scale-out)?",
                options = listOf(
                    QuizOption("opt-a", "A", "Comprar una CPU con el doble de núcleos para el mismo servidor"),
                    QuizOption("opt-b", "B", "Añadir más servidores o nodos idénticos para repartir la carga"),
                    QuizOption("opt-c", "C", "Reducir la memoria RAM de los servidores activos"),
                    QuizOption("opt-d", "D", "Reescribir el programa en ensamblador")
                ),
                correctOptionId = "opt-b",
                explanation = "El escalamiento horizontal consiste en aumentar la capacidad agregando nuevos nodos distribuidos.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-scale-2",
                lessonId = "node-scaling",
                unitId = "unit-1",
                question = "2/5: ¿Cuál es la función primordial de un Balanceador de Carga (Load Balancer)?",
                options = listOf(
                    QuizOption("opt-a", "A", "Distribuir las peticiones entrantes entre múltiples servidores backend"),
                    QuizOption("opt-b", "B", "Compilar el código fuente en tiempo real"),
                    QuizOption("opt-c", "C", "Generar backups diarios de la base de datos"),
                    QuizOption("opt-d", "D", "Encriptar el disco duro del cliente")
                ),
                correctOptionId = "opt-a",
                explanation = "El balanceador de carga redirige el tráfico para evitar la saturación de un único servidor.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-scale-3",
                lessonId = "node-scaling",
                unitId = "unit-1",
                question = "3/5: Según el Teorema CAP, ante una Partición de Red (P), ¿qué se debe sacrificar?",
                options = listOf(
                    QuizOption("opt-a", "A", "O Consistencia (C) o Disponibilidad (A)"),
                    QuizOption("opt-b", "B", "La velocidad de la memoria RAM"),
                    QuizOption("opt-c", "C", "El protocolo HTTP/2"),
                    QuizOption("opt-d", "D", "Los certificados de seguridad SSL")
                ),
                correctOptionId = "opt-a",
                explanation = "El Teorema CAP demuestra que durante una falla de red no se puede garantizar simultáneamente consistencia estricta y disponibilidad total.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-scale-4",
                lessonId = "node-scaling",
                unitId = "unit-1",
                question = "4/5: ¿Qué es una estrategia de caché 'Write-Through'?",
                options = listOf(
                    QuizOption("opt-a", "A", "Escribir los datos en la memoria caché y en la base de datos simultáneamente"),
                    QuizOption("opt-b", "B", "Escribir únicamente en disco cuando el servidor se apague"),
                    QuizOption("opt-c", "C", "Ignorar la base de datos por completo"),
                    QuizOption("opt-d", "D", "Borrar la memoria caché cada 5 minutos")
                ),
                correctOptionId = "opt-a",
                explanation = "Write-Through actualiza la memoria caché y la persistencia en base de datos al mismo tiempo para garantizar consistencia.",
                xpReward = 10
            ),
            QuizQuestion(
                id = "q-scale-5",
                lessonId = "node-scaling",
                unitId = "unit-1",
                question = "5/5: ¿Qué ventaja ofrece un servidor Stateless (sin estado) para escalar?",
                options = listOf(
                    QuizOption("opt-a", "A", "Cualquier petición puede ser procesada por cualquier servidor disponible sin perder sesión"),
                    QuizOption("opt-b", "B", "Requiere más memoria RAM para guardar las cookies"),
                    QuizOption("opt-c", "C", "Impide agregar más servidores en el futuro"),
                    QuizOption("opt-d", "D", "Solo funciona con un único usuario a la vez")
                ),
                correctOptionId = "opt-a",
                explanation = "Al no almacenar estado de sesión localmente, cualquier réplica puede atender la petición de cualquier cliente.",
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
        Badge("badge-1", "Primeros Principios", "Completaste tu primer módulo de Arquitectura de Sistemas.", "school", unlocked = true, "Hoy"),
        Badge("badge-2", "Maestro de POO", "Respondiste correctamente el Quiz de 5 preguntas sobre POO.", "code", unlocked = true, "Hoy"),
        Badge("badge-3", "Pionero Distribuido", "100% de precisión en Teorema CAP y Balanceo de Carga.", "hub", unlocked = false),
        Badge("badge-4", "Liga Diamante", "Puesto entre los mejores 3 ingenieros de la semana.", "military_tech", unlocked = false)
    )
}

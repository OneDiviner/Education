package org.example.kotlinBasic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class AppStartDelegate : ReadOnlyProperty<Any?, LocalTime> { // Используем ReadOnly интерфейс

    private val startTime: LocalTime = LocalTime.now()

    init {
        CoroutineScope(Dispatchers.Default).launch { // Выполняем логирование с задержкой 3 секунды в фоновом потоке
            while (isActive) {
                println("[start time: ${startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))}]")
                delay(3000)
            }
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): LocalTime { // Перегрузка getValue является обязательной для ReadOnly интерфейса
        return startTime
    }
}
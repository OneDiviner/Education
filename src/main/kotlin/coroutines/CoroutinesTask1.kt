package org.example.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun <T> Flow<T>.throttleFirst(timeWindowMillis: Long): Flow<T> = flow {

    var lastTime = 0L

    collect { value ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime > timeWindowMillis) {
            lastTime = currentTime
            emit(value)
        }
    }
}

fun <T> Flow<T>.throttleLatest(timeWindowMillis: Long): Flow<T> = channelFlow { // с помощью channelFlow организуем канал между вложенной корутиной job и внешним подписчиком,
                                                                                // который собирает значение отправленное через send()
    val mutex = Mutex()
    var latestValue: T?
    var job: Job? = null

    collect { value ->
        mutex.withLock { // Использую mutex.withLock, чтобы избежать гонки между корутиной, выполняющей collect, и корутиной, выполняющей job
            latestValue = value
            if (job == null) {
                job = launch {
                    delay(timeWindowMillis)
                    val toSend = mutex.withLock {
                        val v = latestValue
                        latestValue = null
                        job = null
                        v
                    }
                    toSend?.let { send(it) }
                }
            }
        }
    }
}
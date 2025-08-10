package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.example.coroutines.throttleFirst
import org.example.coroutines.throttleLatest
import org.example.kotlinBasic.AppStartDelegate
import org.example.kotlinBasic.checkKey
import org.example.kotlinBasic.findInt
import org.example.kotlinBasic.shakerSort

fun main() {

    /*//region KotlinTasks
    // Задание 1
    println("\n--------------------------------------Задание1-----------------------------------------")
    checkKey()
    println("---------------------------------------------------------------------------------------\n")



    // Задание 3
    println("\n--------------------------------------Задание3-----------------------------------------")
    findInt()
    println("---------------------------------------------------------------------------------------\n")



    // Задание 4
    println("\n--------------------------------------Задание4-----------------------------------------")
    val list: List<Int?> = listOf(17, 22, null, 49, 265, null, 107, 28, 99, 1)
    val nullableList: List<Int?> = listOf(null, null, null, null, null)
    println(
        """
            Sort:
            List: ${shakerSort(list)}
            EmptyList: ${shakerSort(emptyList())}
            Nullable list: ${shakerSort(nullableList)}
        """.trimIndent()
    )
    println("---------------------------------------------------------------------------------------\n")



    // Задание 2
    println("\n--------------------------------------Задание2-----------------------------------------")
    val appStart by AppStartDelegate()
    runBlocking { delay(6100) } // Блокируем основной поток, чтобы увидеть выполнение делегата в фоновом потоке
    println("---------------------------------------------------------------------------------------\n")
    //endregion*/
    runBlocking {
        val events = flow {
            repeat(100) {
                emit(it)
                delay(100) // события каждые 200 мс
            }
        }
        println("---- throttleFirst ----")
        events
            .throttleFirst(123)
            .collect { println("first: $it at ${System.currentTimeMillis() % 10000}") }

        println("---- throttleLatest ----")
        events
            .throttleLatest(200)
            .collect { println("latest: $it at ${System.currentTimeMillis() % 10000}") }
    }
}
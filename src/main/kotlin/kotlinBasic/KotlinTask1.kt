package org.example.kotlinBasic

data class Key(
    val field1: Int,
    var field2: String // Проблема может возникнуть из-за использования var
) {
    var field3: String? = null
}

fun checkKey() {

    val key = Key(1, "key")
    //Если мы изменим поле в конструкторе класса, то из-за того, что переопределяется hashCode, поиск будет производиться в другой корзине, а наш объект останется в старой

    val map = HashMap<Key, String>()
    map[key] = "Developer"

    println("До изменения:")
    println("Содержит ключ? ${map.containsKey(key)}") // true
    println("Получить по ключу: ${map[key]}")         // "Developer"

    // Меняем поле, участвующее в hashCode/equals
    key.field2 = "Changed"

    println("\nПосле изменения:")
    println("Содержит ключ? ${map.containsKey(key)}") // false
    println("Получить по ключу: ${map[key]}")         // null

    println("\nСодержимое map:")
    for ((k, v) in map) {
        println("Key = $k, value = $v")
    } // Как можно заметить в map у нас есть содержимое, но из-за того, что мы поменяли поле field2, изменился hashCode и теперь мы не сможем найти наш элемент по измененному ключу

    // Решить это можно используя val или используя .copy():
    val key1 = Key(1, "key")
    val safeKey = key1.copy()
    map[safeKey] = "Developer"

    // Меняем оригинальный key
    key1.field2 = "Changed"

    println("\nПопытка доступа по оригиналу: ${map[key1]}")         // null
    println("Попытка доступа по копии (safeKey): ${map[safeKey]}") // "Developer"
}
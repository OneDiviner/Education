package org.example.kotlinBasic

fun List<Any?>.findFirstInt() : Int? {
    return filterIsInstance<Int>().firstOrNull() // Вариант с "as?": return firstOrNull { it is Int } as? Int
}

fun findInt() {
    val list: List<Any?> = listOf('1', 2, false, 4, "Int", 6f, 7.0, 8L, 9, null)

    println("""
        List: $list
        First Int is ${list.findFirstInt()}
        """.trimIndent()
    )
}
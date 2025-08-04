package org.example.kotlinBasic

fun shakerSort(list: List<Int?>?): List<Int?> {
    if (list == null) return emptyList() // Возвращаем пустой список, если был получен пустой список

    // Отделяем null и non null значения в отдельные списки
    val notNullableList = list.filterNotNull().toMutableList()
    val nullableList = list.filter { it == null }

    var left = 0 // Левая граница
    var right = notNullableList.size - 1 // Правая граница
    var swapped = false // Флаг указывающий на то, что преобразований больше нет

    do {
        swapped = false

        // Проходим по списку слева направо и вытаскиваем самый большой элемент в конец списка
        for (i in left until right) {
            if (notNullableList[i] > notNullableList[i + 1]) {
                notNullableList[i] = notNullableList[i + 1].also { notNullableList[i + 1] = notNullableList[i] } // Меняем элементы местами
                swapped = true
            }
        }
        right-- // Уменьшаем правую границу так как самый тяжелый элемент уже в конце списка

        // Проход по списку справа налево, тащим самый маленький элемент в начало списка
        for (i in right downTo left + 1) {
            if (notNullableList[i - 1] > notNullableList[i]) {
                notNullableList[i - 1] = notNullableList[i].also { notNullableList[i] = notNullableList[i - 1] }
                swapped = true
            }
        }
        left++ // Увеличиваем левую границу

    } while (swapped) // Цикл выполняется пока происходят перестановки

    return notNullableList + nullableList // Возвращаем отсортированный non null список и null список в конце
}
package com.example.randnum

import androidx.lifecycle.ViewModel

// Создаём ViewModel-класс для сохранения данных приложения при повороте устройства
class RandNumViewModel: ViewModel() {

    // Начальное и конечное значения диапазонов по-умолчанию
    val startRangeDefault: String = "-10"
    val endRangeDefault: String = "10"

    // Создаём переменную для хранения списка с началом и концом диапазона
    // range[0] - начало диапазона, range[1] - конец диапазона
    // (Числа хранятся в строковом формате)
    var range: MutableList<String> = mutableListOf(startRangeDefault, endRangeDefault)

    // Создаём переменную для хранения текущего случайного числа
    // (По-умолчанию, текущее случайное число равно null)
    var currentRandNum: Int? = null
}



package com.example.randnum

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

// Класс фрагмента для экрана получения нескольких случайных чисел
class SomeRandNumFragment: Fragment() {

    // Создаём переменную для хранения поля ввода количества требующихся случайных чисел
    private lateinit var quantityEditText: EditText

    // Создаём переменную для хранения поля ввода числа начала диапазона
    private lateinit var startRangeEditText: EditText

    // Создаём переменную для хранения поля ввода числа конца диапазона
    private lateinit var endRangeEditText: EditText

    // Создаём переменную для хранения текстового поля со сгенерированными числами
    private lateinit var someRandNumTextView: TextView

    // Создаём переменную для хранения кнопки генерации числа
    private lateinit var generationButton: Button

    // Создаём экземпляр SomeRandNumViewModel, подключая его к данному фрагменту
    private val someRandNumViewModel: SomeRandNumViewModel by lazy {
        ViewModelProvider(this)[SomeRandNumViewModel::class.java]
    }

    // Переопределяем функцию, вызываемую при заполнении представления макета
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Создаём объект представления фрагмента, используя inflater
        // (Подключаем XML-макет фрагмента и указываем контейнер для него)
        val view = inflater.inflate(R.layout.fragment_some_rand_num, container, false)

        // Инициализируем поле ввода количества требующихся случайных чисел
        quantityEditText = view.findViewById(R.id.quantity)

        // Инициализируем поле ввода числа начала диапазона
        startRangeEditText = view.findViewById(R.id.start_range)

        // Инициализируем поле ввода числа конца диапазона
        endRangeEditText = view.findViewById(R.id.end_range)

        // Инициализируем текстовое поле для хранения сгенерированного числа
        someRandNumTextView = view.findViewById(R.id.some_rand_num)

        // Инициализируем кнопку генерации числа
        generationButton = view.findViewById(R.id.generation_button)

        // Возвращаем созданный объект представления фрагмента
        return view
    }

    // Переопределяем функцию, вызываемую после onCreateView(), когда представление фрагмента уже созданно
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Если текущей строки со случанйми числами ещё не задано, то обновляем её, чтобы задать начальное значение
        // Иначе помещаем в someRandNumTextView.text текущую строку со случаными числами
        if (someRandNumViewModel.currentSomeRandNum == null) {
            updateSomeRandNum()
        } else {
            someRandNumTextView.text = someRandNumViewModel.currentSomeRandNum
        }
    }

    // Переопределяем функцию, вызываемую при запуске фрагмента
    override fun onStart() {
        super.onStart()

        // Создаём слушателя для поля ввода количества требующихся случайных чисел
        val quantityWatcher = object: TextWatcher {

            // Функция, обрабатывающая текст в поле ввода до его изменения
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // Функция, обрабатывающая текст в поле ввода во время его изменения
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // Если введённая строка равна null или не может быть конвертированна в число или меньше единицы
                if (s == null || !isInteger(s.toString()) || s.toString().toInt() < 1) {

                    // Записываем в someRandNumViewModel.quantity значение по-умолчанию
                    someRandNumViewModel.quantity = someRandNumViewModel.qualityDefault
                }

                // Иначе
                else {

                    // Записываем в someRandNumViewModel.quantity введёную строку
                    someRandNumViewModel.quantity = s.toString()
                }
            }

            // Функция, обрабатывающая текст в поле ввода после его изменения
            override fun afterTextChanged(s: Editable?) {}
        }

        // Создаём слушателя для поля ввода числа начала диапазона
        val startRangeWatcher = object: TextWatcher {

            // Функция, обрабатывающая текст в поле ввода до его изменения
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // Функция, обрабатывающая текст в поле ввода во время его изменения
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // Если введённая строка равна null или не может быть конвертированна в число
                if (s == null || !isInteger(s.toString())) {

                    // Записываем в randNumViewModel.range[0] значение по умолчанию
                    someRandNumViewModel.range[0] = someRandNumViewModel.startRangeDefault
                }

                // Иначе
                else {

                    // Записываем в randNumViewModel.range[0] введённую строку
                    someRandNumViewModel.range[0] = s.toString()
                }
            }

            // Функция, обрабатывающая текст в поле ввода после его изменения
            override fun afterTextChanged(s: Editable?) {}
        }

        // Создаём слушателя для поля ввода числа конца диапазона
        val endRangeWatcher = object: TextWatcher {

            // Функция, обрабатывающая текст в поле ввода до его изменения
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // Функция, обрабатывающая текст в поле ввода во время его изменения
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // Если введённая строка равна null или не может быть конвертированна в число,
                // то записываем в randNumViewModel.range[1] значение по умолчанию.
                // Иначе записываем в randNumViewModel.range[1] введённую строку
                if (s == null || !isInteger(s.toString())) {
                    someRandNumViewModel.range[1] = someRandNumViewModel.endRangeDefault
                } else {
                    someRandNumViewModel.range[1] = s.toString()
                }
            }

            // Функция, обрабатывающая текст в поле ввода после его изменения
            override fun afterTextChanged(s: Editable?) {}
        }

        // Добавляем созданного выше слушателя к полю ввода количества требующихся случайных чисел
        quantityEditText.addTextChangedListener(quantityWatcher)

        // Добавляем созданного выше слушателя к полю ввода числа начала диапазона
        startRangeEditText.addTextChangedListener(startRangeWatcher)

        // Добавляем созданного выше слушателя к полю ввода числа конца диапазона
        endRangeEditText.addTextChangedListener(endRangeWatcher)

        // Добавляем слушателя к кнопке генерации случайных чисел
        generationButton.setOnClickListener {

            // Обновляем случайные числа
            updateSomeRandNum()
        }
    }

    // Функция для обновляения текущих случайных чисел новыми случайными значениями из заданного диапазона
    private fun updateSomeRandNum() {

        // Приводим содержание полей EditText к корректному состоянию
        prepareEditText()

        // Переводим количество требующихся случайных чисел в числовой формат
        val quantity = someRandNumViewModel.quantity.toInt()

        // Переводим числа начала и конца диапозона в числовой формат
        var startNum = someRandNumViewModel.range[0].toInt()
        var endNum = someRandNumViewModel.range[1].toInt()

        // Если начальное число заданного диапазона больше конечного числа, то меняем эти числа местами
        if (startNum > endNum) {
            startNum = endNum.also { endNum = startNum }
        }

        // Генерируем новую строку случайных чисел
        val newSomeRandNum = getSomeRandNum(quantity ,startNum, endNum)

        // Обновляем текущую строку случайных чисел в someRandNumViewModel
        someRandNumViewModel.currentSomeRandNum = newSomeRandNum

        // Помещаем сгенерированную строку случайных чисел в соответсвующее текствовое поле
        someRandNumTextView.text = newSomeRandNum
    }

    // Функция для приведения содержания EditText к корректному состоянию
    private fun prepareEditText() {

        // Если в каком-либо поле EditText записан лишь "-", то очищаем данное поле EditText
        if (quantityEditText.text.toString() == "-") quantityEditText.text.clear()
        if (startRangeEditText.text.toString() == "-") startRangeEditText.text.clear()
        if (endRangeEditText.text.toString() == "-") endRangeEditText.text.clear()
    }

    // Функция, определяющая, может ли переданная строка быть конвертированна в число
    private fun isInteger(str: String): Boolean {
        return try {
            str.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    // Функция для генерации строки с требуемым количеством случайных чисел из требуемого диапазона
    private fun getSomeRandNum(quantity: Int, startNum: Int, endNum: Int): String {

        // Создаём переменную для сборки требуемой строки случайных чисел
        var someRandNum = ""

        // Заполняем строку случайными числами, разделяя их запятыми
        for (i in 0 until quantity) {
            someRandNum += (startNum..endNum).random().toString()
            if (i < quantity - 1) someRandNum += ", "
        }

        // Возвращаем заполненную строку
        return someRandNum
    }

    // Блок кода, доступный без создания экземпляра фрагмента
    companion object {

        // Создаём функцию, которая возвращает новый экземпляр фрагмента
        // (Создание подобной функции считается хорошим тоном)
        fun getInstance(): SomeRandNumFragment {
            return SomeRandNumFragment()
        }
    }
}
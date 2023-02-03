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

// Класс фрагмента для экрана получения случайного числа
class RandNumFragment: Fragment() {

    // Создаём переменную для хранения поля ввода числа начала диапазона
    private lateinit var startRangeEditText: EditText

    // Создаём переменную для хранения поля ввода числа конца диапазона
    private lateinit var endRangeEditText: EditText

    // Создаём переменную для хранения текстового поля со сгенерированным числом
    private lateinit var randNumTextView: TextView

    // Создаём переменную для хранения кнопки генерации числа
    private lateinit var generationButton: Button

    // Создаём экземпляр RandNumViewModel, подключая его к данному фрагменту
    private val randNumViewModel: RandNumViewModel by lazy {
        ViewModelProvider(this)[RandNumViewModel::class.java]
    }

    // Переопределяем функцию, вызываемую при заполнении представления макета
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Создаём объект представления фрагмента, используя inflater
        // (Подключаем XML-макет фрагмента и указываем контейнер для него)
        val view = inflater.inflate(R.layout.fragment_rand_num, container, false)

        // Инициализируем поле ввода числа начала диапазона
        startRangeEditText = view.findViewById(R.id.start_range)

        // Инициализируем поле ввода числа конца диапазона
        endRangeEditText = view.findViewById(R.id.end_range)

        // Инициализируем текстовое поле для хранения сгенерированного числа
        randNumTextView = view.findViewById(R.id.rand_num)

        // Инициализируем кнопку генерации числа
        generationButton = view.findViewById(R.id.generation_button)

        // Возвращаем созданный объект представления фрагмента
        return view
    }

    // Переопределяем функцию, вызываемую после onCreateView(), когда представление фрагмента уже созданно
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Если текущего случайного числа ещё не задано, то обновляем его, чтобы задать начальное значение
        // Иначе помещаем в randNumTextView.text текущее случайное число
        if (randNumViewModel.currentRandNum == null) {
            updateRandNum()
        } else {
            randNumTextView.text = randNumViewModel.currentRandNum.toString()
        }
    }

    // Переопределяем функцию, вызываемую при запуске фрагмента
    override fun onStart() {
        super.onStart()

        // Создаём слушателя для поля ввода числа начала диапазона
        val startRangeWatcher = object: TextWatcher {

            // Функция, обрабатывающая текст в поле ввода до его изменения
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // Функция, обрабатывающая текст в поле ввода во время его изменения
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // Если введённая строка равна null или не может быть конвертированна в число
                if (s == null || !isInteger(s.toString())) {

                    // Записываем в randNumViewModel.range[0] значение по умолчанию
                    randNumViewModel.range[0] = randNumViewModel.startRangeDefault
                }

                // Иначе
                else {

                    // Записываем в randNumViewModel.range[0] введённую строку
                    randNumViewModel.range[0] = s.toString()
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
                    randNumViewModel.range[1] = randNumViewModel.endRangeDefault
                } else {
                    randNumViewModel.range[1] = s.toString()
                }
            }

            // Функция, обрабатывающая текст в поле ввода после его изменения
            override fun afterTextChanged(s: Editable?) {}
        }

        // Добавляем созданного выше слушателя к полю ввода числа начала диапазона
        startRangeEditText.addTextChangedListener(startRangeWatcher)

        // Добавляем созданного выше слушателя к полю ввода числа конца диапазона
        endRangeEditText.addTextChangedListener(endRangeWatcher)

        // Добавляем слушателя к кнопке генерации числа
        generationButton.setOnClickListener {

            // Обновляем случайное число
            updateRandNum()
        }
    }

    // Функция для обновляения текущего случайного числа новым случайным числом из заданного диапазона
    private fun updateRandNum() {

        // Приводим содержание полей EditText к корректному состоянию
        prepareEditText()

        // Переводим числа начала и конца диапозона в числовой формат
        var startNum = randNumViewModel.range[0].toInt()
        var endNum = randNumViewModel.range[1].toInt()

        // Если начальное число заданного диапазона больше конечного числа, то меняем эти числа местами
        if (startNum > endNum) {
            startNum = endNum.also { endNum = startNum }
        }

        // Генерируем новое случайное число из заданного диапазона
        val newRandNum = (startNum..endNum).random()

        // Обновляем текущее случайное число в randNumViewModel
        randNumViewModel.currentRandNum = newRandNum

        // Помещаем сгенерированное число в соответствующее текстовое поле
        randNumTextView.text = newRandNum.toString()
    }

    // Функция для приведения содержания EditText к корректному состоянию
    private fun prepareEditText() {

        // Если в каком-либо поле EditText записан лишь "-", то очищаем данное поле EditText
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

    // Блок кода, доступный без создания экземпляра фрагмента
    companion object {

        // Создаём функцию, которая возвращает новый экземпляр фрагмента
        // (Создание подобной функции считается хорошим тоном)
        fun getInstance(): RandNumFragment {
            return RandNumFragment()
        }
    }
}



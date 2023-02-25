package com.example.randnum

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

// Класс для фрагмента экрана меню выбора режима работы
class MenuFragment: Fragment() {

    // Создаём интерфейс для обратного вызова (передачи сообщения в хост-активити)
    interface Callbacks {

        // Функция выбора режима генерации одного случайного числа
        fun generateSingleRandNum()

        // Функция выбора режима генерации нескольких случайных чисел
        fun generateSomeRandNum()
    }

    // Инициализируем переменную, хранящую экземпляр интерфейса Callbacks обратного вызова хост-активити
    private var callbacks: Callbacks ?= null

    // Создаём переменную для хранения кнопки генерации одного случайного числа
    private lateinit var singleGenerateButton: Button

    // Создаём переменную для хранения кнопки генерации нескольких случайных чисел
    private lateinit var someGenerateButton: Button

    // Переопреедляем функцию onAttach(), вызываемую, когда фрагмент прикрепляется к activity
    // В качестве context передаётся экземпляр activity
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Помещаем в callbacks экземпляр activity, к которой был прикреплён фрагмент
        callbacks = context as MenuFragment.Callbacks?
    }

    // Переопределяем функцию, вызываемую при заполнении представления макета
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Создаём объект представления фрагмента, используя inflater
        // (Подключаем XML-макет фрагмента и указываем контейнер для него)
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        // Инициализируем кнопку генерации одного случайного числа
        singleGenerateButton = view.findViewById(R.id.single_generate_button)

        // Инициализируем кнопку генерации нескольких случайных чисел
        someGenerateButton = view.findViewById(R.id.some_generate_button)

        // Возвращаем созданный объект представления фрагмента
        return view
    }

    // Переопределяем функцию, вызываемую при запуске фрагмента
    override fun onStart() {
        super.onStart()

        // Добавляем слушателя к кнопке генерации одного случайного числа
        singleGenerateButton.setOnClickListener {

            // Вызываем функцию выбора режима генерации одного случайного числа
            callbacks?.generateSingleRandNum()
        }

        // Добавляем слушателя к кнопке генерации нескольких случайных чисел
        someGenerateButton.setOnClickListener {

            // Вызываем функцию выбора режима генерации нескольких случайных чисел
            callbacks?.generateSomeRandNum()
        }
    }

    // Блок кода, доступный без создания экземпляра фрагмента
    companion object {

        // Создаём функцию, которая возвращает новый экземпляр фрагмента
        // (Создание подобной функции считается хорошим тоном)
        fun getInstance(): MenuFragment {
            return MenuFragment()
        }
    }
}
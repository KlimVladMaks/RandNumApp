package com.example.randnum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// Главная активити приложения, запускающаяся первой при запуске приложения
class MainActivity : AppCompatActivity(), MenuFragment.Callbacks {

    // Переопределяем функцию, вызываемую при создании активити
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Подключаем соответсвующий XML-макет к активити
        setContentView(R.layout.activity_main)

        // Скрываем верхний заголовок (title bar) приложения
        supportActionBar?.hide()

        // Получаем фрагмент, который в настоящее время заполняет макет активити
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        // Если активити на данный момент не содержит фрагмент
        if (currentFragment == null) {

            // Создаём новый экземпляр фрагмента MenuFragment
            val fragment = MenuFragment.getInstance()

            // Загружаем созданный выше фрагмент во fragment_container данной активити
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    // Переопределяем функцию выбора режима генерации одного случайного числа
    override fun generateSingleRandNum() {

        // Создаём экземпляр фрагмента для генерации одного случайного числа
        val fragment = RandNumFragment.getInstance()

        // Заменяем старый фрагмент на новый фрагмент, но с возможностью вернуться к старому фрагменту
        // при нажатии кнопки "Назад"
        supportFragmentManager
            .beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Переопределяем функцию выбора режима генерации нескольких случайных чисел
    override fun generateSomeRandNum() {

        // Создаём экземпляр фрагмента для генерации нескольких случайных чисел
        val fragment = SomeRandNumFragment.getInstance()

        // Заменяем старый фрагмент на новый фрагмент, но с возможностью вернуться к старому фрагменту
        // при нажатии кнопки "Назад"
        supportFragmentManager
            .beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}



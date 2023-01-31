package com.example.randnum

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// Главная активити приложения, запускающаяся первой при запуске приложения
class MainActivity : AppCompatActivity() {

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

            // Создаём новый экземпляр фрагмента RandNumFragment
            val fragment = RandNumFragment.getInstance()

            // Загружаем созданный выше фрагмент во fragment_container данной активити
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}



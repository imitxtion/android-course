package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаем ConstraintLayout как корневой контейнер
        val constraintLayout = ConstraintLayout(this)

        // Создаем TextView
        val textView = TextView(this)
        textView.text = "Вторая активность"
        textView.textSize = 26f

        // Устанавливаем параметры расположения для TextView
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        textView.layoutParams = layoutParams

        // Добавляем TextView в ConstraintLayout
        constraintLayout.addView(textView)

        // Устанавливаем ConstraintLayout как основной вид
        setContentView(constraintLayout)
    }
}
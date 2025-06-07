package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams


class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val constraintLayout = ConstraintLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            setBackgroundColor(getColor(R.color.custom_background))
        }

        val textView = TextView(this).apply {
            text = getString(R.string.activity_2_title)
            textSize = 26f
            setTextColor(getColor(R.color.custom_text))
            setPadding(16, 16, 16, 16)
        }

        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            leftToLeft = LayoutParams.PARENT_ID
            topToTop = LayoutParams.PARENT_ID
        }
        textView.layoutParams = layoutParams
        constraintLayout.addView(textView)

        setContentView(constraintLayout)
    }
}
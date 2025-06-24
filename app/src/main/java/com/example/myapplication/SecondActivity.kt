package com.example.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class SecondActivity : ComponentActivity() {

    private lateinit var dbHelper: HotelDbHelper
    private lateinit var passportInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var cityInput: EditText
    private lateinit var clientsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = HotelDbHelper(this)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            setPadding(32, 32, 32, 32)
            setBackgroundColor(getColor(R.color.custom_background))
        }

        val title = TextView(this).apply {
            text = getString(R.string.activity_2_title)
            textSize = 24f
            setTextColor(getColor(R.color.custom_text))
            setPadding(0, 0, 0, 48)
        }
        layout.addView(title)

        passportInput = EditText(this).apply {
            hint = getString(R.string.hint_passport)
        }
        layout.addView(passportInput)

        nameInput = EditText(this).apply {
            hint = getString(R.string.hint_name)
        }
        layout.addView(nameInput)

        cityInput = EditText(this).apply {
            hint = getString(R.string.hint_city)
        }
        layout.addView(cityInput)


        val addButton = Button(this).apply {
            text = getString(R.string.add_client_button)
            setOnClickListener {
                addClient()
            }
        }
        layout.addView(addButton)

        val listTitle = TextView(this).apply {
            text = getString(R.string.clients_list_title)
            textSize = 20f
            setTextColor(getColor(R.color.custom_text))
            setPadding(0, 48, 0, 16)
        }
        layout.addView(listTitle)

        clientsTextView = TextView(this).apply {
            textSize = 16f
            setTextColor(getColor(R.color.custom_text))
        }
        layout.addView(clientsTextView)

        setContentView(layout)

        readClients()
    }

    private fun addClient() {
        val passport = passportInput.text.toString().trim()
        val fullName = nameInput.text.toString().trim()
        val city = cityInput.text.toString().trim()

        if (passport.isEmpty() || fullName.isEmpty() || city.isEmpty()) {
            Toast.makeText(this, "Будь ласка, заповніть усі поля", Toast.LENGTH_SHORT).show()
            return
        }

        val parts = fullName.split(" ", limit = 2)
        val lastName = parts.getOrNull(0) ?: ""
        val firstName = parts.getOrNull(1) ?: ""


        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(HotelContract.ClientEntry.COLUMN_PASSPORT_NUMBER, passport)
            put(HotelContract.ClientEntry.COLUMN_LAST_NAME, lastName)
            put(HotelContract.ClientEntry.COLUMN_FIRST_NAME, firstName)
            put(HotelContract.ClientEntry.COLUMN_PATRONYMIC, "") // Поки залишаємо порожнім
            put(HotelContract.ClientEntry.COLUMN_CITY, city)
            put(HotelContract.ClientEntry.COLUMN_CHECK_IN_DATE, System.currentTimeMillis().toString()) // Поточний час
        }

        val newRowId = db.insert(HotelContract.ClientEntry.TABLE_NAME, null, values)

        if (newRowId == -1L) {
            Toast.makeText(this, "Помилка при додаванні клієнта", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Клієнта успішно додано", Toast.LENGTH_SHORT).show()
            passportInput.text.clear()
            nameInput.text.clear()
            cityInput.text.clear()
            readClients()
        }
    }

    private fun readClients() {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            HotelContract.ClientEntry.COLUMN_PASSPORT_NUMBER,
            HotelContract.ClientEntry.COLUMN_LAST_NAME,
            HotelContract.ClientEntry.COLUMN_FIRST_NAME,
            HotelContract.ClientEntry.COLUMN_CITY
        )

        val cursor = db.query(
            HotelContract.ClientEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val stringBuilder = StringBuilder()
        with(cursor) {
            while (moveToNext()) {
                val passport = getString(getColumnIndexOrThrow(HotelContract.ClientEntry.COLUMN_PASSPORT_NUMBER))
                val lastName = getString(getColumnIndexOrThrow(HotelContract.ClientEntry.COLUMN_LAST_NAME))
                val firstName = getString(getColumnIndexOrThrow(HotelContract.ClientEntry.COLUMN_FIRST_NAME))
                val city = getString(getColumnIndexOrThrow(HotelContract.ClientEntry.COLUMN_CITY))
                stringBuilder.append("$lastName $firstName, ($city) - Паспорт: $passport\n\n")
            }
        }
        cursor.close()

        clientsTextView.text = if (stringBuilder.isNotEmpty()) stringBuilder.toString() else "Список клієнтів порожній."
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}

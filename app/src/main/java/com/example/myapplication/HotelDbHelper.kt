package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object HotelContract {
    object RoomEntry : BaseColumns {
        const val TABLE_NAME = "rooms"
        const val COLUMN_TYPE = "type"
        const val COLUMN_COST = "cost"
    }

    object ClientEntry : BaseColumns {
        const val TABLE_NAME = "clients"
        const val COLUMN_PASSPORT_NUMBER = "passport_number"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_PATRONYMIC = "patronymic"
        const val COLUMN_CITY = "city"
        const val COLUMN_CHECK_IN_DATE = "check_in_date"
        const val COLUMN_ROOM_ID = "room_id"
    }

    object EmployeeEntry : BaseColumns {
        const val TABLE_NAME = "employees"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_PATRONYMIC = "patronymic"
        const val COLUMN_FLOOR = "floor"
        const val COLUMN_CLEANING_DAY = "cleaning_day"
    }
}

class HotelDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Hotel.db"

        private const val SQL_CREATE_ROOMS =
            "CREATE TABLE ${HotelContract.RoomEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${HotelContract.RoomEntry.COLUMN_TYPE} TEXT," +
                    "${HotelContract.RoomEntry.COLUMN_COST} REAL)"

        private const val SQL_CREATE_CLIENTS =
            "CREATE TABLE ${HotelContract.ClientEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${HotelContract.ClientEntry.COLUMN_PASSPORT_NUMBER} TEXT UNIQUE," +
                    "${HotelContract.ClientEntry.COLUMN_LAST_NAME} TEXT," +
                    "${HotelContract.ClientEntry.COLUMN_FIRST_NAME} TEXT," +
                    "${HotelContract.ClientEntry.COLUMN_PATRONYMIC} TEXT," +
                    "${HotelContract.ClientEntry.COLUMN_CITY} TEXT," +
                    "${HotelContract.ClientEntry.COLUMN_CHECK_IN_DATE} TEXT," +
                    "${HotelContract.ClientEntry.COLUMN_ROOM_ID} INTEGER," +
                    "FOREIGN KEY(${HotelContract.ClientEntry.COLUMN_ROOM_ID}) REFERENCES ${HotelContract.RoomEntry.TABLE_NAME}(${BaseColumns._ID}))"


        private const val SQL_CREATE_EMPLOYEES =
            "CREATE TABLE ${HotelContract.EmployeeEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${HotelContract.EmployeeEntry.COLUMN_LAST_NAME} TEXT," +
                    "${HotelContract.EmployeeEntry.COLUMN_FIRST_NAME} TEXT," +
                    "${HotelContract.EmployeeEntry.COLUMN_PATRONYMIC} TEXT," +
                    "${HotelContract.EmployeeEntry.COLUMN_FLOOR} INTEGER," +
                    "${HotelContract.EmployeeEntry.COLUMN_CLEANING_DAY} TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${HotelContract.RoomEntry.TABLE_NAME};" +
                "DROP TABLE IF EXISTS ${HotelContract.ClientEntry.TABLE_NAME};" +
                "DROP TABLE IF EXISTS ${HotelContract.EmployeeEntry.TABLE_NAME};"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ROOMS)
        db.execSQL(SQL_CREATE_CLIENTS)
        db.execSQL(SQL_CREATE_EMPLOYEES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
package com.amaurypm.videogames.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Creado por Amaury Perea Matsumura el 21/10/22
 */
open class DbHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "phones.db"
        private const val TABLE_PHONES = "phones"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_PHONES (id INTEGER PRIMARY KEY AUTOINCREMENT, modelo TEXT NOT NULL, marca TEXT NOT NULL, capacidad TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE $TABLE_PHONES")
        onCreate(db)
    }
}
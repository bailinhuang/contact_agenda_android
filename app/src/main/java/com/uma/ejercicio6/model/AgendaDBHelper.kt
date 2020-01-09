package com.uma.ejercicio6.model

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${AgendaContract.Agenda.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${AgendaContract.Agenda.NOMBRE} TEXT," +
            "${AgendaContract.Agenda.TELEFONO} INTEGER)"
private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${AgendaContract.Agenda.TABLE_NAME}"

class AgendaDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Ejercicio6.db"

        private var instance: AgendaDBHelper? = null

        fun getInstance(context: Context): AgendaDBHelper {
            if (instance == null) {
                instance = AgendaDBHelper(context)
            }
            return instance as AgendaDBHelper
        }
    }
}
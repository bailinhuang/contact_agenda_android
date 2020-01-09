package com.uma.ejercicio6.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import kotlin.Array

class Agenda (context: Context) {
    private var db: SQLiteDatabase = AgendaDBHelper.getInstance(context).writableDatabase

    fun insertOrUpdate(name: String, number: Int): Long{
        var row:Long = -1
        var selectionArgs = arrayOf(name)
        val cursor = db.query(
            AgendaContract.Agenda.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            AgendaContract.Agenda.NOMBRE + " = ?",              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null         // The sort order
        )

        val values = ContentValues()
        values.put(AgendaContract.Agenda.NOMBRE, name)
        values.put(AgendaContract.Agenda.TELEFONO, number)
        if (cursor.count == 0) {
            row = db?.insert(AgendaContract.Agenda.TABLE_NAME, null, values)
        } else {
            db?.update(
                AgendaContract.Agenda.TABLE_NAME,
                values,
                AgendaContract.Agenda.NOMBRE + " = ?",
                selectionArgs
            )
        }
        return row
    }


    fun readLike(selectionArgs: Array<String>):ArrayList<ContactModel>{
        var contacts: ArrayList<ContactModel> = ArrayList()
        selectionArgs[0] = selectionArgs[0] + "%"
        val cursor = db.query(
            AgendaContract.Agenda.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            AgendaContract.Agenda.NOMBRE + " LIKE ?",              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null         // The sort order
        )
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(AgendaContract.Agenda.NOMBRE))
                val number = getInt(getColumnIndexOrThrow(AgendaContract.Agenda.TELEFONO))
                contacts.add(ContactModel(name, number))
            }
        }
        return contacts
    }

    fun getAll():ArrayList<ContactModel>{
        var contacts: ArrayList<ContactModel> = ArrayList()
        val cursor = db.query(
            AgendaContract.Agenda.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,          // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null              // The sort order
        )
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(AgendaContract.Agenda.NOMBRE))
                val number = getInt(getColumnIndexOrThrow(AgendaContract.Agenda.TELEFONO))
                contacts.add(ContactModel(name, number))
            }
        }
        return contacts
    }

    companion object {
        private var instance: Agenda? = null

        fun getInstance(context: Context): Agenda {
            if (instance == null) {
                instance = Agenda(context)
            }
            return instance as Agenda
        }
    }
}
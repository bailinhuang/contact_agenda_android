package com.uma.ejercicio6.model

import android.provider.BaseColumns

class AgendaContract {
    object Agenda : BaseColumns {
        const val TABLE_NAME = "Agenda"
        const val NOMBRE = "Nombre"
        const val TELEFONO = "Telefono"
    }
}
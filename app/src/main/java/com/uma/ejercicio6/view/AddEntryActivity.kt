package com.uma.ejercicio6.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.uma.ejercicio6.R
import com.uma.ejercicio6.model.Agenda
import kotlinx.android.synthetic.main.activity_add_entry.*
import java.lang.Exception

class AddEntryActivity : AppCompatActivity() {

    private lateinit var agenda: Agenda
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)
        agenda = Agenda.getInstance(this)

        button_add.setOnClickListener{
            var name = editText_name.text.toString()
            name = name.trimEnd()
            val number = editText_number.text.toString()
            try {
                if (name != "" && number != "") {
                    agenda.insertOrUpdate(name, number.toInt())
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("ADD_SUCCESS", name)
                    startActivity(intent)
                } else {
                    showDialog(this, getString(R.string.error_title), getString(R.string.error_msg))
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    companion object{
        fun showDialog(context: Context, title: String, message: String){
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(context,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(context,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
    }
}

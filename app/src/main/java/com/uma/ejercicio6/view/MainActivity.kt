package com.uma.ejercicio6.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.uma.ejercicio6.R
import com.uma.ejercicio6.model.Agenda
import com.uma.ejercicio6.model.ContactModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.AdapterView.OnItemClickListener
import android.net.Uri
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.widget.*
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_CALL_PHONE = 1

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var listView: ListView
    private lateinit var agenda: Agenda

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        var context = this
        agenda = Agenda.getInstance(this)
        var contacts = agenda.getAll()
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("ADD_SUCCESS")
            Toast.makeText(this, getString(R.string.add_success_msg) + " " + value, Toast.LENGTH_LONG).show()
        }
        fab.setOnClickListener {
            val intent = Intent(context, AddEntryActivity::class.java)
            startActivity(intent)
        }

        editText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var arguments = arrayOf(s.toString().trimEnd())
                contacts = agenda.readLike(arguments)
                if (contacts.size == 0){
                    Toast.makeText(context, getString(R.string.user_not_found_msg), Toast.LENGTH_SHORT).show()
                }
                viewAdapter = AgendaAdapter(contacts.toList())
                listView = findViewById(R.id.contacts)
                listView.adapter = CustomAdapter(context, contacts)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        viewAdapter = AgendaAdapter(contacts.toList())
        listView = findViewById(com.uma.ejercicio6.R.id.contacts)
        listView.adapter = CustomAdapter(this, contacts)
        listView.setOnItemClickListener(
            object : OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = listView.adapter.getItem(position) as ContactModel
                    val intent = Intent(Intent.ACTION_CALL)
                    val number = item.number
                    intent.data = Uri.parse("tel:$number")

                    //You already have permission
                    if (ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {

                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            MY_PERMISSIONS_REQUEST_CALL_PHONE
                        )
                    } else try {
                        startActivity(intent)
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }
                }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private class CustomAdapter(context: Context, items: ArrayList<ContactModel>): BaseAdapter(){

        private val mContext: Context
        private val items = items

        init {
            mContext = context
        }
        override fun getCount(): Int {
            return items.size
        }

        override fun getItem(position: Int): ContactModel {
            return items[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val row:View = layoutInflater.inflate(R.layout.contact_item_agenda, parent, false)
            val name = row.findViewById<TextView>(R.id.name)
            val number = row.findViewById<TextView>(R.id.number)

            var item: ContactModel = items[position]
            name.text = item.name
            number.text = item.number.toString()
            return row
        }
    }


}

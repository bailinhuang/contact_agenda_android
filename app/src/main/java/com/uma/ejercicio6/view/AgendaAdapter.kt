package com.uma.ejercicio6.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uma.ejercicio6.R
import com.uma.ejercicio6.model.ContactModel
import kotlinx.android.synthetic.main.item_agenda.view.*

class AgendaAdapter(private var items: List<ContactModel>) :
    RecyclerView.Adapter<AgendaAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_agenda, parent, false) as TextView

        return ViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, index: Int) = holder.bind(items.get(index))

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(contact: ContactModel) {

            itemView.name.text = contact.name
            itemView.number.text = contact.number.toString()
        }
    }
}

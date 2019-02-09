package com.app.laboratorio3.laboratorio3.models

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.laboratorio3.laboratorio3.R
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class MyAdapter(
    private val myDataset: ArrayList<Contact>,
    private val context: Context,
    private val listener: (Contact) -> Unit) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(contact: Contact, listener: (Contact) -> Unit) = with(itemView){
            itemView.tv_contact_info.text = contact.toString()
            setOnClickListener{listener(contact)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTYpe: Int): MyAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bindItems(myDataset[position], listener)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size


}
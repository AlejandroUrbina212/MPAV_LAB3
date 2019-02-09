package com.app.laboratorio3.laboratorio3

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.app.laboratorio3.laboratorio3.Providers.ContactsProvider
import com.app.laboratorio3.laboratorio3.models.Contact
import com.app.laboratorio3.laboratorio3.models.MyAdapter
import com.app.laboratorio3.laboratorio3.models.myApplicationExtendedClass
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var adapter: MyAdapter
        const val SAVED_CONTACT_ID = "savedContactId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //creates a linear Layout Manager to display the data (Contact type)

        contacts_recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(obtainAllContacts(), this) { contact ->
            run {
                val intent = Intent(this, ViewContactActivity::class.java)
                intent.putExtra(SAVED_CONTACT_ID, contact._id)
                startActivity(intent)
                this.finish()
                startActivity(intent)
            }
        }
        //sets the adapter defined in updateData() to the recycler view
        contacts_recycler_view.adapter = adapter

        btnAddContact.setOnClickListener {
            startActivity(Intent(this, CreateContactActivity::class.java))
            this.finish()
        }

    }

    private fun obtainAllContacts(): ArrayList<Contact> {

        val myContacts = ArrayList<Contact>()
        // Retrieve all the contacts records
        val URL = "content://com.app.laboratorio3.ContactsProvider"
        val contacts = Uri.parse(URL)
        val cursor = contentResolver.query(contacts, null, null, null, "name")

        if (cursor.moveToFirst()) {
            do {
                myContacts.add(
                    Contact(
                        cursor.getInt(cursor.getColumnIndex(ContactsProvider._ID)),
                        cursor.getString(cursor.getColumnIndex(ContactsProvider.NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactsProvider.PHONE)),
                        cursor.getString(cursor.getColumnIndex(ContactsProvider.EMAIL)),
                        cursor.getString(cursor.getColumnIndex(ContactsProvider.IMAGE_PATH))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return myContacts
    }

}

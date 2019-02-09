package com.app.laboratorio3.laboratorio3
/**
 * The LABORATORIO 3 APP, uses a sqlLite database to
 * make CRUD operations over the contacts saved in the user's phone
 * simply displays "Hello World!" to the standard output.
 *
 * @author  Luis Urbina
 * @version 1.0
 * @since   20148-02-09
 */

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.app.laboratorio3.laboratorio3.Providers.ContactsProvider
import com.app.laboratorio3.laboratorio3.models.Contact
import com.app.laboratorio3.laboratorio3.models.MyAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        // Adapter to fill and manage the contacts_recycler_view
        lateinit var adapter: MyAdapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //creates a linear Layout Manager to display the data (Contact type) by invoking the function
        //obtainAllContacts()
        contacts_recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(obtainAllContacts(), this) { contact ->
            run {
                //intent now will contain the selected contact Id.
                val intent = Intent(this, ViewContactActivity::class.java)
                intent.putExtra("savedContactId", contact._id)
                startActivity(intent)
                this.finish()
            }
        }
        //sets the adapter to the recycler view
        contacts_recycler_view.adapter = adapter

        btnAddContact.setOnClickListener {
            startActivity(Intent(this, CreateContactActivity::class.java))
            this.finish()
        }

    }

    /**
     * returns the arrayList of all contacts in the database
     */
    private fun obtainAllContacts(): ArrayList<Contact> {

        val contactsArrayList = ArrayList<Contact>()
        // Retrieve all the contacts records
        val URL = "content://com.app.laboratorio3.ContactsProvider"
        val contacts = Uri.parse(URL)
        val cursor = contentResolver.query(contacts, null, null, null, "name")

        //Instance a Contact with the database data in the ColumnIndex as long as cursor can moveToNext
        if (cursor.moveToFirst()) {
            do {
                contactsArrayList.add(
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
        return contactsArrayList
    }

}

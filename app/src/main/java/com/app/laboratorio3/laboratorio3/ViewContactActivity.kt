package com.app.laboratorio3.laboratorio3

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.app.laboratorio3.laboratorio3.Providers.ContactsProvider
import com.app.laboratorio3.laboratorio3.models.Contact
import kotlinx.android.synthetic.main.activity_view_contact.*

class ViewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_contact)

        val savedContactId = intent.getIntExtra(MainActivity.SAVED_CONTACT_ID, -1)
        if (savedContactId != -1) {
            val actualContact = findContactById(savedContactId)
            detailsContactName.text = actualContact!!.name
            detailsContactPhone.text = actualContact!!.phone
            detailsContactEmail.text = actualContact!!.email
        }

        btnBackToMain.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }

        btnEditContact.setOnClickListener{
            startActivity(Intent(this, EditContactActivity::class.java))
            this.finish()
        }
    }

    private fun findContactById(id: Int): Contact? {
        var actualContact: Contact? = null
        // Get Contact that matches the id
        val URL = "content://com.app.laboratorio3.ContactsProvider/contacts/$id"
        val contact = Uri.parse(URL)
        val cursor = contentResolver.query(contact, null, null, null, "name")

        if (cursor.moveToFirst()) {
            actualContact = Contact(
                cursor.getColumnIndex(ContactsProvider._ID),
                cursor.getString(cursor.getColumnIndex(ContactsProvider.NAME)),
                cursor.getString(cursor.getColumnIndex(ContactsProvider.PHONE)),
                cursor.getString(cursor.getColumnIndex(ContactsProvider.EMAIL)),
                cursor.getString(cursor.getColumnIndex(ContactsProvider.IMAGE_PATH))
            )

        }
        cursor.close()

        return actualContact
    }
}

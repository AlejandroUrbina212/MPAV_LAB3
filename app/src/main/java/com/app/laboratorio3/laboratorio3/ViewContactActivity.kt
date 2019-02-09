package com.app.laboratorio3.laboratorio3

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.app.laboratorio3.laboratorio3.Providers.ContactsProvider
import com.app.laboratorio3.laboratorio3.models.Contact
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_contact.*

class ViewContactActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_contact)
        
        //savedContactId saves the Extra passed by MainActivity
        val savedContactId = intent.getIntExtra("savedContactId", -1)
        //if there's actually a saved contact, then create an actualContact object
        if (savedContactId != -1) {
            //fill the editTexts of the xml file
            val actualContact = findContactById(savedContactId)
            detailsContactName.text = actualContact!!.name
            detailsContactPhone.text = actualContact!!.phone
            detailsContactEmail.text = actualContact!!.email

            //fill the detailsContactImageView if the actual contact has a photoPath (its not empty)
            if (actualContact.photoPath != ""){
                Glide.with(this).load(actualContact.photoPath).into(detailsContactImageView)
            }
        }

        btnBackToMain.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }

        btnEditContact.setOnClickListener{
            //Starts EditContactActivity with the savedContactId as an extra
            val intent = Intent(this, EditContactActivity::class.java)
            intent.putExtra("editableContactId", savedContactId)
            startActivity(intent)
            this.finish()
        }
    }
    /**
     * returns a single contact in the database
     */

    private fun findContactById(id: Int): Contact? {
        var actualContact: Contact? = null
        // Get Contact that matches the id
        val URL = "content://com.app.laboratorio3.ContactsProvider/contacts/$id"
        val contact = Uri.parse(URL)
        val cursor = contentResolver.query(contact, null, null, null, "name")

        //instances a single ContactType Object to work with along all the lifeCycle of the current Activity
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

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()

    }
}

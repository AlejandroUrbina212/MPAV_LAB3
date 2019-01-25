package com.app.laboratorio3.laboratorio3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.app.laboratorio3.laboratorio3.models.Contact
import com.app.laboratorio3.laboratorio3.models.myApplicationExtendedClass
import kotlinx.android.synthetic.main.activity_create_contact.*

class CreateContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)

        val vnameEditText = findViewById<EditText>(R.id.nameEditText)
        val vphoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val vmailEditText = findViewById<EditText>(R.id.mailEditText)

        val backToMainButton = findViewById<Button>(R.id.btnBackToMain)
        backToMainButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }

        val createContactButton = findViewById<Button>(R.id.btnCreateContact)
        createContactButton.setOnClickListener {
            if(!vnameEditText.text.isEmpty() || !vphoneEditText.text.isEmpty() || !vmailEditText.text.isEmpty()){
                val newContact = Contact(vnameEditText.text.toString(), vphoneEditText.text.toString(), vmailEditText.text.toString())
                myApplicationExtendedClass.contacts.add(newContact)
                Toast.makeText(this, "Contacto guardado de forma exitosa", Toast.LENGTH_SHORT).show()
                vnameEditText.setText("")
                vphoneEditText.setText("")
                vmailEditText.setText("")

            } else {
                Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }


}

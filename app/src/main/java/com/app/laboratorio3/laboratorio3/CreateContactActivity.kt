package com.app.laboratorio3.laboratorio3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_create_contact.*

class CreateContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val mailEditText = findViewById<EditText>(R.id.mailEditText)

        val backToMainButton = findViewById<Button>(R.id.btnBackToMain)
        backToMainButton.setOnClickListener {
            this.finish()
        }

        val createContactButton = findViewById<Button>(R.id.btnCreateContact)
        createContactButton.setOnClickListener {
            saveContact()
        }
    }

    private fun saveContact(){
        if (nameEditText.text.toString() != ""){

        }
    }
}

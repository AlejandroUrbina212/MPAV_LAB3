package com.app.laboratorio3.laboratorio3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_contact.*

class CreateContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)

        btnBackToMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }

        btnCreateContact.setOnClickListener {

        }
    }


}

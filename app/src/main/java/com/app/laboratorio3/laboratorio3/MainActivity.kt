package com.app.laboratorio3.laboratorio3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.app.laboratorio3.laboratorio3.models.MyAdapter
import com.app.laboratorio3.laboratorio3.models.myApplicationExtendedClass

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(myApplicationExtendedClass.contacts)

        recyclerView = findViewById<RecyclerView>(R.id.contacts_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        val addContactButton = findViewById<Button>(R.id.btnAddContact)
        addContactButton.setOnClickListener {
            startActivity(Intent(this, CreateContactActivity::class.java))
            this.finish()
        }

    }

}

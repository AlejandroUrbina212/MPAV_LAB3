package com.app.laboratorio3.laboratorio3.models

import android.app.Application

class myApplicationExtendedClass: Application() {

    companion object {
        val contacts = arrayListOf<Contact>(
            Contact("Luis Urbina", "44891647", "luis212urbina@gmail.com"), //adds dummy data to the array
            Contact("Gustavo Méndez", "32349997", "gusmendez99@gmail.com"),
            Contact("Diego Estrada", "41152889", "diegoEstrada@gmail.com")
        )
    }

}
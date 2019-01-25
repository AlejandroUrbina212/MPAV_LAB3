package com.app.laboratorio3.laboratorio3.models

class Contact (val name: String, val number: String, val mail: String){
    override fun toString(): String {
        return "$name, Teléfono: $number "
    }
}
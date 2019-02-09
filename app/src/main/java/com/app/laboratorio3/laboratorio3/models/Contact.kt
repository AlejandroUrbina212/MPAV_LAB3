package com.app.laboratorio3.laboratorio3.models

class Contact (val _id: Int, val name: String, val phone: String, val email: String, val photoPath: String){
    override fun toString(): String {
        return "$name, Tel: $phone "
    }
}
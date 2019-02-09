package com.app.laboratorio3.laboratorio3

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.app.laboratorio3.laboratorio3.Providers.ContactsProvider
import kotlinx.android.synthetic.main.activity_create_contact.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class CreateContactActivity : AppCompatActivity() {
    companion object {
        private const val GALLERY = 1
        private const val IMAGE_DIRECTORY = "/contactsImages"

    }

    private val EXTERNAL_WRITE_CODE = 1
    private var photoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)

        btnBackToMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }

        btnCreateContact.setOnClickListener {
            val contactName = nameEditText.text.toString()
            val contactPhone = phoneEditText.text.toString()
            val contactEmail = emailEditText.text.toString()

            if (contactName.isNotEmpty() && contactPhone.isNotEmpty() && contactEmail.isNotEmpty()) {

                val contentV = ContentValues()
                contentV.put(ContactsProvider.NAME, contactName)
                contentV.put(ContactsProvider.PHONE, contactPhone)
                contentV.put(ContactsProvider.EMAIL, contactEmail)
                contentV.put(ContactsProvider.IMAGE_PATH, photoPath)


                val uri = contentResolver.insert(ContactsProvider.CONTENT_URI, contentV)

                nameEditText.text.clear()
                phoneEditText.text.clear()
                emailEditText.text.clear()
                newContactImageView.setImageResource(R.drawable.ic_account_circle_black_24dp);

                Toast.makeText(this, "Contacto creado exitosamente", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Por favor llene todos los campos.", Snackbar.LENGTH_LONG).show()
            }

        }

        changePhotoButton.setOnClickListener {
            pickPhotoFromDevice()
        }

        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()

        }
    }

    private fun pickPhotoFromDevice() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val contentURI = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                photoPath = constructImagePath(bitmap)
                Toast.makeText(this, "Imagen cargada exitosamente!", Toast.LENGTH_SHORT).show()
                newContactImageView!!.setImageBitmap(bitmap)

            } catch (ioException: IOException) {
                Toast.makeText(this, "Hubo un error al cargar la imagen! error $ioException", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No hay nada seleccionado!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun constructImagePath(imageAsBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        imageAsBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val imageDirectory = File((Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs()
        }
        try {
            val myFile = File(
                imageDirectory, ((Calendar.getInstance().timeInMillis).toString() + ".jpg")
            )
            myFile.createNewFile()
            val fileOutputAsStream = FileOutputStream(myFile)
            fileOutputAsStream.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                this, arrayOf(myFile.path), arrayOf("image/jpeg"), null
            )
            fileOutputAsStream.close()
            return myFile.absolutePath
        } catch (ioException: IOException) {
            Toast.makeText(this, ioException.toString(), Toast.LENGTH_SHORT).show()
        }
        return ""
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == EXTERNAL_WRITE_CODE) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Acceso a escritura denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()

    }
}

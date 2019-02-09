package com.app.laboratorio3.laboratorio3

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.app.laboratorio3.laboratorio3.Providers.ContactsProvider
import com.app.laboratorio3.laboratorio3.models.Contact
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_edit_contact.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class EditContactActivity : AppCompatActivity() {
    companion object {
        private const val GALLERY = 1
        private const val IMAGE_DIRECTORY = "/contactsImages"

    }

    private var photoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        val savedContactId = intent.getIntExtra("editableContactId", -1)

        if (savedContactId != -1) {
            //fill the editTexts with savedContact info
            val actualContact = findContactById(savedContactId)
            editContactName.setText(actualContact!!.name)
            editContactPhone.setText(actualContact!!.phone)
            editContactMail.setText(actualContact.email)
            photoPath = actualContact.photoPath

            if (actualContact.photoPath != "") {
                Glide.with(this).load(actualContact.photoPath).into(editContactImageView)
            }
        }



        btnSaveChanges.setOnClickListener {
            //if none of the editTexts is empty
            if (editContactName.text.toString() != "" && editContactPhone.text.toString() != "" && editContactMail.text.toString() != "") {
                //fill the contentValue with the specified data
                val contentV = ContentValues()
                contentV.put(ContactsProvider.NAME, editContactName.text.toString())
                contentV.put(ContactsProvider.PHONE, editContactPhone.text.toString())
                contentV.put(ContactsProvider.EMAIL, editContactMail.text.toString())

                contentV.put(ContactsProvider.IMAGE_PATH, photoPath)

                //update contentV in the database
                val uri = contentResolver.update(
                    Uri.parse("${ContactsProvider.URL}/$savedContactId"),
                    contentV, null, null
                )

                Toast.makeText(this, "Cambios Guardados", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Por favor llene todos los campos.", Snackbar.LENGTH_LONG).show()
            }

        }
        choosePhotoButton.setOnClickListener {
            pickPhotoFromDevice()
        }
        btnBackToDetails.setOnClickListener {
            val intent = Intent(this, ViewContactActivity::class.java)
            intent.putExtra("savedContactId", savedContactId)
            startActivity(intent)
            this.finish()
        }
    }

    //creates an intent to retrieve external storage data
    private fun pickPhotoFromDevice() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //if something is selected in the gallery picker
        if (data != null) {
            //save data as an Uri
            val contentURI = data.data
            try {
                //pass the contentUri as a bitmap
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                //photopath will take the value of constructImagePath (a String of the photo's path)
                photoPath = constructImagePath(bitmap)
                Toast.makeText(this, "Imagen cargada exitosamente!", Toast.LENGTH_SHORT).show()
                //sets the image in the image view as a bitmap
                editContactImageView!!.setImageBitmap(bitmap)

            } catch (ioException: IOException) {
                Toast.makeText(this, "Hubo un error al cargar la imagen! error $ioException", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No hay nada seleccionado!", Toast.LENGTH_SHORT).show()
        }
    }

    //This does exactly the same as constructImagePath() method defined in CreateContactActivity
    private fun constructImagePath(imageAsBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        //compress the image, to JPEG and to a 90% quality
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

    //obtains a single contact that matches the specified id.
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

    override fun onBackPressed() {
        val intent = Intent(this, ViewContactActivity::class.java)
        intent.putExtra("savedContactId", intent.getIntExtra("editableContactId", -1))
        startActivity(intent)
        this.finish()
    }
}

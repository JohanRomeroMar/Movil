package com.example.juggerco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import java.text.SimpleDateFormat
import java.util.*

class UserData : AppCompatActivity() {
    private  lateinit var userName: TextView
    private  lateinit var userRH: TextView
    private  lateinit var userDocument: TextView
    private  lateinit var userPhone: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        var bundle: Bundle? = intent.extras
        val firstName: String? = bundle?.getString("firstName")
        val secondName: String? = bundle?.getString("secondName")
        val lastName: String? = bundle?.getString("lastName")
        val secondLastName: String? = bundle?.getString("secondLastName")
        val RH: String? = bundle?.getString("RH")
        val bornDate: String? = bundle?.getString("bornDate")
        val documentNumber: String? = bundle?.getString("documentNumber")
        val documentType: String? = bundle?.getString("documentType")
        val phoneNumber: String? = bundle?.getString("phoneNumber")
        val analytics= FirebaseAnalytics.getInstance(this)
        val evento= Bundle()
        evento.putString("message","Viendo datos de usuario")
        analytics.logEvent("Envento",evento)
        setup(firstName ?: "", secondName ?: "", lastName ?: "", secondLastName ?: "", RH ?: "", bornDate ?: "", documentNumber ?: "", documentType ?: "", phoneNumber ?: "" )
    }
    private fun setup(firstName: String, secondName: String, lastName: String, secondLastName: String, RH: String, bornDate: String, documentNumber: String, documentType: String, phoneNumber: String) {
        println(firstName + secondName )

        var firstNameC = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        var secondNameC = secondName.substring(0, 1).toUpperCase() + secondName.substring(1).toLowerCase();
        var lastNameC = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        var secondLastNameC = secondLastName.substring(0, 1).toUpperCase() + secondLastName.substring(1).toLowerCase();

        userName = findViewById(R.id.textViewtName)
        userName.text = "Nombres: $firstNameC $secondNameC $lastNameC $secondLastNameC"

        userRH = findViewById(R.id.textViewRH)
        userRH.text = "RH: $RH"

        userDocument = findViewById(R.id.Document)
        userDocument.text = "Documento: $documentType $documentNumber"

        userPhone = findViewById(R.id.txtPhone)
        userPhone.text = "Numero de celular : $phoneNumber"

        val buttonReturn: Button = findViewById(R.id.btnBack)

        buttonReturn.setOnClickListener {
            val backIntent = Intent(this, Home::class.java).apply {
            }
            startActivity(backIntent)
        }

    }
}
package com.example.juggerco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.analytics.FirebaseAnalytics

class UserData : AppCompatActivity() {
    private  lateinit var user: TextView
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

        user = findViewById(R.id.textView)
        user.text = firstName

    }
}
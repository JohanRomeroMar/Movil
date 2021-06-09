package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class RecoveredPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovered_password)
        val analytics= FirebaseAnalytics.getInstance(this)
        val evento= Bundle()
        evento.putString("message","Recuperando contraseña")
        analytics.logEvent("Envento",evento)
        setup()
    }
    private fun setup() {
        //Obteniendo objetos del activity
        val emailAddress = findViewById<EditText>(R.id.emailEditText);
        val botonRecuperar = findViewById<Button>(R.id.buttonRecovered);
        val volver = findViewById<Button>(R.id.volver);

        var intent = Intent(this, Login::class.java)
        var intent2 = Intent(this, Home::class.java)

        //Volver al activity de iniciar sesion
        volver.setOnClickListener {
            startActivity(intent)
            finish()
        }
        botonRecuperar.setOnClickListener {

            //Logica para recuperar contraseña
            FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Snackbar.make(
                            botonRecuperar, "Se envio un link para recuperar tu contraseña",
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction("Esconder") {
                        }.show()
                    }
                }
        }
    }
}
package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class ChangePassword : AppCompatActivity() {

    private lateinit var newPassword: EditText
    private lateinit var confirmNewpass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        //setup
        var bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val type:String? = bundle?.getString("type")
        val teamUser:String? = bundle?.getString("teamUser")
        val analytics= FirebaseAnalytics.getInstance(this)
        val evento= Bundle()
        evento.putString("message","Cambio de Contraseña")
        analytics.logEvent("Envento",evento)
        setup(email ?: "", type ?: "", teamUser ?: "")
    }

    private fun setup(user: String, type: String, teamUser: String){

        title = "registro"

        val buttonChange: Button = findViewById(R.id.btnChange)

        newPassword = findViewById(R.id.txtNewPass)
        confirmNewpass = findViewById((R.id.txtConfirmPass))

        val buttonReturn: Button = findViewById(R.id.btnBack)


        buttonChange.setOnClickListener {
            if (newPassword.text.isNotEmpty() && confirmNewpass.text.isNotEmpty()) {
                println(FirebaseAuth.getInstance().currentUser?.email)
                if (newPassword.text.toString().equals(confirmNewpass.text.toString()) ) {

                    FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword.text.toString())?.addOnSuccessListener {
                        alertSucces()
                    }?.addOnFailureListener {
                        alertFailure()
                    }

                } else {
                    alertFailure()
                }
            }
        }

        buttonReturn.setOnClickListener {
            val backIntent = Intent(this, Home::class.java).apply {
                putExtra("email", user)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(backIntent)
        }

    }

    private fun alertSucces() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Succesful")
        builder.setMessage("Se ha cambiado la contraseña con exito")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun alertFailure() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Failure")
        builder.setMessage("Se ha producido un error al registrar la nueva contraseña")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}


package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {

    private val  db = FirebaseFirestore.getInstance()

    private lateinit var email:EditText
    private lateinit var password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //setup
        setup()

    }

    private fun setup(){
        title="Login"

        val buttonLog: Button = findViewById(R.id.btnLogin)
        email = findViewById(R.id.txtUser)
        password = findViewById(R.id.txtPassword)
        var userType = ""
        var teamUser = ""

        buttonLog.setOnClickListener {
            if(email.text.isNotEmpty() && password.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString() + "@jugger.co", password.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        db.collection("Users").document(email.text.toString() + "@jugger.co").get()
                            .addOnSuccessListener { document ->
                                if(document.exists()){
                                    userType = document.data?.get("Type").toString()
                                    teamUser = document.data?.get("Team").toString()
                                    showHome(it.result?.user?.email ?: "", userType, teamUser)
                                }else{
                                    showAlert()
                                }
                            }
                    }else{
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, type: String, teamUser: String){
        val homeIntent = Intent(this, Home::class.java).apply {
            putExtra("email", email)
            putExtra("type", type)
            putExtra("teamUser", teamUser)
        }
        startActivity(homeIntent)
    }
}
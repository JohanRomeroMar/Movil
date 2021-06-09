package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private val  db = FirebaseFirestore.getInstance()

    private lateinit var email:EditText
    private lateinit var password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //setup
        val analytics= FirebaseAnalytics.getInstance(this)
        val evento= Bundle()
        evento.putString("message","En el login")
        analytics.logEvent("Envento",evento)
        setup()
    }


    private fun setup(){
        title="Login"
        var recuperarContraseña= findViewById<TextView>(R.id.recuperarPassword)

        recuperarContraseña.setOnClickListener {
            var intentenew= Intent(this,RecoveredPasswordActivity::class.java)
            startActivity(intentenew)
        }
        var user=FirebaseAuth.getInstance().currentUser;
        if(user!==null){
            var home=Intent(this,Home::class.java);
            startActivity(home)
            finish()
        }
        val buttonLog: Button = findViewById(R.id.btnLogin)
        email = findViewById(R.id.emailEditTextSignIn)
        password = findViewById(R.id.passwordEditTextSignIn)
        var userType = ""
        var teamUser = ""

        // Logica de inicio de sesion
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
        finish()
    }
}
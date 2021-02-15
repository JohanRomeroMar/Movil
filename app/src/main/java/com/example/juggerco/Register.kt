package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    private val  db = FirebaseFirestore.getInstance()

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var capitan:RadioButton
    private lateinit var admin: RadioButton
    private lateinit var player: RadioButton
    private lateinit var team: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //setup
        var bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val type:String? = bundle?.getString("type")
        val teamUser:String? = bundle?.getString("teamUser")
        setup(email ?: "", type ?: "", teamUser ?: "")
    }

    private fun setup(user: String, type: String, teamUser: String){

        title="registro"

        val buttonReg:Button = findViewById(R.id.signUpButton)

        email = findViewById(R.id.txtUser)
        password = findViewById(R.id.txtPassword)
        admin = findViewById(R.id.optionAdmin)
        capitan = findViewById(R.id.optionCapitan)
        player = findViewById(R.id.optionPlayer)
        team = findViewById(R.id.txtTeam)

        val buttonReturn: Button = findViewById(R.id.btnBack)
        var userType = ""


        buttonReg.setOnClickListener {
            if(email.text.isNotEmpty() && password.text.isNotEmpty() && team.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString() + "@jugger.co", password.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        if (admin.isChecked){
                            userType = "Admin"
                        }else if (capitan.isChecked){
                            userType = "Capitan"
                        }else if (player.isChecked){
                            userType = "Player"
                        }
                        db.collection("Users").document(email.text.toString() + "@jugger.co")
                            .set(hashMapOf(
                                "Type" to userType,
                                "Team" to team.text.toString().trim().toUpperCase()
                            ))
                            .addOnSuccessListener { alertSucces() }
                            .addOnFailureListener { alertFailure() }
                    }else{
                        alertFailure()
                    }
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

    private fun alertSucces(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Succesful")
        builder.setMessage("Se ha registrado el equipo con exito")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun alertFailure(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Failure")
        builder.setMessage("Se ha producido un error al registrar el usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
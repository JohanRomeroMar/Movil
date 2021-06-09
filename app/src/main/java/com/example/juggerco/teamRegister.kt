package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class teamRegister : AppCompatActivity() {

    private val  db = FirebaseFirestore.getInstance()

    private lateinit var team: EditText
    private lateinit var league: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_register)

        //setup
        var bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val type:String? = bundle?.getString("type")
        val teamUser:String? = bundle?.getString("teamUser")
        val analytics= FirebaseAnalytics.getInstance(this)
        val evento= Bundle()
        evento.putString("message","Registrando equipo")
        analytics.logEvent("Envento",evento)
        setup(email ?: "", type ?: "", teamUser ?: "")
    }

    private fun setup(email: String, type: String, teamUser:String){

        title="teamRegister"

        val buttonReg: Button = findViewById(R.id.signUpButton)
        val buttonReturn: Button = findViewById(R.id.btnBack)

        team = findViewById(R.id.txtTeamr)
        league = findViewById(R.id.txtLeague)


        buttonReturn.setOnClickListener {
            val backIntent = Intent(this, Home::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(backIntent)
        }

        buttonReg.setOnClickListener {

            db.collection("Teams").document(team.text.toString().trim().toUpperCase())
                .set(hashMapOf(
                    "Name" to team.text.toString().trim().toUpperCase()
                ))

            db.collection("Leagues").document(league.text.toString().trim().toUpperCase())
                .collection("Teams").document(team.text.toString().trim().toUpperCase())
                .set(hashMapOf(
                    "Name" to team.text.toString().trim().toUpperCase()
                ))
                .addOnSuccessListener { alertSucces() }
                .addOnFailureListener { alertFailure() }

            league.setText("")
            team.setText("")
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
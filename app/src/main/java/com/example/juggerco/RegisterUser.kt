package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterUser : AppCompatActivity() {

    private val  db = FirebaseFirestore.getInstance()

    private lateinit var txtFName: EditText
    private lateinit var txtSName: EditText
    private lateinit var txtFLName: EditText
    private lateinit var txtSLName: EditText
    private lateinit var txtRH: EditText
    private lateinit var txtBornDate: EditText
    private lateinit var txtDocNum: EditText
    private lateinit var txtDocType: EditText
    private lateinit var txtPhoneNum: EditText
    private lateinit var txtLeague: EditText
    private lateinit var txtTeam: EditText
    private lateinit var txtEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        //setup
        var bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val type:String? = bundle?.getString("type")
        val teamUser:String? = bundle?.getString("teamUser")
        setup(email ?: "", type ?: "", teamUser ?: "")
    }

    private fun setup(user: String, type: String, teamUser: String){

        title="registro"

        val buttonReg: Button = findViewById(R.id.btnRegister)

        txtFName = findViewById(R.id.txtFirstName)
        txtSName = findViewById(R.id.txtSecondName)
        txtFLName = findViewById(R.id.txtFirstLastName)
        txtSLName = findViewById(R.id.txtSecondLastName)
        txtRH = findViewById(R.id.txtRH)
        txtBornDate = findViewById(R.id.txtBornDate)
        txtDocNum = findViewById(R.id.txtDocument)
        txtDocType = findViewById(R.id.txtDocType)
        txtPhoneNum = findViewById(R.id.txtPhone)
        txtLeague = findViewById(R.id.txtLeague)
        txtTeam = findViewById(R.id.txtTeam)
        txtEmail = findViewById(R.id.txtEmail)

        val buttonReturn: Button = findViewById(R.id.btnBack)


        buttonReg.setOnClickListener {
            if(txtFName.text.isNotEmpty() && txtFLName.text.isNotEmpty() && txtSLName.text.isNotEmpty() && txtRH.text.isNotEmpty() && txtBornDate.text.isNotEmpty() && txtDocNum.text.isNotEmpty() && txtDocType.text.isNotEmpty() && txtPhoneNum.text.isNotEmpty() && txtLeague.text.isNotEmpty() && txtTeam.text.isNotEmpty() && txtEmail.text.isNotEmpty()){
                var email = txtEmail.text.toString().trim().toLowerCase() + "@jugger.co"
                db.collection("Leagues").document(txtLeague.text.toString().trim().toUpperCase())
                    .collection("Teams").document(txtTeam.text.toString().trim().toUpperCase())
                    .collection("Players").document(txtDocNum.text.toString().trim().toUpperCase())
                    .set(hashMapOf(
                        "FFName" to txtFName.text.toString().trim().toUpperCase(),
                        "SFName" to txtSName.text.toString().trim().toUpperCase(),
                        "FLName" to txtFLName.text.toString().trim().toUpperCase(),
                        "SLName" to txtSLName.text.toString().trim().toUpperCase(),
                        "RH" to txtRH.text.toString().trim().toUpperCase(),
                        "BornDate" to txtBornDate.text.toString().trim().toUpperCase(),
                        "DocNum" to txtDocNum.text.toString().trim().toUpperCase(),
                        "DocType" to txtDocType.text.toString().trim().toUpperCase(),
                        "PhoneNum" to txtPhoneNum.text.toString().trim().toUpperCase(),
                        "Email" to email
                    ))
                    .addOnSuccessListener { alertSucces() }
                    .addOnFailureListener { alertFailure() }
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
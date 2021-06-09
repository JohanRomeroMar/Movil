package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginBottom
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore

class ConsultUser : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var txtFName: TextView
    private lateinit var txtSName: TextView
    private lateinit var txtFLName: TextView
    private lateinit var txtSLName: TextView
    private lateinit var txtRH: TextView
    private lateinit var txtBornDate: TextView
    private lateinit var txtDocNum: TextView
    private lateinit var txtDocType: TextView
    private lateinit var txtPhoneNum: TextView

    private lateinit var txtDocument: TextView
    private lateinit var txtLeague: EditText
    private lateinit var txtTeam: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consult_user)

        //setup
        var bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val type: String? = bundle?.getString("type")
        val teamUser: String? = bundle?.getString("teamUser")
        val analytics= FirebaseAnalytics.getInstance(this)
        val evento= Bundle()
        evento.putString("message","Consulta de usuario")
        analytics.logEvent("Envento",evento)
        setup(email ?: "", type ?: "", teamUser ?: "")
    }

    private fun setup(user: String, type: String, teamUser: String) {

        title = "registro"

        val buttonCons: Button = findViewById(R.id.btnConsult)

        /* txtFName = findViewById(R.id.txtFFName)
         txtSName = findViewById(R.id.txtSFName)
         txtFLName = findViewById(R.id.txtFLName)
         txtSLName = findViewById(R.id.txtSLName)
         txtRH = findViewById(R.id.txtRH)
         txtBornDate = findViewById(R.id.txtBornDate)
         txtDocNum = findViewById(R.id.txtDocNum)
         txtDocType = findViewById(R.id.txtDocType)
         txtPhoneNum = findViewById(R.id.txtPhone)*/

        txtDocument = findViewById(R.id.txtDocument)
        txtLeague = findViewById(R.id.txtLeague)
        txtTeam = findViewById(R.id.txtTeam)

        val buttonReturn: Button = findViewById(R.id.btnBack)


        buttonCons.setOnClickListener {
            if (txtDocument.text.toString().isNotEmpty() && txtLeague.text.toString()
                    .isNotEmpty() && txtTeam.text.toString().isNotEmpty()
            ) {
                db.collection("Leagues").document(txtLeague.text.toString().trim().toUpperCase())
                    .collection("Teams").document(txtTeam.text.toString().trim().toUpperCase())
                    .collection("Players")
                    .document(txtDocument.text.toString().trim().toUpperCase())
                    .get()
                    .addOnSuccessListener { document ->
                        alertSucces()
                        var txtFName = document.data?.get("FFName").toString()
                        var txtSName = document.data?.get("SFName").toString()
                        var txtFLName = document.data?.get("FLName").toString()
                        var txtSLName = document.data?.get("SLName").toString()
                        var txtRH = document.data?.get("RH").toString()
                        var txtBornDate = document.data?.get("BornDate").toString()
                        var txtDocNum = document.data?.get("DocNum").toString()
                        var txtDocType = document.data?.get("DocType").toString()
                        var txtPhoneNum =document.data?.get("PhoneNum").toString()
                        println(txtFName)
                        /*txtDocument.visibility = View.GONE
                        txtLeague.visibility = View.GONE
                        txtTeam.visibility = View.GONE
                        imgLogo.visibility = View.GONE
                        buttonCons.visibility = View.GONE*/

                        val consulIntent = Intent(this, UserData::class.java).apply {
                            putExtra("firstName", txtFName)
                            putExtra("secondName", txtSName)
                            putExtra("lastName", txtFLName)
                            putExtra("secondLastName", txtSLName)
                            putExtra("RH", txtRH)
                            putExtra("bornDate", txtBornDate)
                            putExtra("documentNumber", txtDocNum)
                            putExtra("documentType", txtDocType)
                            putExtra("phoneNumber", txtPhoneNum)
                        }
                        startActivity(consulIntent)

                    }
                    .addOnFailureListener { alertFailure() }
            } else {
                alertFailure()
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
        builder.setMessage("Se ha registrado el equipo con exito")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun alertFailure() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Failure")
        builder.setMessage("Se ha producido un error al consultar el jugador")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
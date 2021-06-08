package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {

    private lateinit var txtUser: TextView
    private lateinit var txtType: TextView
    private lateinit var txtTeamUser: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //setup
        var bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val type:String? = bundle?.getString("type")
        val teamUser:String? = bundle?.getString("teamUser")
        setup(email ?: "", type ?: "", teamUser ?: "")
    }

    private fun setup(email: String, type: String, teamUser: String){

        val buttonPlayerReg=findViewById<CardView>(R.id.btn_home_registerJ)
        val buttonReg=findViewById<CardView>(R.id.btn_home_crearU)
        val buttonTeamReg=findViewById<CardView>(R.id.btn_home_registrarE)
       // val buttonScores: Button = findViewById(R.id.btnScores) sin uso
        val buttonUserCheck=findViewById<CardView>(R.id.btn_home_consultarJ)
        val buttonChangePassword=findViewById<CardView>(R.id.btn_home_cambiar_contraseña)
        val buttonLogout=findViewById<CardView>(R.id.btn_home_cerrar_sesion)
        val userNameList = email.split("@").map { it.trim() }
        val  userName = userNameList.first()
        txtUser = findViewById(R.id.txtUser)
        txtType = findViewById(R.id.txtType)
        txtTeamUser = findViewById(R.id.txtTeamUser)


        txtUser.text = Editable.Factory.getInstance().newEditable(userName)
        txtType.text = Editable.Factory.getInstance().newEditable(type)
        txtTeamUser.text = Editable.Factory.getInstance().newEditable(teamUser)



        if (type == "Capitan"){
            buttonReg.visibility = View.GONE
            buttonTeamReg.visibility = View.GONE
            buttonUserCheck.visibility = View.GONE
        }

        //Registrar Jugador
        buttonPlayerReg.setOnClickListener {
            val playerRegIntent = Intent(this, RegisterUser::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(playerRegIntent)
        }

        //Crear Usuario
        buttonReg.setOnClickListener {
            val registerIntent = Intent(this, Register::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(registerIntent)
        }

        // Registrar Equipo
        buttonTeamReg.setOnClickListener {
            val registerTeamIntent = Intent(this, teamRegister::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(registerTeamIntent)
        }

        //Cambiar Contraseña
        buttonChangePassword.setOnClickListener {
            val ChangePasswordIntent = Intent(this, ChangePassword::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(ChangePasswordIntent)
        }

        //Consultar jugador
        buttonUserCheck.setOnClickListener {
            val userCheckIntent = Intent(this, ConsultUser::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(userCheckIntent)
        }

        // Cerrar Sesion
        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val logofIntent = Intent(this, Login::class.java)
            startActivity(logofIntent)

        }
    }
}
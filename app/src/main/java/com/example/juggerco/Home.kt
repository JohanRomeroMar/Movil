package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        val buttonLogout: Button = findViewById(R.id.btnLogout)

        val buttonReg: Button = findViewById(R.id.btnRegisterUser)
        val buttonTeamReg: Button = findViewById(R.id.btnCreateTeam)
        val buttonPlayerReg: Button = findViewById(R.id.btnCreatePlayer)
        val buttonCreateTournement: Button = findViewById(R.id.bntCreateTournament)
       // val buttonScores: Button = findViewById(R.id.btnScores) sin uso
        val buttonUserCheck: Button = findViewById(R.id.btnConsultarJugador)
        val buttonChangePassword: Button = findViewById(R.id.btnPasswordChange)

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
            buttonCreateTournement.visibility = View.GONE//aun no
            buttonUserCheck.visibility = View.GONE
        }

        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val logofIntent = Intent(this, Login::class.java)
            startActivity(logofIntent)

        }

        buttonReg.setOnClickListener {
            val registerIntent = Intent(this, Register::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(registerIntent)
        }

        buttonTeamReg.setOnClickListener {
            val registerTeamIntent = Intent(this, teamRegister::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(registerTeamIntent)
        }

        buttonChangePassword.setOnClickListener {
            val ChangePasswordIntent = Intent(this, ChangePassword::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(ChangePasswordIntent)
        }

        buttonPlayerReg.setOnClickListener {
            val playerRegIntent = Intent(this, RegisterUser::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(playerRegIntent)
        }

        buttonUserCheck.setOnClickListener {
            val userCheckIntent = Intent(this, ConsultUser::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(userCheckIntent)
        }

        buttonCreateTournement.setOnClickListener {
            val createTournamentIntent = Intent(this, CreateTournament::class.java).apply {
                putExtra("email", email)
                putExtra("type", type)
                putExtra("teamUser", teamUser)
            }
            startActivity(createTournamentIntent)
        }

    }
}
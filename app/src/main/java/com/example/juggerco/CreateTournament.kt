package com.example.juggerco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class CreateTournament : AppCompatActivity() {

    private val  db = FirebaseFirestore.getInstance()

    private lateinit var txtTournamentName: EditText
    private lateinit var txtTournamentDate: EditText
    private lateinit var txtTournamentType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament)

        //setup
        var bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val type:String? = bundle?.getString("type")
        val teamUser:String? = bundle?.getString("teamUser")
        setup(email ?: "", type ?: "", teamUser ?: "")
    }
    private fun setup(user: String, type: String, teamUser: String) {

        //define values of a GUI
        val buttonReturn: Button = findViewById(R.id.btnBack)
        val buttonCreate: Button = findViewById(R.id.btnCreate)

        val list: MutableList<String> = ArrayList()
        list.add("")
        list.add("Keys")
        list.add("Groups")
        list.add("Mixt")

        val  adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)

        txtTournamentType = findViewById(R.id.spinner)

        var txtType = ""

        txtTournamentName = findViewById(R.id.txtTournamentName)
        txtTournamentDate = findViewById(R.id.txtDate)

        txtTournamentType.adapter = adapter

        txtTournamentType.onItemSelectedListener = object :

        //get value from list
        AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                txtType = list[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                txtType = ""
            }

        }

        var txtTournamentNameDate = ""

        println(txtTournamentNameDate)

        //get teams from db
        var teams = mutableListOf<String>()

        db.collection("Teams").get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    teams.add(document.id)
                }
            }
            .addOnFailureListener{ alertFailure() }



        buttonCreate.setOnClickListener {
            if (txtType.equals("Keys")){
                var name = ""
                var size = 0
                var count = 1
                var listVs = mutableListOf<String>()
                var temp = ""

                for (element in teams){
                    size += 1
                }

                //Matchmaking
                for (x in 0 until  size){
                    name = teams.random()
                    teams.remove(name)

                    if (count == 1){
                        temp = name
                        count += 1
                    }else if (count == 2){
                        temp = temp + " vs " + name
                        listVs.add(temp)
                        count = 1
                        temp = ""
                    }
                }

                if (count == 2 && temp.isNotEmpty()){
                    temp = temp + " vs "
                    listVs.add(temp)
                }

                txtTournamentNameDate = txtTournamentName.text.toString().trim().toUpperCase() + "-" + txtTournamentDate.text.toString().trim().toUpperCase()

                //add tournament info to db
                db.collection("Tournaments").document(txtTournamentNameDate)
                    .set(hashMapOf(
                        "Name" to txtTournamentName.text.toString().trim().toUpperCase(),
                        "Date" to txtTournamentDate.text.toString().trim().toUpperCase()
                    ))

                db.collection("Tournaments").document(txtTournamentNameDate)
                    .collection("rounds").document("round1")
                    .set(hashMapOf(
                        "Round" to "round1"
                    ))
                    .addOnSuccessListener { alertSucces() }
                    .addOnFailureListener { alertFailure() }

                //adding matches to db
                for (element in listVs){
                    var match = element.split(" vs ")
                    db.collection("Tournaments").document(txtTournamentNameDate)
                        .collection("rounds").document("round1")
                        .collection("Matches").document(element)
                        .set(hashMapOf(
                            "MatchName" to element.trim().toUpperCase(),
                            "Team1" to match.first().trim(),
                            "Team2" to match.last().trim()
                        ))
                }
            }else if (txtType.equals("Groups")){
                println("Grupos aun no implementado")
            }else if (txtType.equals("Mixt")){
                println("Mixto aun no implementado")
            }else {
                println("Typo de torneo invalido")
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
        builder.setMessage("Se ha producido un error al registrar la nueva contraseña")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
package com.example.juggerco

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val analytics= FirebaseAnalytics.getInstance(this)
        val evento= Bundle()
        evento.putString("message","Abrio la aplicacion")
        analytics.logEvent("Envento",evento)
        setContentView(R.layout.activity_splash)
        var intent=Intent(this,Login::class.java)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(intent)
                finish()
            },
            3000
        )
    }
}
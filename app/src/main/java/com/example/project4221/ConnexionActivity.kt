package com.example.project4221

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ConnexionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        val btn_cre_account = findViewById<Button>(R.id.button_create_account)
        val tv_login_link = findViewById<TextView>(R.id.text_login_link)

        btn_cre_account.setOnClickListener{
            val intent = Intent(this,CreateAccountActivity::class.java)
            startActivity(intent)
        }
        tv_login_link.setOnClickListener {
            val intent = Intent(this, LoginActivity:: class.java)
            startActivity(intent)
        }
    }
}
package com.androidjson.sqlite_signinpage_ws_10

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class WelcomeActivity : AppCompatActivity() {
    var EmailHolder: String? = null
    var PasswordHolder: String? = null
    var Email: TextView? = null
    var Password: TextView? = null
    var LogOUT: Button? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Email = findViewById<View>(R.id.textView1) as TextView
        Password = findViewById<View>(R.id.textView2) as TextView
        LogOUT = findViewById<View>(R.id.button1) as Button

        val intent1 = intent
//        val intent2 = intent

//        Receiving User Email Send By MainActivity.
        EmailHolder = intent1.getStringExtra(MainActivity.UserEmail)

//        PasswordHolder = intent2.getStringExtra(MainActivity.UserPassword)


//        Setting up received email to TextView.
        Email!!.text = Email!!.text.toString() + EmailHolder

//        Password!!.text = Password!!.text.toString() + PasswordHolder

//        Adding click listener to Sign Out button.
        LogOUT!!.setOnClickListener {
            finish()
            Toast.makeText(this@WelcomeActivity, "Signed Out Successfully !!", Toast.LENGTH_LONG).show()
        }
    }
}

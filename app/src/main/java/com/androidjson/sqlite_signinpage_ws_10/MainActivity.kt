package com.androidjson.sqlite_signinpage_ws_10

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var SignInBtn: Button? = null
    var SignUpBtn: Button? = null
    var Name: EditText? = null
    var NameHolder: String? = null

    var Email: EditText? = null
    var Password: EditText? = null
    var EmailHolder: String? = null
    var PasswordHolder: String? = null
    var EditTextEmptyHolder: Boolean? = null
    var sqLiteDatabaseObj: SQLiteDatabase? = null
    var sqLiteHelper: SQLiteDB? = null
    var cursor: Cursor? = null
    var TempPassword = "NOT_FOUND"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SignInBtn = findViewById<View>(R.id.btnsignin1) as Button
        SignUpBtn = findViewById<View>(R.id.btnsignup1) as Button
        Email = findViewById<View>(R.id.email1) as EditText
        Password = findViewById<View>(R.id.password1) as EditText
        sqLiteHelper = SQLiteDB(this)

        //Adding click listener to log in button.
        SignInBtn!!.setOnClickListener { // Calling EditText is empty or no method.
            CheckEditTextStatus()

            // Calling login method.
            LoginFunction()
        }

        // Adding click listener to register button.
        SignUpBtn!!.setOnClickListener { // Opening new user registration activity using intent on button click.
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // Login function starts from here.
    @SuppressLint("Range")
    fun LoginFunction() {
        if (EditTextEmptyHolder!!) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper?.writableDatabase

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj!!.query(
                SQLiteDB.TABLE_NAME,
                null,
                " " + SQLiteDB.Table_Column_2_Email.toString() + "=?",
                arrayOf(EmailHolder),
                null,
                null,
                null
            )
            if (cursor != null && cursor!!.moveToFirst() ){

                // Storing Password associated with entered email.
                TempPassword =
                    cursor!!.getString(cursor!!.getColumnIndex(SQLiteDB.Table_Column_3_Password))

                // Closing cursor.
                cursor!!.close()
            }

            // Calling method to check final result ..
            CheckFinalResult()
        } else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(
                this@MainActivity,
                "Please Enter UserName or Password.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Checking EditText is empty or not.
    fun CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email!!.text.toString()
        PasswordHolder = Password!!.text.toString()



        // Checking EditText is empty or no using TextUtils.
        EditTextEmptyHolder =
            !(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
    }
//    Checking entered password from SQLite database email associated password.
    fun CheckFinalResult() {
        if (TempPassword.equals(PasswordHolder, ignoreCase = true)) {
            Toast.makeText(this@MainActivity, "Signed In Successfully !!", Toast.LENGTH_LONG).show()
            // Going to Dashboard activity after login success message.
            val intent1 = Intent(this@MainActivity, WelcomeActivity::class.java)
//            val intent2 = Intent(this@MainActivity, WelcomeActivity::class.java)

//            Sending Email to Dashboard Activity using intent.
            intent1.putExtra(
                UserEmail,
                EmailHolder,
            )
            startActivity(intent1)
//            intent2.putExtra(
//                UserPassword,
//                PasswordHolder
//            )
//            startActivity(intent2)
        } else {
            Toast.makeText(
                this@MainActivity,
                "SignUp or Enter Correct User Email and Password !!",
                Toast.LENGTH_LONG
            ).show()
        }
        TempPassword = "NOT_FOUND"
    }
    companion object {
        const val UserEmail = ""
        const val UserPassword = ""
    }
}

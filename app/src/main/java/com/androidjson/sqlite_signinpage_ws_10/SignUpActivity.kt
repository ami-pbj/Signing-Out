package com.androidjson.sqlite_signinpage_ws_10

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

class SignUpActivity : AppCompatActivity() {
    var Email: EditText? = null
    var Password: EditText? = null
    var Name: EditText? = null
    var SignUp: Button? = null
    var SignIn: Button? = null

    var NameHolder: String? = null
    var EmailHolder: String? = null
    var PasswordHolder: String? = null
    var EditTextEmptyHolder: Boolean? = null
    var sqLiteDatabaseObj: SQLiteDatabase? = null
    var SQLiteDataBaseQueryHolder: String? = null
    var sqLiteHelper: SQLiteDB? = null
    var cursor: Cursor? = null
    var F_Result = "Not_Found"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        SignUp = findViewById<View>(R.id.btnsignup) as Button
        SignIn = findViewById<View>(R.id.btnsignin) as Button
        Email = findViewById<View>(R.id.email) as EditText
        Password = findViewById<View>(R.id.password) as EditText
        Name = findViewById<View>(R.id.name) as EditText

        sqLiteHelper = SQLiteDB(this)

        // Adding click listener to register button.
        SignUp!!.setOnClickListener { // Creating SQLite database if dose n't exists
            SQLiteDataBaseBuild()

            // Creating SQLite table if dose n't exists.
            SQLiteTableBuild()

            // Checking EditText is empty or Not.
            CheckEditTextStatus()

            // Method to check Email is already exists or not.
            CheckingEmailAlreadyExistsOrNot()

            // Empty EditText After done inserting process.
            EmptyEditTextAfterDataInsert()
        }

        // Move to signin page == MainActivity
        SignIn!!.setOnClickListener {
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // SQLite database build method.
    fun SQLiteDataBaseBuild() {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteDB.DATABASE_NAME, MODE_PRIVATE, null)
    }

    // SQLite table build method.
    fun SQLiteTableBuild() {
        sqLiteDatabaseObj!!.execSQL("CREATE TABLE IF NOT EXISTS "
                + SQLiteDB.TABLE_NAME.toString() + "(" + SQLiteDB.Table_Column_ID.toString() + " PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + SQLiteDB.Table_Column_1_Name.toString() + " VARCHAR, " + SQLiteDB.Table_Column_2_Email.toString() + " VARCHAR, "
                + SQLiteDB.Table_Column_3_Password.toString() + " VARCHAR);")
    }

    // Insert data into SQLite database method.
    fun InsertDataIntoSQLiteDatabase() {

        // If editText is not empty then this block will executed.
        if (EditTextEmptyHolder == true) {

            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder =
                "INSERT INTO " + SQLiteDB.TABLE_NAME.toString() + " (name,email,password) VALUES('" + NameHolder.toString() + "', '" + EmailHolder.toString() + "', '" + PasswordHolder.toString() + "');"

            // Executing query.
            sqLiteDatabaseObj!!.execSQL(SQLiteDataBaseQueryHolder)

            // Closing SQLite database object.
            sqLiteDatabaseObj!!.close()

            // Printing toast message after done inserting.
            Toast.makeText(this@SignUpActivity, "User Registered Successfully", Toast.LENGTH_LONG)
                .show()
        } else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(
                this@SignUpActivity,
                "Please Fill All The Required Fields.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Empty edittext after done inserting process method.
    fun EmptyEditTextAfterDataInsert() {
        Name!!.text.clear()
        Email!!.text.clear()
        Password!!.text.clear()
    }

    fun CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        NameHolder = Name!!.text.toString()
        EmailHolder = Email!!.text.toString()
        PasswordHolder = Password!!.text.toString()

        EditTextEmptyHolder =
            !(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(
                PasswordHolder
            ))
    }

    // Checking Email is already exists or not.
    fun CheckingEmailAlreadyExistsOrNot() {

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
            cursor!!.moveToFirst()

            // If Email is already exists then Result variable value set as Email Found.
            F_Result = "Email Found"

            // Closing cursor.
            cursor!!.close()
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult()
    }

    // Checking result
    fun CheckFinalResult() {

        // Checking whether email is already exists or not.
        if (F_Result.equals("Email Found", ignoreCase = true)) {

            // If email is exists then toast msg will display.
            Toast.makeText(this@SignUpActivity, "Email Already Exists", Toast.LENGTH_LONG).show()
        } else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase()
        }
        F_Result = "Not_Found"
    }
}

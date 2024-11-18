package hr.tvz.android.projectfranprizmic.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import hr.tvz.android.projectfranprizmic.Activity.Controller.LoginController
import hr.tvz.android.projectfranprizmic.Activity.Controller.RegisterController
import hr.tvz.android.projectfranprizmic.Activity.Model.User
import hr.tvz.android.projectfranprizmic.Activity.View.IRegisterView
import hr.tvz.android.projectfranprizmic.R

class RegisterActivity : ComponentActivity(), IRegisterView{

    lateinit var loginButton: Button
    lateinit var registerButton: Button

    lateinit var usernameField: TextView
    lateinit var passwordField1: TextView
    lateinit var passwordField2: TextView

    fun clearField(){
        usernameField.text = ""
        passwordField1.text = ""
        passwordField2.text = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        loginButton = findViewById(R.id.returnButton)
        registerButton = findViewById(R.id.registerButton)

        usernameField = findViewById(R.id.usernameField)
        passwordField1 = findViewById(R.id.password1Field)
        passwordField2 = findViewById(R.id.password2Field)

        val registerController = RegisterController(this)

        loginButton.setOnClickListener {
            this.finish()
        }
        registerButton.setOnClickListener {
            val username = usernameField.text.toString()
            val pass1 = passwordField1.text.toString()
            val pass2 = passwordField2.text.toString()

            registerController.onRegister(username, pass1, pass2)
        }
    }

    override fun onRegisterSuccess(user: User) {
        clearField()
        Log.e("Register", "Register Success")
        this.finish()
    }

    override fun onRegisterFail() {
        clearField()
        Log.e("Register", "Register Failed")
    }
}
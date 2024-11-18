package hr.tvz.android.projectfranprizmic.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import hr.tvz.android.projectfranprizmic.Activity.Controller.LoginController
import hr.tvz.android.projectfranprizmic.Activity.Controller.RegisterController
import hr.tvz.android.projectfranprizmic.Activity.Model.User
import hr.tvz.android.projectfranprizmic.Activity.View.ILoginView
import hr.tvz.android.projectfranprizmic.R

class LoginActivity : ComponentActivity(), ILoginView {
    lateinit var loginButton: Button
    lateinit var registerButton: Button

    lateinit var usernameField: TextView
    lateinit var passwordField: TextView

    fun clearField(){
        usernameField.text = ""
        passwordField.text = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        usernameField = findViewById(R.id.usernameField)
        passwordField = findViewById(R.id.passwordField)

        val loginController = LoginController(this)

        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val pass = passwordField.text.toString()

            loginController.onLogin(username, pass)
        }
        registerButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    override fun onLoginSuccess(user: User) {
        clearField()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        Log.e("Login", "Login Success")
    }

    override fun onLoginFail() {
        clearField()
        Log.e("Login", "Login Failed")
    }
}
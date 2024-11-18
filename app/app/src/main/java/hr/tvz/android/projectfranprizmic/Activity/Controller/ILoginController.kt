package hr.tvz.android.projectfranprizmic.Activity.Controller

interface ILoginController {
    //fun hashPassword(password: String): String
    fun onLogin(username: String, password: String)
    fun onLogout()
    //fun onRegister(username: String, password1: String, password2: String)
}
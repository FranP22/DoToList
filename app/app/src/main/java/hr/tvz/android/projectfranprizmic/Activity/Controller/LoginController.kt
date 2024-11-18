package hr.tvz.android.projectfranprizmic.Activity.Controller

import android.util.Log
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IValidCallBack
import hr.tvz.android.projectfranprizmic.Activity.Model.User
import hr.tvz.android.projectfranprizmic.Activity.Model.UserValid
import hr.tvz.android.projectfranprizmic.Activity.View.ILoginView
import hr.tvz.android.projectfranprizmic.PasswordHash

class LoginController(
    private val loginView: ILoginView
) : ILoginController{
    companion object {
        private var user: User = User(-1, "", "")

        fun getUser() : User {
            return user
        }
        fun setUser(newuser: User){
            user = newuser
        }
    }

    override fun onLogin(username: String, password: String) {
        val hashedpassword = PasswordHash.hashPassword(password)

        val newuser = User(-1, username, hashedpassword)
        var isValid: UserValid = UserValid.ERROR
        newuser.isValid(object : IValidCallBack {
            override fun onResult(valid: UserValid, user: User) {
                isValid = valid

                if(isValid.code == 0){
                    setUser(user)
                    loginView.onLoginSuccess(user)
                }else{
                    Log.e("Login", isValid.toString())
                    loginView.onLoginFail()
                }
            }
        })
    }

    override fun onLogout() {
        user = User(-1, "", "")
    }
}
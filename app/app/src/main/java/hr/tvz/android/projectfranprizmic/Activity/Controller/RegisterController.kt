package hr.tvz.android.projectfranprizmic.Activity.Controller

import android.util.Log
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IValidCallBack
import hr.tvz.android.projectfranprizmic.Activity.Model.User
import hr.tvz.android.projectfranprizmic.Activity.Model.UserValid
import hr.tvz.android.projectfranprizmic.Activity.View.IRegisterView
import hr.tvz.android.projectfranprizmic.PasswordHash

class RegisterController(
    private val registerView: IRegisterView
): IRegisterController {
    override fun onRegister(username: String, password1: String, password2: String) {
        val hashedpassword1 = PasswordHash.hashPassword(password1)
        val hashedpassword2 = PasswordHash.hashPassword(password2)

        val newuser = User(-1, username, hashedpassword1)
        newuser.startRegister(hashedpassword2)
        var isValid: UserValid = UserValid.ERROR
        newuser.isValid(object : IValidCallBack {
            override fun onResult(valid: UserValid, user: User) {
                isValid = valid

                if(isValid.code == 1){
                    LoginController.setUser(newuser)
                    registerView.onRegisterSuccess(newuser)
                }else{
                    Log.e("Register", isValid.toString())
                    registerView.onRegisterFail()
                }
            }
        })
    }
}
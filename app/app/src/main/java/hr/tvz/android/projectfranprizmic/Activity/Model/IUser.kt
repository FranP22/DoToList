package hr.tvz.android.projectfranprizmic.Activity.Model

import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IValidCallBack

interface IUser {
    fun getId(): Int
    fun getUsername(): String?
    fun getPassword(): String?
    fun getRegisterPassword(): String?
    fun isValid(callback: IValidCallBack)
    fun startRegister(secondpassword: String)
}
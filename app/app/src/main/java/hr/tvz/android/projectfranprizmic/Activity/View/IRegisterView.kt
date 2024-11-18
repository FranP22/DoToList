package hr.tvz.android.projectfranprizmic.Activity.View

import hr.tvz.android.projectfranprizmic.Activity.Model.User

interface IRegisterView {
    fun onRegisterSuccess(user: User)
    fun onRegisterFail()
}
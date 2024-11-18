package hr.tvz.android.projectfranprizmic.Activity.View

import hr.tvz.android.projectfranprizmic.Activity.Model.User

interface ILoginView {
    fun onLoginSuccess(user: User)
    fun onLoginFail()
}
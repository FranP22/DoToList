package hr.tvz.android.projectfranprizmic.Activity.Model.Callback

import hr.tvz.android.projectfranprizmic.Activity.Model.User
import hr.tvz.android.projectfranprizmic.Activity.Model.UserValid

interface IValidCallBack {
    fun onResult(valid: UserValid, user: User)
}
package hr.tvz.android.projectfranprizmic.Activity.Model.Callback

import hr.tvz.android.projectfranprizmic.Activity.Model.ToDo

interface IToDoListCallBack {
    fun onResult(list: ArrayList<ToDo>)
}
package hr.tvz.android.projectfranprizmic.Activity.View

import hr.tvz.android.projectfranprizmic.Activity.Model.ToDo

interface IMainView {
    fun onLoadListSuccess(todos: ArrayList<ToDo>)
    fun onLoadListFail()
    fun onDataRefresh()
}
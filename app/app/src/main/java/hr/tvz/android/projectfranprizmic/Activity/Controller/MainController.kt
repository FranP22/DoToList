package hr.tvz.android.projectfranprizmic.Activity.Controller

import android.util.Log
import android.widget.Button
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IToDoChangeCallBack
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IToDoListCallBack
import hr.tvz.android.projectfranprizmic.Activity.Model.ToDo
import hr.tvz.android.projectfranprizmic.Activity.View.IMainView

class MainController(
    private val mainView: IMainView
): IMainController {
    override fun onLoadList() {
        ToDo.getToDo(object : IToDoListCallBack {
            override fun onResult(list: ArrayList<ToDo>) {
                if(list.isEmpty()){
                    mainView.onLoadListFail()
                }else{
                    mainView.onLoadListSuccess(list)
                }
            }
        })
    }

    override fun onClearCompleted() {
        ToDo.clearToDo(object : IToDoChangeCallBack {
            override fun onResult(success: Boolean) {
                mainView.onDataRefresh()
            }
        })
    }
}
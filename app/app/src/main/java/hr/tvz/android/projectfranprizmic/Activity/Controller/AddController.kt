package hr.tvz.android.projectfranprizmic.Activity.Controller

import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IToDoChangeCallBack
import hr.tvz.android.projectfranprizmic.Activity.Model.ToDo
import hr.tvz.android.projectfranprizmic.Activity.View.IAddView

class AddController(
    private val addView: IAddView
) : IAddController {
    override fun onAddTask(todo: ToDo) {
        todo.addTask(object : IToDoChangeCallBack {
            override fun onResult(success: Boolean) {
                if(success){
                    addView.onTaskAddSuccess()
                }else{
                    addView.onTaskAddFail()
                }
            }
        })
    }
}
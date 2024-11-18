package hr.tvz.android.projectfranprizmic.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import hr.tvz.android.projectfranprizmic.Activity.Controller.AddController
import hr.tvz.android.projectfranprizmic.Activity.Controller.LoginController
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IValidCallBack
import hr.tvz.android.projectfranprizmic.Activity.Model.ToDo
import hr.tvz.android.projectfranprizmic.Activity.Model.User
import hr.tvz.android.projectfranprizmic.Activity.Model.UserValid
import hr.tvz.android.projectfranprizmic.Activity.View.IAddView
import hr.tvz.android.projectfranprizmic.R
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class AddActivity : ComponentActivity(), IAddView {
    lateinit var timeLayout: LinearLayout
    lateinit var timeCheck: CheckBox
    lateinit var priorityCheck: CheckBox
    lateinit var daysField: TextView
    lateinit var todoField: TextView
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        timeLayout = findViewById(R.id.layoutTimeLimit)
        timeCheck = findViewById(R.id.checkLimited)
        priorityCheck = findViewById(R.id.checkPriority)
        daysField = findViewById(R.id.daysField)
        todoField = findViewById(R.id.taskField)
        submitButton = findViewById(R.id.submitButton)

        timeCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                timeLayout.visibility = View.VISIBLE
            }else{
                timeLayout.visibility = View.INVISIBLE
            }
        }

        val AddController = AddController(this)

        submitButton.setOnClickListener {
            if(!todoField.text.isEmpty()){
                val User = User(-1,
                    LoginController.getUser().getUsername().toString(),
                    LoginController.getUser().getPassword().toString())

                User.isValid(object: IValidCallBack {
                    override fun onResult(valid: UserValid, user: User) {
                        if(valid.code == 0){

                            var priority = 0
                            if(priorityCheck.isChecked) priority = 1

                            var limitedtime = false
                            if(timeCheck.isChecked) limitedtime = true

                            var dayslimited = 1
                            if(timeCheck.isChecked && !daysField.text.isEmpty()) dayslimited = daysField.text.toString().toInt()

                            val todo = ToDo(
                                -1,
                                user.getId(),
                                todoField.text.toString(),
                                priority,
                                false,
                                limitedtime,
                                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                dayslimited
                            )

                            AddController.onAddTask(todo)

                        }else{
                            Log.e("Auth", "Authentication failed")
                        }
                    }
                })
            }
        }
    }

    override fun onTaskAddSuccess() {
        Log.i("ADD", "Success")
        this.finish()
    }

    override fun onTaskAddFail() {
        Log.i("ADD", "Fail")
    }
}
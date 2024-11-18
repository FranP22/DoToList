package hr.tvz.android.projectfranprizmic.Activity.Model

import android.util.Log
import com.google.gson.annotations.SerializedName
import hr.tvz.android.listaprizmic.classes.database.ServiceInterface
import hr.tvz.android.projectfranprizmic.Activity.Controller.LoginController
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IToDoChangeCallBack
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IToDoListCallBack
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IValidCallBack
import hr.tvz.android.projectfranprizmic.Database.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

class ToDo(
    @SerializedName("Id")
    private val id: Int,
    @SerializedName("UserId")
    private val userid: Int,
    @SerializedName("Task")
    private val todo: String?,
    @SerializedName("Priority")
    private val priority: Int,
    @SerializedName("Completed")
    private val completed: Boolean,
    @SerializedName("LimitedTime")
    private val limitedtime: Boolean,
    @SerializedName("DateCreated")
    private val timecreated: Date,
    @SerializedName("DayLimit")
    private val dayslimited: Int,
): IToDo {
    private var daysleft: Int = 0

    override fun getId(): Int {
        return id
    }

    override fun getUserId(): Int {
        return userid
    }

    override fun getToDo(): String? {
        return todo
    }

    override fun getPriority(): Int {
        return priority
    }

    override fun getCompleted(): Boolean {
        return completed
    }

    override fun getLimitedTime(): Boolean {
        return limitedtime
    }

    override fun getTimeCreated(): Date {
        return timecreated
    }

    override fun getDaysLimited(): Int {
        return dayslimited
    }

    override fun getDaysLeft(): Int {
        return daysleft
    }

    override fun setDaysLeft() {
        if(limitedtime){
            val enddate = Date(timecreated.year, timecreated.month, timecreated.date+dayslimited)
            val today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())

            daysleft = ChronoUnit.DAYS.between(today.toInstant(), enddate.toInstant()).toInt()
        }
    }

    companion object {
        private val API_URL = "http://10.0.2.2:8080/api/"

        fun getToDo(callback: IToDoListCallBack) {
            val client: ServiceInterface = ServiceGenerator().createService(
                ServiceInterface::class.java,
                API_URL
            )

            val todocall: Call<MutableList<ToDo>> = client.getAllToDoFromUser(LoginController.getUser().getId())
            todocall.enqueue(object : Callback<MutableList<ToDo>>{
                override fun onResponse(call: Call<MutableList<ToDo>>, response: Response<MutableList<ToDo>>) {
                    if(response.isSuccessful){
                        var result = response.body()!!.toCollection(ArrayList())
                        for(i in result){
                            i.setDaysLeft()
                        }

                        callback.onResult(result)
                    }else{
                        callback.onResult(emptyArray<ToDo>().toCollection(ArrayList()))
                    }
                }

                override fun onFailure(call: Call<MutableList<ToDo>>, t: Throwable) {
                    callback.onResult(emptyArray<ToDo>().toCollection(ArrayList()))
                }
            })
        }

        fun clearToDo(callback: IToDoChangeCallBack) {
            val client: ServiceInterface = ServiceGenerator().createService(
                ServiceInterface::class.java,
                API_URL
            )

            val todocall: Call<ToDo> = client.clearToDo(LoginController.getUser().getId())
            todocall.enqueue(object : Callback<ToDo> {
                override fun onResponse(call: Call<ToDo>, response: Response<ToDo>) {
                    callback.onResult(true)
                }

                override fun onFailure(call: Call<ToDo>, t: Throwable) {
                    callback.onResult(false)
                }
            })
        }
    }

    override fun addTask(callback: IToDoChangeCallBack) {
        val client: ServiceInterface = ServiceGenerator().createService(
            ServiceInterface::class.java,
            API_URL
        )

        val call: Call<ToDo> = client.insertToDo(this@ToDo)
        call.enqueue(object : Callback<ToDo> {
            override fun onResponse(call: Call<ToDo>, response: Response<ToDo>) {
                if(response.isSuccessful) {
                    callback.onResult(true)
                }else{
                    callback.onResult(false)
                }
            }

            override fun onFailure(call: Call<ToDo>, t: Throwable) {
                callback.onResult(false)
            }
        })
    }

    override fun completeTask(callback: IToDoChangeCallBack) {
        val client: ServiceInterface = ServiceGenerator().createService(
            ServiceInterface::class.java,
            API_URL
        )

        var newtodo = ToDo(
            this.id,
            this.userid,
            this.todo,
            this.priority,
            true,
            this.limitedtime,
            this.timecreated,
            this.dayslimited)

        val call: Call<ToDo> = client.putToDo(this.id,newtodo)
        call.enqueue(object : Callback<ToDo> {
            override fun onResponse(call: Call<ToDo>, response: Response<ToDo>) {
                if(response.isSuccessful) {
                    callback.onResult(true)
                }else{
                    callback.onResult(false)
                }
            }

            override fun onFailure(call: Call<ToDo>, t: Throwable) {
                callback.onResult(false)
            }
        })
    }

    override fun deleteTask(callback: IToDoChangeCallBack) {
        val client: ServiceInterface = ServiceGenerator().createService(
            ServiceInterface::class.java,
            API_URL
        )

        val call: Call<ToDo> = client.deleteToDo(this.id)
        call.enqueue(object : Callback<ToDo> {
            override fun onResponse(call: Call<ToDo>, response: Response<ToDo>) {
                if(response.isSuccessful) {
                    callback.onResult(true)
                }else{
                    callback.onResult(false)
                }
            }

            override fun onFailure(call: Call<ToDo>, t: Throwable) {
                callback.onResult(false)
            }
        })
    }
}
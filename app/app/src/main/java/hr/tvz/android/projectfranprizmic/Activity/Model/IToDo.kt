package hr.tvz.android.projectfranprizmic.Activity.Model

import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IToDoChangeCallBack
import java.security.MessageDigest
import java.util.Date

interface IToDo {
    fun getId(): Int
    fun getUserId(): Int
    fun getToDo(): String?
    fun getPriority(): Int
    fun getCompleted(): Boolean
    fun getLimitedTime(): Boolean
    fun getTimeCreated(): Date
    fun getDaysLimited(): Int
    fun getDaysLeft(): Int
    fun setDaysLeft()
    fun addTask(callback: IToDoChangeCallBack)
    fun completeTask(callback: IToDoChangeCallBack)
    fun deleteTask(callback: IToDoChangeCallBack)
}
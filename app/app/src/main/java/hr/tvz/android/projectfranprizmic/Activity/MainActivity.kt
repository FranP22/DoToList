package hr.tvz.android.projectfranprizmic.Activity

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RemoteViews
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.tvz.android.projectfranprizmic.Activity.Controller.LoginController
import hr.tvz.android.projectfranprizmic.Activity.Controller.MainController
import hr.tvz.android.projectfranprizmic.Activity.Model.ToDo
import hr.tvz.android.projectfranprizmic.Activity.View.IMainView
import hr.tvz.android.projectfranprizmic.CustomAdapter
import hr.tvz.android.projectfranprizmic.R

class MainActivity : ComponentActivity(), IMainView {

    lateinit var addButton: Button
    lateinit var completeButton: Button

    lateinit var hpLayout: LinearLayout
    lateinit var lpLayout: LinearLayout

    lateinit var hpList: RecyclerView
    lateinit var lpList: RecyclerView

    lateinit var emptyView: TextView

    fun loadData(){
        addButton = findViewById(R.id.addViewButton)
        completeButton = findViewById(R.id.completeViewButton)

        hpLayout = findViewById(R.id.HPLayout)
        lpLayout = findViewById(R.id.LPLayout)

        hpList = findViewById(R.id.HPList)
        lpList = findViewById(R.id.LPList)

        emptyView = findViewById(R.id.emptyView)

        addButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddActivity::class.java))
        }
        completeButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainCompletedActivity::class.java))
        }

        val mainController = MainController(this)
        mainController.onLoadList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    override fun onLoadListSuccess(todos: ArrayList<ToDo>) {
        var filteredtodo = todos.filter { it.getCompleted()==false }
        if(filteredtodo.isEmpty()){
            emptyView.visibility = View.VISIBLE
        }else{
            emptyView.visibility = View.GONE
        }

        var limitedtime = filteredtodo.filter { it.getLimitedTime() == true }
        var nonlimitedtime = filteredtodo.filter { it.getLimitedTime() == false }

        limitedtime = limitedtime.sortedBy { it.getDaysLeft() }

        filteredtodo = limitedtime + nonlimitedtime

        var highprio = filteredtodo.filter { it.getPriority() == 1 }
        var lowprio = filteredtodo.filter { it.getPriority() == 0 }

        if(!highprio.isEmpty()){
            hpLayout.visibility = View.VISIBLE

            hpList.layoutManager = LinearLayoutManager(this)
            hpList.adapter = CustomAdapter(highprio, this)
        }

        if(!lowprio.isEmpty()){
            lpLayout.visibility = View.VISIBLE

            lpList.layoutManager = LinearLayoutManager(this)
            lpList.adapter = CustomAdapter(lowprio, this)
        }
    }

    override fun onLoadListFail() {
        emptyView.visibility = View.VISIBLE
    }

    override fun onDataRefresh() {
        recreate()
    }

    /*private fun updateWidget(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val componentName = ComponentName(context, TaskWidget::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)

        // Create an instance of RemoteViews
        val views = RemoteViews(context.packageName, R.layout.task_widget)

        // Set new text for the widget
        val newText = "Updated from MainActivity"
        views.setTextViewText(R.id.widgetText, newText)

        // Update all widgets with the new text
        appWidgetManager.updateAppWidget(appWidgetIds, views)
    }*/
}
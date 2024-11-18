package hr.tvz.android.projectfranprizmic.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.tvz.android.projectfranprizmic.Activity.Controller.MainController
import hr.tvz.android.projectfranprizmic.Activity.Model.ToDo
import hr.tvz.android.projectfranprizmic.Activity.View.IMainView
import hr.tvz.android.projectfranprizmic.CustomAdapter
import hr.tvz.android.projectfranprizmic.CustomAdapterCompleted
import hr.tvz.android.projectfranprizmic.R

class MainCompletedActivity : ComponentActivity(), IMainView {
    lateinit var clearButton: Button
    lateinit var compLayout: LinearLayout
    lateinit var compRecycler: RecyclerView
    lateinit var emptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed)

        clearButton = findViewById(R.id.clearButton)
        compLayout = findViewById(R.id.completeLayout)
        compRecycler = findViewById(R.id.completeList)
        emptyText = findViewById(R.id.emptyView)

        val mainController = MainController(this)
        mainController.onLoadList()

        clearButton.setOnClickListener{
            mainController.onClearCompleted()
        }
    }

    override fun onLoadListSuccess(todos: ArrayList<ToDo>) {
        var filteredtodo = todos.filter { it.getCompleted()==true }

        if(filteredtodo.isEmpty()){
            emptyText.visibility = View.VISIBLE
        }else{
            filteredtodo = filteredtodo.sortedByDescending { it.getId() }

            compLayout.visibility = View.VISIBLE
            clearButton.visibility = View.VISIBLE

            compRecycler.layoutManager = LinearLayoutManager(this)
            compRecycler.adapter = CustomAdapterCompleted(filteredtodo, this)
        }
    }

    override fun onLoadListFail() {
        emptyText.visibility = View.VISIBLE
    }

    override fun onDataRefresh() {
        recreate()
    }
}
package hr.tvz.android.projectfranprizmic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IToDoChangeCallBack
import hr.tvz.android.projectfranprizmic.Activity.Model.ToDo
import hr.tvz.android.projectfranprizmic.Activity.View.IAddView
import hr.tvz.android.projectfranprizmic.Activity.View.IMainView

class CustomAdapter(private val dataArray: List<ToDo>, private val mainView: IMainView) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeText: TextView = itemView.findViewById(R.id.timeText)
        private val taskText: TextView = itemView.findViewById(R.id.taskText)
        private val completeButton: Button = itemView.findViewById(R.id.completeButton)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(item: ToDo, main: IMainView) {
            if(item.getLimitedTime()){
                timeText.text = item.getDaysLeft().toString()
            }else{
                timeText.text = ""
            }

            taskText.text = item.getToDo()

            completeButton.setOnClickListener {
                item.completeTask(object : IToDoChangeCallBack {
                    override fun onResult(success: Boolean) {
                        if(success) main.onDataRefresh()
                    }
                })
            }
            deleteButton.setOnClickListener {
                item.deleteTask(object : IToDoChangeCallBack {
                    override fun onResult(success: Boolean) {
                        if(success) main.onDataRefresh()
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataArray[position]
        holder.bind(item, mainView)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }
}
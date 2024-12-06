package com.example.android_todoapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_todoapp.databinding.LayoutItemTaskBinding
import com.example.android_todoapp.model.DONE
import com.example.android_todoapp.model.TaskModel

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val taskModels = mutableListOf<TaskModel>()
    var onItemClick: (TaskModel) -> Unit = {}
    var onRemoveClick: (TaskModel) -> Unit = {}
    var onStateClick: (TaskModel) -> Unit = {}

    inner class TaskViewHolder(private val binding: LayoutItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: TaskModel) {
            binding.apply {
                val isDone = item.status == DONE

                txtTitleTask.text = item.taskName
                txtTime.text = item.dateTime
                imgStatus.isActivated = isDone
                vBackground.isActivated = isDone

                imgRemove.setOnClickListener {
                    onRemoveClick(item)
                }

                imgStatus.setOnClickListener {
                    onStateClick(item)
                }

                imgEdit.setOnClickListener {
                    onItemClick(item)
                }

                root.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = taskModels.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(taskModels[position])
    }

    fun setData(data: List<TaskModel>) {
        taskModels.clear()
        taskModels.addAll(data)
        notifyDataSetChanged()
    }
}
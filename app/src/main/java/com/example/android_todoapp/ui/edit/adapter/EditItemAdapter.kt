package com.example.android_todoapp.ui.edit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android_todoapp.R
import com.example.android_todoapp.databinding.LayoutItemCategoriesStatusBinding

class EditItemAdapter(private val context: Context) :
    RecyclerView.Adapter<EditItemAdapter.EditItemViewHolder>() {
    private val editUiModels = mutableListOf<EditUIModel>()
    private var currentItemSelected: Int = 0
    var onItemClick: (EditUIModel) -> Unit = {}

    inner class EditItemViewHolder(private val binding: LayoutItemCategoriesStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: EditUIModel, position: Int) {
            binding.txtContent.apply {
                text = item.content
                isActivated = currentItemSelected == position
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isActivated) R.color.white else R.color.primary_text_color
                    )
                )
            }
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditItemViewHolder {
        return EditItemViewHolder(
            LayoutItemCategoriesStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = editUiModels.size

    override fun onBindViewHolder(holder: EditItemViewHolder, position: Int) {
        holder.onBind(editUiModels[position], position)
    }

    fun setData(editUiModels: List<EditUIModel>) {
        this.editUiModels.clear()
        this.editUiModels.addAll(editUiModels)
        notifyDataSetChanged()
    }

    fun updateItemSelected(itemSelected: EditUIModel) {
        val lastSelected = currentItemSelected
        currentItemSelected = editUiModels.indexOf(itemSelected)
        notifyItemChanged(lastSelected)
        notifyItemChanged(currentItemSelected)
    }


}
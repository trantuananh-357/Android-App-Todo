package com.example.android_todoapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android_todoapp.R
import com.example.android_todoapp.databinding.LayoutItemCategoryTaskBinding
import com.example.android_todoapp.extension.loadImage
import com.example.android_todoapp.model.CategoriesTaskModel

class CategoriesTaskAdapter(private val context: Context) :
    RecyclerView.Adapter<CategoriesTaskAdapter.CategoriesTaskViewHolder>() {
    private val categoriesTaskModels = mutableListOf<CategoriesTaskModel>()

    inner class CategoriesTaskViewHolder(private val binding: LayoutItemCategoryTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CategoriesTaskModel, position: Int) {
            binding.apply {
                imgCategoryTask.loadImage(item.icon)
                txtTitleCategoryTask.setText(item.title)
                txtCountTask.text = String.format(item.totalTask.toString())
                ctlBackground.setBackgroundColor(ContextCompat.getColor(context, listColor[position % 4]))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesTaskViewHolder {
        return CategoriesTaskViewHolder(
            LayoutItemCategoryTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = categoriesTaskModels.size

    override fun onBindViewHolder(holder: CategoriesTaskViewHolder, position: Int) {
        holder.onBind(categoriesTaskModels[position], position)
    }

    fun setData(data: List<CategoriesTaskModel>) {
        categoriesTaskModels.clear()
        categoriesTaskModels.addAll(data)
        notifyDataSetChanged()
    }

    companion object {
        val listColor = listOf(
            R.color.color_B4C4FF,
            R.color.color_CFF3E9,
            R.color.color_669747FF,
            R.color.color_EDBE7D,
        )
    }
}
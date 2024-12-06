package com.example.android_todoapp.ui.settinglanguage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_todoapp.databinding.LayoutItemLanguageBinding
import com.example.android_todoapp.extension.loadImage
import com.example.android_todoapp.model.LanguageModel

class SettingLanguageAdapter :
    RecyclerView.Adapter<SettingLanguageAdapter.SettingLanguageViewHolder>() {

    private var listLanguage = mutableListOf<LanguageModel>()
    private var languageCurrent: LanguageModel? = null

    var setOnClickItem: (LanguageModel) -> Unit = {}

    inner class SettingLanguageViewHolder(private val binding: LayoutItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(languageModel: LanguageModel) {
            binding.apply {
                imgLanguage.loadImage(languageModel.languageFlag)
                txtLanguage.text = root.context.getText(languageModel.languageName)
                imgSelector.isSelected = languageCurrent?.languageCode == languageModel.languageCode
                root.setOnClickListener {
                    setOnClickItem.invoke(languageModel)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingLanguageViewHolder {
        return SettingLanguageViewHolder(
            LayoutItemLanguageBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SettingLanguageViewHolder, position: Int) {
        holder.bind(listLanguage[position])
    }

    override fun getItemCount(): Int {
        return listLanguage.size
    }

    fun setData(listLanguage: List<LanguageModel>) {
        this.listLanguage.clear()
        this.listLanguage.addAll(listLanguage)
        notifyDataSetChanged()
    }

    fun setCurrentLanguage(languageModel: LanguageModel) {
        if (languageCurrent == languageModel) {
            return
        }
        if (languageCurrent != null) {
            notifyItemChanged(listLanguage.indexOfFirst { it.languageCode == languageCurrent!!.languageCode })
        }
        languageCurrent = languageModel
        notifyItemChanged(listLanguage.indexOfFirst { it.languageCode == languageModel.languageCode })

    }

}
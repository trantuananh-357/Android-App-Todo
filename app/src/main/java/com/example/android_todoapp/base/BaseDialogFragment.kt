package com.apero.luxelook.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.apero.common.data.pref.SharePref
import com.example.android_todoapp.extension.getContextWithResources
import org.koin.android.ext.android.inject

abstract class BaseDialogFragment<V : ViewDataBinding> : DialogFragment() {
    protected abstract val layoutId: Int
    protected lateinit var binding: V

    protected val sharePref by inject<SharePref>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(getContextWithResources(context, sharePref.language)!!)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    abstract fun initView()

}
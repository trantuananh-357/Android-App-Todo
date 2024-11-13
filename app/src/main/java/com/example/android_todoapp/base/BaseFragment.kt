package com.apero.luxelook.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), View.OnClickListener {
    protected lateinit var binding: T
    protected abstract val layoutId: Int

    protected open val timeClick: Long = TIME_CLICK_DEFAULT_TIME

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObserver()
        setupUI()
        setupData()
    }

    open fun setupObserver() {}
    protected open fun setupData() {}
    protected open fun setupUI() {}
    protected open fun setupListener() {}
    private var lastTimeClick = 0L
    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTimeClick < timeClick) return
        lastTimeClick = currentTime
        singleClickListener(v)
    }

    protected open fun singleClickListener(v: View?) {}

    companion object {
        private const val TIME_CLICK_DEFAULT_TIME = 500L
    }
}

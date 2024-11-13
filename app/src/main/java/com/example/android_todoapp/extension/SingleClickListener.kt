package com.example.android_todoapp.extension

import android.view.View
import android.view.View.OnClickListener
import com.example.android_todoapp.extension.SingleClickListener.Companion.TIMER_CLICK

fun View.singleClickListener(
    timeClick: Long = TIMER_CLICK,
    callback: (View) -> Unit
) {
    setOnClickListener(object : SingleClickListener(timeClick) {
        override fun onSingleClick(view: View) {
            callback(view)
        }
    })
}

abstract class SingleClickListener(private val timeCLick: Long = TIMER_CLICK) :
    OnClickListener {

    private var lastTime = 0L
    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (v?.isEnabled != true || currentTime - lastTime < timeCLick) {
            return
        }
        lastTime = currentTime
        onSingleClick(v)
    }

    abstract fun onSingleClick(view: View)

    companion object {
        const val TIMER_CLICK = 500L
        const val TIMER_CLICK_SHORT = 200L
        const val TIMER_CLICK_LONG = 1000L
    }

}

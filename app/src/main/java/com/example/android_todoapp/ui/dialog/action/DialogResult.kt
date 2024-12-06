package com.example.android_todoapp.ui.dialog.action

import android.os.Bundle
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import androidx.core.view.isVisible
import com.apero.luxelook.base.BaseDialogFragment
import com.example.android_todoapp.R
import com.example.android_todoapp.databinding.ClothesDialogActionBinding
import com.example.android_todoapp.extension.singleClickListener

internal class DialogResult : BaseDialogFragment<ClothesDialogActionBinding>() {
    override val layoutId: Int
        get() = R.layout.clothes_dialog_action

    private var listener: Listener? = null

    private val contentDialog: ActionResult by lazy {
        BundleCompat.getSerializable(
            requireArguments(),
            KEY_CONTENT_DIALOG,
            ActionResult::class.java
        ) ?: ActionResult.Exit
    }

    override fun initView() {
        dialog?.window?.setBackgroundDrawable(null)
        val isShowContent = requireArguments().getBoolean(KEY_IS_SHOW_CONTENT, true)
        val isShowAds = requireArguments().getBoolean(KEY_IS_SHOW_ADS, true)
        binding.apply {
            initListener()

            txtTitle.setText(contentDialog.title)
            txtPositive.setText(contentDialog.action)
            txtNegative.setText(contentDialog.actionNegative)
            imgAds.isVisible = isShowAds
            txtContent.isVisible = isShowContent
            if (isShowContent) {
                txtContent.setText(contentDialog.content)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.92).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun ClothesDialogActionBinding.initListener() {
        btnClose.singleClickListener {
            dismiss()
        }
        vPositive.singleClickListener {
            listener?.onPositiveClick()
            dismiss()
        }
        txtNegative.singleClickListener {
            listener?.onNegativeClick()
            dismiss()
        }
    }

    interface Listener {
        fun onPositiveClick()
        fun onNegativeClick()

    }

    companion object {
        private const val KEY_CONTENT_DIALOG = "KEY_CONTENT_DIALOG"
        private const val KEY_IS_SHOW_CONTENT = "KEY_IS_SHOW_CONTENT"
        private const val KEY_IS_SHOW_ADS = "KEY_IS_SHOW_ADS"

        @JvmStatic
        fun newInstance(
            contentDialog: ActionResult,
            listener: Listener,
            isShowContent: Boolean,
            isShowAds: Boolean = false
        ): DialogResult {
            val args = Bundle()
            args.putBoolean(KEY_IS_SHOW_CONTENT, isShowContent)
            args.putBoolean(KEY_IS_SHOW_ADS, isShowAds)
            args.putSerializable(KEY_CONTENT_DIALOG, contentDialog)
            val fragment = DialogResult()
            fragment.listener = listener
            fragment.arguments = args
            return fragment
        }
    }
}
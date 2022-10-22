package com.nrahmatd.storyapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.databinding.BottomSheetConfirmBinding

class BottomSheetConfirmDialog(
    val title: String,
    private val btnYesText: String,
    private val btnNoText: String
) : BottomSheetDialogFragment() {

    lateinit var listener: OnClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = BottomSheetConfirmBinding.inflate(inflater, container, false)

        view.tvConfirm.text = title
        view.btnYes.text = btnYesText
        view.btnCancel.text = btnNoText
        view.ivClose.setOnClickListener { dismiss() }
        view.btnYes.setOnClickListener { listener.onYes() }
        view.btnCancel.setOnClickListener { dismiss() }
        return view.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    interface OnClickListener {
        fun onYes()
    }
}

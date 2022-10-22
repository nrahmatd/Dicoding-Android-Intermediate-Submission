package com.nrahmatd.storyapp.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.databinding.BottomSheetChooseBinding

class BottomSheetDialogChoose : BottomSheetDialogFragment() {
    lateinit var listener: OnClickListener

    companion object {
        const val CAMERA = 1
        const val GALLERY = 2
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = BottomSheetChooseBinding.inflate(inflater, container, false)

        view.tvCamera.setOnClickListener {
            listener.onClick(CAMERA)
        }

        view.tvGallery.setOnClickListener {
            listener.onClick(GALLERY)
        }

        view.ivClose.setOnClickListener { dismiss() }

        return view.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                val dialog = dialog as BottomSheetDialog?
//                val bottomSheet = dialog!!.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
//                val behavior = BottomSheetBehavior.from(bottomSheet as FrameLayout)
//                behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                behavior.peekHeight = 250 // Remove this line to hide a dark background if you manually hide the dialog.
//            }
//        })
//    }

    interface OnClickListener {
        fun onClick(type: Int)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}

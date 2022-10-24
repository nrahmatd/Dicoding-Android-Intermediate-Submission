package com.nrahmatd.storyapp.home.view.fragment

import android.content.Intent
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.app.GlobalApp
import com.nrahmatd.storyapp.databinding.FragmentSettingsBinding
import com.nrahmatd.storyapp.dialog.BottomSheetConfirmDialog
import com.sagara.klipz.baseview.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private lateinit var fm: FragmentManager

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingsBinding
        get() = FragmentSettingsBinding::inflate

    override fun setup() {
        initView()
        initOnClick()
    }

    private fun initView() {
        fm = childFragmentManager
    }

    private fun initOnClick() {
        binding.apply {
            btnLanguageSetting.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            btnLogout.setOnClickListener {
                showBottomSheetDialog()
            }
        }
    }

    private fun showBottomSheetDialog() {
        BottomSheetConfirmDialog(
            getString(R.string.are_you_sure_you_want_to_leave),
            getString(R.string.yes),
            getString(R.string.no)
        ).apply {
            show(fm, "Show")
            listener = object : BottomSheetConfirmDialog.OnClickListener {
                override fun onYes() {
                    GlobalApp.instance.logout()
                }
            }
        }
    }
}

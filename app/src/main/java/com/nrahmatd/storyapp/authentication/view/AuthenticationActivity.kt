package com.nrahmatd.storyapp.authentication.view

import android.os.Bundle
import android.view.LayoutInflater
import com.nrahmatd.storyapp.baseview.BaseActivity
import com.nrahmatd.storyapp.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityAuthenticationBinding
        get() = ActivityAuthenticationBinding::inflate

    override fun setup(savedInstanceState: Bundle?) {
    }

    override fun statusBarColor(): Int = 0
}

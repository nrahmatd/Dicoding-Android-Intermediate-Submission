package com.nrahmatd.storyapp.splashscreen.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.nrahmatd.storyapp.authentication.view.AuthenticationActivity
import com.nrahmatd.storyapp.baseview.BaseActivity
import com.nrahmatd.storyapp.database.sharedpref.PreferenceManager
import com.nrahmatd.storyapp.databinding.ActivitySplashscreenBinding
import com.nrahmatd.storyapp.home.view.MainActivity
import com.nrahmatd.storyapp.utils.GlobalVariable
import kotlinx.coroutines.delay

class SplashScreenActivity : BaseActivity<ActivitySplashscreenBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivitySplashscreenBinding
        get() = ActivitySplashscreenBinding::inflate

    override fun setup(savedInstanceState: Bundle?) {
        init()
    }

    override fun statusBarColor(): Int = 0

    private fun init() {
        lifecycleScope.launchWhenCreated {
            delay(1500)
            finish()
            if (PreferenceManager.instance.getString(GlobalVariable.ACCESS_TOKEN, null)
                    .isNullOrEmpty()
            )
                startActivity(Intent(this@SplashScreenActivity, AuthenticationActivity::class.java))
            else
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        }
    }
}

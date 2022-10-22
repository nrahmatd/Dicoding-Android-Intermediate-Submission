package com.nrahmatd.storyapp.authentication.view.fragment

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.authentication.model.LoginModel
import com.nrahmatd.storyapp.authentication.viewmodel.AuthViewModel
import com.nrahmatd.storyapp.authentication.viewmodel.AuthViewModelFactory
import com.nrahmatd.storyapp.database.sharedpref.PreferenceManager
import com.nrahmatd.storyapp.databinding.FragmentLoginBinding
import com.nrahmatd.storyapp.home.view.MainActivity
import com.nrahmatd.storyapp.utils.GlobalVariable
import com.nrahmatd.storyapp.utils.ResponseHelper
import com.nrahmatd.storyapp.utils.customview.EmailEditText
import com.nrahmatd.storyapp.utils.customview.PasswordEditText
import com.nrahmatd.storyapp.utils.general_model.GeneralModel
import com.nrahmatd.storyapp.utils.transitionFade
import com.sagara.klipz.baseview.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(), EmailEditText.OnEditTextChangeListener,
    PasswordEditText.OnEditTextChangeListener {

    /**
     * Index 0: For validation email input
     * Index 1 : For validation email input
     */
    private val isValidationForm = arrayOf(false, false)
    private var showPassword = false
    private lateinit var authViewModel: AuthViewModel

    companion object {
        const val LOGIN = 1
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setup() {
        initView()
        initViewModel()
        initOnClick()
        getResponse()
    }

    private fun initView() {
        binding.apply {
            etEmail.onEditTextChangeListener = this@LoginFragment
            etPassword.onEditTextChangeListener = this@LoginFragment
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    override fun onEmailValidation(isValid: Boolean) {
        binding.apply {
            setFormValidation(isValid, 0)
            if (isValid) {
                tvEmailIncorrect.visibility = View.GONE
            } else {
                tvEmailIncorrect.text = getString(R.string.the_email_not_yet_valid)
                tvEmailIncorrect.visibility = View.VISIBLE
            }
        }
    }

    override fun onPasswordValidation(isValid: Boolean) {
        binding.apply {
            setFormValidation(isValid, 1)
            if (isValid) {
                tvPasswordIncorrect.visibility = View.GONE
            } else {
                tvPasswordIncorrect.text =
                    getString(R.string.use_6_characters_or_more_for_your_password)
                tvPasswordIncorrect.visibility = View.VISIBLE
            }
        }
    }

    private fun initViewModel() {
        authViewModel = ViewModelProvider(this, AuthViewModelFactory())[AuthViewModel::class.java]
    }

    private fun initOnClick() {
        binding.apply {
            ivShowPassword.setOnClickListener { showPassword() }
            tvSignUp.setOnClickListener(navigateToRegister())
            btnLogin.setOnClickListener {
                setFormDefaultColor()
                login()
            }
        }
    }

    private fun navigateToRegister() =
        Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)

    private fun setFormValidation(condition: Boolean, index: Int) {
        isValidationForm[index] = condition
        validateButtonIsEnable()
    }

    private fun setFormDefaultColor() {
        binding.apply {
            tvEmailIncorrect.visibility = View.GONE
            tvPasswordIncorrect.visibility = View.GONE
            tvEmailLabel.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.black
                )
            )
            tvPasswordLabel.setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.black
                )
            )
        }
    }

    private fun setFormIncorrectColor(error: String) {
        binding.apply {
            if (error.contains("user", ignoreCase = true)) {
                tvEmailLabel.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.red
                    )
                )
                tvEmailIncorrect.text = getString(R.string.email_account_not_registered)
                tvEmailIncorrect.visibility = View.VISIBLE
                transitionFade(root, tvEmailIncorrect)
            }

            if (error.contains("password", ignoreCase = true)) {
                tvPasswordLabel.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.red
                    )
                )
                tvPasswordIncorrect.text = getString(R.string.invalid_password)
                tvPasswordIncorrect.visibility = View.VISIBLE
                transitionFade(root, tvPasswordIncorrect)
            }
        }
    }

    private fun validateFormIsValid(): Boolean =
        isValidationForm.all { it } and binding.etEmail.text.toString()
            .isNotEmpty() and binding.etPassword.text.toString().isNotEmpty()

    private fun validateButtonIsEnable() {
        binding.apply {
            btnLogin.isEnabled = validateFormIsValid()
            if (validateFormIsValid())
                btnLogin.setBackgroundResource(R.drawable.ripple_rounded_grey)
            else
                btnLogin.setBackgroundResource(R.drawable.ripple_rounded_primary)
        }
    }

    private fun showPassword() {
        showPassword = !showPassword
        binding.apply {
            if (showPassword) {
                ivShowPassword.setImageResource(R.drawable.ic_show_password_active)
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                ivShowPassword.setImageResource(R.drawable.ic_show_password_inactive)
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    private fun loading() {
        binding.apply {
            btnLogin.visibility = View.INVISIBLE
            spinKitLoading.visibility = View.VISIBLE
        }
    }

    private fun loadingDone() {
        binding.apply {
            btnLogin.visibility = View.VISIBLE
            spinKitLoading.visibility = View.GONE
        }
    }

    private fun login() = authViewModel.login(
        LOGIN,
        binding.etEmail.text.toString(),
        binding.etPassword.text.toString()
    )

    private fun getResponse() {
        authViewModel.response.observe(this) {
            when (it.code) {
                ResponseHelper.ERROR or ResponseHelper.BadRequest -> {
                    val errorModel = it.response as GeneralModel
                    setFormIncorrectColor(errorModel.message)
                    loadingDone()
                }
                ResponseHelper.LOADING -> {
                    val loading = it.response as Boolean
                    if (loading) loading() else loadingDone()
                }
                LOGIN -> {
                    val loginModel = it.response as LoginModel
                    if (loginModel.error) {
                        setFormIncorrectColor(loginModel.message)
                    } else {
                        PreferenceManager.instance.putString(GlobalVariable.ACCESS_TOKEN, loginModel.loginResult?.token)
                        startActivity(Intent(requireActivity(), MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                    }
                }
            }
        }
    }
}

package com.nrahmatd.storyapp.authentication.view.fragment

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.authentication.viewmodel.AuthViewModel
import com.nrahmatd.storyapp.authentication.viewmodel.AuthViewModelFactory
import com.nrahmatd.storyapp.databinding.FragmentRegisterBinding
import com.nrahmatd.storyapp.utils.ResponseHelper
import com.nrahmatd.storyapp.utils.customview.EmailEditText
import com.nrahmatd.storyapp.utils.customview.NameEditText
import com.nrahmatd.storyapp.utils.customview.PasswordEditText
import com.nrahmatd.storyapp.utils.general_model.GeneralModel
import com.nrahmatd.storyapp.utils.toast
import com.nrahmatd.storyapp.utils.transitionFade
import com.sagara.klipz.baseview.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(),
    NameEditText.OnEditTextChangeListener, EmailEditText.OnEditTextChangeListener,
    PasswordEditText.OnEditTextChangeListener {

    /**
     * Index 0: For validation name input
     * Index 1: For validation email input
     * Index 2 : For validation password input
     */
    private val isValidationForm = arrayOf(false, false, false)
    private var showPassword = false
    private lateinit var authViewModel: AuthViewModel

    companion object {
        const val REGISTER = 1
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate

    override fun setup() {
        initView()
        initViewModel()
        initOnClick()
        getResponse()
    }

    private fun initView() {
        binding.apply {
            etName.onEditTextChangeListener = this@RegisterFragment
            etEmail.onEditTextChangeListener = this@RegisterFragment
            etPassword.onEditTextChangeListener = this@RegisterFragment
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    override fun onNameValidation(isValid: Boolean) {
        binding.apply {
            setFormValidation(isValid, 0)
            if (isValid) {
                tvUsernameIncorrect.visibility = View.GONE
            } else {
                tvUsernameIncorrect.text =
                    getString(R.string.use_3_characters_or_more_for_your_name)
                tvUsernameIncorrect.visibility = View.VISIBLE
            }
        }
    }

    override fun onEmailValidation(isValid: Boolean) {
        binding.apply {
            setFormValidation(isValid, 1)
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
            setFormValidation(isValid, 2)
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
            btnBack.setOnClickListener(navigateToLogin())
            ivShowPassword.setOnClickListener { showPassword() }
            btnRegister.setOnClickListener {
                setFormDefaultColor()
                register()
            }
        }
    }

    private fun navigateToLogin() =
        Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment)

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
            if (error.contains("email", ignoreCase = true)) {
                tvEmailLabel.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.red
                    )
                )
                val message = if (error.contains(
                        "already",
                        ignoreCase = true
                    )
                ) getString(R.string.this_email_account_already_exists) else getString(R.string.email_must_be_valid)
                tvEmailIncorrect.text = message
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
                tvPasswordIncorrect.text = error
                tvPasswordIncorrect.visibility = View.VISIBLE
                transitionFade(root, tvPasswordIncorrect)
            }
        }
    }

    private fun validateFormIsValid(): Boolean =
        isValidationForm.all { it } and binding.etName.text.toString()
            .isNotEmpty() and binding.etEmail.text.toString()
            .isNotEmpty() and binding.etPassword.text.toString().isNotEmpty()

    private fun validateButtonIsEnable() {
        binding.apply {
            btnRegister.isEnabled = validateFormIsValid()
            if (validateFormIsValid())
                btnRegister.setBackgroundResource(R.drawable.ripple_rounded_grey)
            else
                btnRegister.setBackgroundResource(R.drawable.ripple_rounded_primary)
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
            btnRegister.visibility = View.INVISIBLE
            spinKitLoading.visibility = View.VISIBLE
        }
    }

    private fun loadingDone() {
        binding.apply {
            btnRegister.visibility = View.VISIBLE
            spinKitLoading.visibility = View.GONE
        }
    }

    private fun register() = authViewModel.register(
        REGISTER,
        binding.etName.text.toString(),
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
                REGISTER -> {
                    val generalModel = it.response as GeneralModel
                    if (generalModel.error) {
                        setFormIncorrectColor(generalModel.message)
                    } else {
                        toast(requireActivity(), getString(R.string.success_register))
                        Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
                    }
                }
            }
        }
    }
}

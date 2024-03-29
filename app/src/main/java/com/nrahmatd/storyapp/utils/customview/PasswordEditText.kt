package com.nrahmatd.storyapp.utils.customview

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.nrahmatd.storyapp.R

class PasswordEditText : TextInputEditText {

    lateinit var onEditTextChangeListener: OnEditTextChangeListener

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        setHint(R.string.password)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setAutofillHints(AUTOFILL_HINT_PASSWORD)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setAutofillHints(AUTOFILL_HINT_PASSWORD)
        }

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /** Password validation
                 *  Trigger listener with condition isValid
                 */
                if (!s.isNullOrEmpty() && s.length < 6)
                    onEditTextChangeListener.onPasswordValidation(false)
                else
                    onEditTextChangeListener.onPasswordValidation(true)
            }
        })
    }

    interface OnEditTextChangeListener {
        fun onPasswordValidation(isValid: Boolean)
    }
}

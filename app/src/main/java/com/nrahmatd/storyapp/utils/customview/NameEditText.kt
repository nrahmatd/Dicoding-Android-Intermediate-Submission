package com.nrahmatd.storyapp.utils.customview

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.nrahmatd.storyapp.R

class NameEditText : TextInputEditText {

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
        inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME

        setHint(R.string.name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setAutofillHints(AUTOFILL_HINT_NAME)
        }

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /** Password validation
                 *  Trigger listener with condition isValid
                 */
                if (!s.isNullOrEmpty() && s.length < 3)
                    onEditTextChangeListener.onNameValidation(false)
                else
                    onEditTextChangeListener.onNameValidation(true)
            }
        })
    }

    interface OnEditTextChangeListener {
        fun onNameValidation(isValid: Boolean)
    }
}

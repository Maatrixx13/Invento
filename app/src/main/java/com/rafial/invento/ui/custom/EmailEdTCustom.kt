package com.rafial.invento.ui.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.rafial.invento.R

class EmailEdTCustom: EditTextCustom {

    private lateinit var clearButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr) {
        init()
    }
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
        private fun init() {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (!text.isNullOrBlank()) {
                        error = if (!isEmailValid(text.toString())) {
                            resources.getString(R.string.valid_email)
                        } else {
                            null
                        }
                    }

                }
                override fun afterTextChanged(s: Editable) {
                    if (text.isNullOrEmpty()) {
                        error = resources.getString(R.string.email_empty)
                    }
                }
            })
        }


}

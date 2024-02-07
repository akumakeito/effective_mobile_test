package ru.akumakeito.util

import android.text.Editable
import android.text.TextWatcher

class PhoneNumberFormattingTextWatcher : TextWatcher {

    private var isFormatting: Boolean = false
    private var isDeleting: Boolean = false

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (isFormatting) return

        isFormatting = true

        val numericOnly = s.toString().replace(Regex("[^\\d]"), "")
        val formatted = StringBuilder()

        for (i in numericOnly.indices) {

            if (i >= 0 && i < 4) {
                formatted.append(numericOnly[i])
            }
            if (i == 4) {
                formatted.append(" ")
            }
            if (i > 4 && i <= 7) {
                formatted.append(numericOnly[i])
            }
            if (i == 7 || i == 9) {
                formatted.append("-")
            }
            if (i > 7 && i < 11) {
                formatted.append(numericOnly[i])
            }
        }

        s.replace(0, s.length, formatted)

        isFormatting = false
    }
}
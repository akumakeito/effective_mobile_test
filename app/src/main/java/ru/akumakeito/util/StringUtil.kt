package ru.akumakeito.util

import android.content.Context
import ru.akumakeito.effectivemobile_test.R

class StringUtil {

    companion object {
        fun getAvailableString(count: Int): String {
            val reminder10 = count % 10
            val reminder100 = count % 100

            return when {
                reminder10 in 2..4 -> "штуки"
                reminder10 == 1 -> "штука"
                reminder100 in 11..14 -> "штук"
                else -> "штук"
            }
        }

        fun getFeedbackString(count: Int): String {
            val reminder10 = count % 10
            val reminder100 = count % 100

            return when {
                reminder10 in 2..4 -> "отзыва"
                reminder10 == 1 -> "отзыв"
                reminder100 in 11..14 -> "отзывов"
                else -> "отзывов"
            }
        }

        fun getItemCountString(count: Int): String {
            val reminder10 = count % 10
            val reminder100 = count % 100

            return when {
                reminder10 in 2..4 -> "товара"
                reminder10 == 1 -> "товар"
                reminder100 in 11..14 -> "товаров"
                else -> "товаров"
            }
        }
        fun getHideOrShowText(context: Context, isHide: CharSequence): String =
            if ( context.getString(R.string.hide) == isHide)  context.getString(R.string.show) else context.getString(R.string.hide)

        fun formatPhoneNumber(phoneNumber: String): String {
            return phoneNumber.replaceFirst(
                Regex("(\\d{3})(\\d{3})(\\d{2})(\\d{2})"),
                "+7 $1 $2 $3 $4"
            )
        }

        fun isOnlyRussianLetters(input: String): Boolean {
            val russianLettersRegex = Regex("^[а-яА-ЯёЁ]+$")
            return russianLettersRegex.matches(input)
        }

    }




}
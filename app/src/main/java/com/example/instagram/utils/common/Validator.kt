package com.example.instagram.utils.common

import com.example.instagram.R
import java.util.regex.Pattern

object Validator {
    private val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    private const val MIN_PASSWORD_LENGTH = 6
    private const val MIN_NAME_LENGTH = 3
    fun validateLoginField(email: String?, password: String?):
            ArrayList<Validation> = ArrayList<Validation>().apply {
        when {
            email.isNullOrEmpty() -> add(
                Validation(
                    Validation.Field.EMAIL,
                    Resource.error(R.string.email_cant_empty)
                )
            )
            EMAIL_ADDRESS.matcher(email).matches().not() ->
                add(
                    Validation(
                        Validation.Field.EMAIL,
                        Resource.error(R.string.not_valid_email)
                    )
                )
            else -> add(Validation(Validation.Field.EMAIL, Resource.success()))
        }
        when {
            password.isNullOrEmpty() -> add(
                Validation(
                    Validation.Field.PASSWORD,
                    Resource.error(R.string.password_cant_empty)
                )
            )
            password.length < MIN_PASSWORD_LENGTH ->
                add(
                    Validation(
                        Validation.Field.PASSWORD,
                        Resource.error(R.string.not_valid_password)
                    )
                )
            else -> add(Validation(Validation.Field.PASSWORD, Resource.success()))

        }
    }

    fun validateSignUpField(name: String?, email: String?, password: String?):
            ArrayList<Validation> = ArrayList<Validation>().apply {
        when {
            name.isNullOrEmpty() -> add(
                Validation(
                    Validation.Field.NAME,
                    Resource.error(R.string.name_cant_empty)
                )
            )
            name.length < MIN_NAME_LENGTH ->
                add(
                    Validation(
                        Validation.Field.NAME,
                        Resource.error(R.string.not_valid_name)
                    )
                )

            else -> add(Validation(Validation.Field.NAME, Resource.success()))
        }
        when {
            email.isNullOrEmpty() -> add(
                Validation(
                    Validation.Field.EMAIL,
                    Resource.error(R.string.email_cant_empty)
                )
            )
            EMAIL_ADDRESS.matcher(email).matches().not() ->
                add(
                    Validation(
                        Validation.Field.EMAIL,
                        Resource.error(R.string.not_valid_email)
                    )
                )
            else -> add(Validation(Validation.Field.EMAIL, Resource.success()))
        }
        when {
            password.isNullOrEmpty() -> add(
                Validation(
                    Validation.Field.PASSWORD,
                    Resource.error(R.string.password_cant_empty)
                )
            )
            password.length < MIN_PASSWORD_LENGTH ->
                add(
                    Validation(
                        Validation.Field.PASSWORD,
                        Resource.error(R.string.not_valid_password)
                    )
                )
            else -> add(Validation(Validation.Field.PASSWORD, Resource.success()))

        }
    }

    data class Validation(val field: Field, val resource: Resource<Int>) {
        enum class Field {
            EMAIL, PASSWORD, NAME
        }
    }
}

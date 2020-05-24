package com.gleam.kiwi.model

import com.gleam.kiwi.view.ValidationResult

data class User(
    val username: String,
    val password: String
) {
    fun validateUsername(): ValidationResult {
        val username = this.username
        return when {
            username.isEmpty() -> ValidationResult.TooShort
            username.length > 255 -> ValidationResult.TooLong(255)
            else -> validate(username)
        }
    }

    fun validatePassword(): ValidationResult {
        val password = this.password
        return when {
            password.length < 8 -> ValidationResult.TooShort
            password.length > 72 -> ValidationResult.TooLong(72)
            else -> validate(password)
        }
    }

    private fun validate(target: String): ValidationResult {
        val validationPattern = "[a-zA-Z0-9]+"
        val regex = Regex(validationPattern)
        return if (regex.matches(target)) ValidationResult.OK else ValidationResult.NotAllowedPattern
    }
}
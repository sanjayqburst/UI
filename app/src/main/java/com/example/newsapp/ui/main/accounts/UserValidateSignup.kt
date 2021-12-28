package com.example.newsapp.ui.main.accounts

// User signup credentials check
class UserValidateSignup(
    private val email: String?,
    private val passWord: String?,
    private val cnfPassword: String?
) {

    fun isPasswordSame(): Boolean {
        return passWord.equals(cnfPassword)
    }

    fun checkIsEmpty(): Boolean {
        return email?.isEmpty() ?: true || passWord?.isEmpty() ?: true || cnfPassword?.isEmpty() ?: true
    }

    fun isUserEmpty(): Boolean {
        return email?.isEmpty() ?: true
    }

    fun isPasswordEmpty(): Boolean {
        return passWord?.isEmpty() ?: true
    }

}
package com.example.newsapp.ui.main.accounts

// User login credentials check
class UserValidateLogin(private val email: String?, private val passWord: String?) {

    fun checkIsEmpty(): Boolean {
        return email?.isEmpty() ?: true || passWord?.isEmpty() ?: true
    }


    fun isUserEmpty(): Boolean {
        return email?.isEmpty() ?: true
    }
}
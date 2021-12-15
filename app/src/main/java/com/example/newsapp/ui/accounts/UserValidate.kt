package com.example.newsapp.ui.accounts

import com.google.firebase.auth.FirebaseAuth

class UserValidate(private val email: String?, private val passWord: String?) {
    private var auth = FirebaseAuth.getInstance()

    fun checkIsEmpty(): Boolean {
        return email?.isEmpty() ?: true || passWord?.isEmpty() ?: true
    }


    fun validateLogin(updateBoolean: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email!!, passWord!!).addOnCompleteListener { task ->
            updateBoolean(task.isSuccessful)
        }
    }


    fun isUserEmpty(): Boolean {
        return email?.isEmpty() ?: true
    }
}
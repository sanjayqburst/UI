package com.example.newsapp.ui.accounts

import com.google.firebase.auth.FirebaseAuth

class UserValidateSignup(
    private val email: String?,
    private val passWord: String?,
    private val cnfPassword: String?
) {
    private val auth = FirebaseAuth.getInstance()

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

    fun isEmailExists(updateBool: (Boolean) -> Unit) {
        auth.fetchSignInMethodsForEmail(email!!).addOnCompleteListener { task ->
            updateBool(task.result.signInMethods?.size == 0)
        }
    }

    fun signUp(updateBool: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email.toString(), passWord.toString())
            .addOnCompleteListener { task ->
                updateBool(task.isSuccessful)
            }
    }
}
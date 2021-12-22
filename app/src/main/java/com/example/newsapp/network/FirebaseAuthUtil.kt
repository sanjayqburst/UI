package com.example.newsapp.network

import com.google.firebase.auth.FirebaseAuth

//      For handling firebase connection
class FirebaseAuthUtil(private var email: String, private var password: String) {
    private var auth = FirebaseAuth.getInstance()

    fun validateLogin(updateBoolean: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            updateBoolean(task.isSuccessful)
        }
    }

    fun isEmailExists(hasEmailExists: (Boolean) -> Unit) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
            hasEmailExists(task.result.signInMethods?.size == 0)
        }
    }

    fun signUp(updateBool: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                updateBool(task.isSuccessful)
            }
    }


}
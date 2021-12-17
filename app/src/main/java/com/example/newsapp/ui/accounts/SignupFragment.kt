package com.example.newsapp.ui.accounts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSignupBinding
import com.example.newsapp.ui.homescreen.HomeScreenActivity

class SignupFragment : Fragment() {
    private lateinit var signUpBinding: FragmentSignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = FragmentSignupBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return signUpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = UserSharedPreference(requireContext())
//        Signup Logic
        signUpBinding.apply {
            signupUsernameValue.addTextChangedListener { signupUsername.error = null }
            signupPasswordValue.addTextChangedListener { signupPassword.error = null }
            signupCnfPasswordValue.addTextChangedListener { signupCnfPassword.error = null }
            signupSignupBtn.setOnClickListener {
                val userEmail = signupUsernameValue.text
                val password = signupPasswordValue.text
                val confirmPassword = signupCnfPasswordValue.text
                val userSignUp = UserValidateSignup(
                    userEmail.toString(),
                    password.toString(),
                    confirmPassword.toString()
                )
                if (userSignUp.checkIsEmpty()) {
                    when {
                        userSignUp.isUserEmpty() -> {
                            signupUsername.error = getString(R.string.username_required)
                        }
                        userSignUp.isPasswordEmpty() -> {
                            signupPassword.error = getString(R.string.password_required)
                        }
                        else -> {
                            signupCnfPassword.error = getString(R.string.cnf_password_required)
                        }
                    }
                } else {
                    if (Patterns.EMAIL_ADDRESS.matcher(userEmail.toString()).matches()) {
                        userSignUp.isEmailExists { task ->
                            if (!task) {
                                signupUsername.error = getString(R.string.email_already_exists)
                            } else {
                                if (userSignUp.isPasswordSame()) {
                                    userSignUp.signUp {
                                        if (it) {
                                            sharedPreference.save("username", userEmail.toString())
                                            moveActivity(
                                                requireContext(),
                                                activity = HomeScreenActivity(),
                                                signupUsernameValue.text.toString()
                                            )
                                        }
                                    }
                                } else {
                                    signupCnfPassword.error =
                                        getString(R.string.cnf_password_not_match)
                                }
                            }
                        }
                    } else {
                        signupUsername.error = getString(R.string.enter_valid_email)
                    }
                }
            }
        }

        signUpBinding.apply {
            signupLoginBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.fragmentContainer,
                    LoginFragment()
                ).commit()
            }
        }
    }

    private fun moveActivity(context: Context, activity: Activity, username: String) {
        val intent = Intent(context, activity::class.java).apply {
            putExtra("username", username)
        }
        startActivity(intent)
        requireActivity().finish()
    }

}
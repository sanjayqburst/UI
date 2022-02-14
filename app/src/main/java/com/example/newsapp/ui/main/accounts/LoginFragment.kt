package com.example.newsapp.ui.main.accounts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentLoginBinding
import com.example.newsapp.network.ConnectivityStatus
import com.example.newsapp.network.FirebaseAuthUtil
import com.example.newsapp.ui.main.homescreen.HomeScreenActivity

class LoginFragment : Fragment() {
    private lateinit var loginFragmentBinding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginFragmentBinding = FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return loginFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = UserSharedPreference(requireContext())
        loginFragmentBinding.apply {
//      Method to clear error on change of text
            loginUsernameValue.addTextChangedListener { loginUsername.error = null }
            loginPasswordValue.addTextChangedListener { loginPassword.error = null }
//      Authenticating user
            loginLoginBtn.setOnClickListener {
                val user = UserValidateLogin(
                    loginUsernameValue.text.toString(),
                    loginPasswordValue.text.toString()
                )
                if (ConnectivityStatus.isNetworkAvailable(requireContext())) {
                    if (!user.checkIsEmpty()) {
                        val firebaseAuthUtil = FirebaseAuthUtil(
                            loginUsernameValue.text.toString(),
                            loginPasswordValue.text.toString()
                        )
                        firebaseAuthUtil.validateLogin {
                            if (it) {
                                sharedPreference.save(
                                    "username",
                                    loginUsernameValue.text.toString()
                                )
                                moveActivity(
                                    requireContext(),
                                    activity = HomeScreenActivity(),
                                    loginUsernameValue.text.toString()
                                )
                            } else {
                                loginPassword.error = getString(R.string.user_authentication)
                            }
                        }
                    } else {
                        if (user.isUserEmpty()) {
                            loginUsername.error = getString(R.string.fill_credentials_username)
                        } else {
                            loginPassword.error = getString(R.string.fill_credentials_password)
                        }
                    }
                } else {
                    Log.d("Msg", "Network Not available")
                }
            }
//      Changing fragment to signup
            loginSignupBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.fragmentContainer,
                    SignupFragment()
                ).commit()
            }
        }
    }

    //    Fun to move to new activity on successful login
    private fun moveActivity(context: Context, activity: Activity, username: String) {
        val intent = Intent(context, activity::class.java).apply {
            putExtra("username", username)
        }
        startActivity(intent)
        requireActivity().finish()
    }
}
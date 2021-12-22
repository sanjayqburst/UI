package com.example.newsapp.ui.accounts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentWelcomeBinding
import com.example.newsapp.ui.homescreen.HomeScreenActivity

// Fragment for loading welcome page
class WelcomeFragment : Fragment() {
    private lateinit var welcomeBinding: FragmentWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        welcomeBinding = FragmentWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreference = UserSharedPreference(requireContext())
        if (sharedPreference.getValue("username") != "username") {
            moveActivity(
                requireContext(),
                HomeScreenActivity(),
                sharedPreference.getValue("username")
            )
        } else {
            welcomeBinding.welcomeLogin.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, LoginFragment())
                    commit()
                }
            }
            welcomeBinding.welcomeSignup.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, SignupFragment())
                    commit()
                }
            }
        }
        return welcomeBinding.root
    }

    //    Fun to move to another activity
    private fun moveActivity(context: Context, activity: Activity, username: String) {
        val intent = Intent(context, activity::class.java).apply {
            putExtra("username", username)
        }
        startActivity(intent)
        requireActivity().finish()
    }
}
package com.example.newsapp.ui.accounts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private lateinit var signUpBinding: FragmentSignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding= FragmentSignupBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return signUpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpBinding.apply {
            signupLoginBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                    LoginFragment()
                ).commit()
            }
        }
    }

}
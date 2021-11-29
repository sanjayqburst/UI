package com.example.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ui.MainActivity
import com.example.ui.R
import com.example.ui.accounts.UserSharedPreference
import com.example.ui.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var sharedPreference:UserSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding= FragmentProfileBinding.inflate(layoutInflater)
        sharedPreference= UserSharedPreference(requireContext())


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding.profileLogout.setOnClickListener {
            sharedPreference.clearPreference()
            val intent= Intent(this@ProfileFragment.context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        val userName=sharedPreference.getValue("username")
        profileBinding.profileUsername.text=userName


        // Inflate the layout for this fragment
        return profileBinding.root
    }
}
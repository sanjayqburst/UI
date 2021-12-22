package com.example.newsapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.ui.accounts.UserSharedPreference

class SettingsFragment : Fragment() {
    private lateinit var profileBinding: FragmentSettingsBinding
    private lateinit var sharedPreference: UserSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = FragmentSettingsBinding.inflate(layoutInflater)
        sharedPreference = UserSharedPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding.profileLogout.setOnClickListener {
            sharedPreference.clearPreference()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        val userName = sharedPreference.getValue("username")
        profileBinding.profileUsername.text = userName
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager: ViewPager2 = requireActivity().findViewById(R.id.viewPager)
        profileBinding.apply {
            profileNewsBtnText.setOnClickListener {
                viewPager.setCurrentItem(0, true)
            }
            profileFavBtn.setOnClickListener {
                viewPager.setCurrentItem(1, true)
            }
        }
    }
}


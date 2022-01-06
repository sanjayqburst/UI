package com.example.newsapp.ui.main.settings

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.ui.main.MainActivity
import com.example.newsapp.ui.main.accounts.UserSharedPreference
import java.util.*

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

            french.setOnClickListener {
                localeChange("fr")
            }
            english.setOnClickListener {
                localeChange("en")
            }

        }

        val result =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val bitMap = result.data?.extras?.get("data") as Bitmap
                    profileBinding.profileImage.setImageBitmap(bitMap)
                } else {
                    Log.d("Msg", "Error")
                }
            }
        val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        profileBinding.fabButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                result.launch(captureImage)
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CAMERA),
                    100
                )
            }
        }
    }

    private fun localeChange(localeCode: String) {
        val config = resources.configuration
        val locale = Locale(localeCode)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }
        resources.updateConfiguration(config, resources.displayMetrics)
    }


}


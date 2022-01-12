package com.example.newsapp.ui.main.settings

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.ui.main.MainActivity
import com.example.newsapp.ui.main.accounts.UserSharedPreference
import com.google.android.material.card.MaterialCardView

class SettingsFragment : Fragment() {
    private lateinit var profileBinding: FragmentSettingsBinding
    private lateinit var sharedPreference: UserSharedPreference
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var captureImage: Intent
    private lateinit var selectImage: Intent

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

        result =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Log.d(TAG, "${result.resultCode}")
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data?.extras?.get("data") is Bitmap) {
                        val bitMap = result.data?.extras?.get("data") as Bitmap
                        Glide.with(this).load(bitMap).centerCrop()
                            .into(profileBinding.profileImage)
                        Log.d(TAG, "${result.data?.extras?.get("uri")}")
                        Log.d(TAG, "${result.data?.extras?.get("path")}")
                        Log.d(TAG, "${result.data?.extras}")
                    } else {
                        val uri = result.data?.data
                        Glide.with(this).load(uri).centerCrop().into(profileBinding.profileImage)
                    }

                } else {
                    Log.d("Msg", "Error")
                }

            }
        selectImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val viewPager: ViewPager2 = requireActivity().findViewById(R.id.viewPager)
        profileBinding.apply {
            profileNewsBtnText.setOnClickListener {
                viewPager.setCurrentItem(0, true)
            }
            profileFavBtn.setOnClickListener {
                viewPager.setCurrentItem(1, true)
            }
        }





        profileBinding.favButton.setOnClickListener {
            Log.d(TAG, "Clicked fav button")
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "Permissions granted")
                showAlert()
            } else {
                Log.d(TAG, "Permission Denied")
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    100
                )
            }
        }
    }

    companion object {
        private const val TAG = "News App"
    }

    private fun showAlert() {

        val profilePictureAlertView = LayoutInflater.from(requireContext())
            .inflate(R.layout.alert_dialogue_profile_image_change, null)
        val profilePictureAlertBuilder =
            AlertDialog.Builder(requireContext()).setView(profilePictureAlertView).create()
        profilePictureAlertBuilder.show()
        val camera = profilePictureAlertView.findViewById<MaterialCardView>(R.id.profilePhotoCamera)
        camera.setOnClickListener {

            result.launch(captureImage)
            profilePictureAlertBuilder.dismiss()
        }
        val gallery =
            profilePictureAlertView.findViewById<MaterialCardView>(R.id.profilePhotoGallery)
        gallery.setOnClickListener {
            result.launch(selectImage)
            profilePictureAlertBuilder.dismiss()

        }

    }


}


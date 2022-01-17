package com.example.newsapp.ui.main.settings

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.network.FirebaseAuthUtil
import com.example.newsapp.ui.main.MainActivity
import com.example.newsapp.ui.main.accounts.UserSharedPreference
import com.example.newsapp.ui.main.favorites.FavSharedPreference
import com.google.android.material.card.MaterialCardView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

class SettingsFragment : Fragment() {

    private lateinit var profileBinding: FragmentSettingsBinding
    private lateinit var favSharedPreference: FavSharedPreference
    private lateinit var sharedPreference: UserSharedPreference
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var captureImage: Intent
    private lateinit var selectImage: Intent
    private lateinit var userName:String

    companion object {
        private const val TAG = "News App"
        private const val KEY = "ProfileImage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favSharedPreference = FavSharedPreference(requireContext())
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
        userName = sharedPreference.getValue("username")
        if (favSharedPreference.hasFav(KEY)) {
            val imageUri = favSharedPreference.getValue(KEY)
            try {
                Glide.with(this).load(imageUri).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).centerCrop().into(profileBinding.profileImage)
            } catch (e: Exception) {
                Log.d(TAG, "${e.printStackTrace()}")
            }
        }
        profileBinding.profileUsername.text = userName
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        result =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data?.extras?.get("data") is Bitmap) {
                        val bitMap = result.data?.extras?.get("data") as Bitmap
                        Glide.with(this).load(bitMap).centerCrop()
                            .into(profileBinding.profileImage)
                        val bitmapSaved = saveBitmap(bitMap)
                        saveImagePreference(bitmapSaved.toString())
                    } else {
                        val uri = result.data?.data
                        Glide.with(this).load(uri).centerCrop().into(profileBinding.profileImage)
                        saveImagePreference(uri.toString())
                    }
                } else {
                    Log.d(TAG, "Error")
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
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showAlert()
            } else {
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

    private fun saveImagePreference(uri: String) {
        if (favSharedPreference.hasFav(KEY)) {
            favSharedPreference.removeFav(KEY)
        }
        favSharedPreference.saveImageUri(KEY, uri)
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

    private fun saveBitmap(bitmap: Bitmap): Uri? {
        if (File(favSharedPreference.getValue(KEY)).exists()) {
            File(favSharedPreference.getValue(KEY)).delete()
        }
        val wrapper = ContextWrapper(requireActivity().applicationContext)
        var file = wrapper.getDir("DCIM", Context.MODE_PRIVATE)
        file = File(file, "$userName.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: Exception) {
            Log.d(TAG, "${e.printStackTrace()}")
        }
        return Uri.parse(file.path)
    }
}


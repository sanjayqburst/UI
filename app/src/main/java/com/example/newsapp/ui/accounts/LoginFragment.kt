package com.example.newsapp.ui.accounts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.newsapp.ui.homescreen.HomeScreenActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var loginFragmentBinding:FragmentLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginFragmentBinding= FragmentLoginBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return loginFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference= UserSharedPreference(requireContext())
        loginFragmentBinding.apply {
            if (sharedPreference.getValue("username")!="username"){
                moveActivity(requireContext(), HomeScreenActivity(),sharedPreference.getValue("username"))
            }else{
                loginLoginBtn.setOnClickListener {
                    val user= UserValidate(loginUsernameValue.text.toString(),loginPasswordValue.text.toString())
                    if (!user.checkIsEmpty()){
                        if (user.validateLogin()){
                            sharedPreference.save("username",loginUsernameValue.text.toString())
                            moveActivity(requireContext(), activity = HomeScreenActivity(),loginUsernameValue.text.toString())
                        }
                        else{
                            Toast.makeText(requireContext(),getString(R.string.user_authentication), Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireContext(),getString(R.string.fill_credentials), Toast.LENGTH_SHORT).show()
                    }
                }


            }
            loginSignupBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                    SignupFragment()
                ).commit()
            }
        }

    }



    private fun moveActivity(context: Context, activity: Activity, username:String){
        val intent= Intent(context,activity::class.java).apply {
            putExtra("username",username)
        }
        startActivity(intent)
        requireActivity().finish()
    }

}
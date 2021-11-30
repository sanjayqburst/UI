package com.example.newsapp.ui.accounts

class UserValidate(private val userName:String?, private val passWord:String?) {
    fun checkIsEmpty():Boolean{
        return userName?.isEmpty() ?:true || passWord?.isEmpty()?:true
    }
    private val map= mapOf<String,String>("Sanjay" to "password","Vaishnav" to "password1","Irshad" to "password")
    fun validateLogin():Boolean{
        return if (userName in map){
            passWord.equals(map.getValue(userName.toString()))
        }else false
    }
}
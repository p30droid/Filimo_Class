package com.navin.filimo.config

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.navin.filimo.models.User

class AppConfig(activity: Activity) {


    var shared: SharedPreferences
    var editor: Editor

    init {
        shared = activity.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        editor = shared.edit()
    }


    fun setLogin(flag: Boolean) {
        editor.putBoolean("loginState", flag)
        editor.commit()
    }

    fun islogin(): Boolean {
        return shared.getBoolean("loginState", false)
    }


    fun setProfile(user: User) {
        editor.putString("name", user.name)
        editor.putString("id", user.userId)
        editor.putString("email", user.email)
        editor.commit()
    }

    fun getProfile(): User {
        val name: String = shared.getString("name", "")!!
        val id: String = shared.getString("id", "")!!
        val email: String = shared.getString("email", "")!!
        val user = User(userId = id, name = name, email = email, success="")
        return user
    }


}
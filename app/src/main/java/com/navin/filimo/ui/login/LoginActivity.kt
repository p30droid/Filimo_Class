package com.navin.filimo.ui.login

import  android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.navin.filimo.MainActivity
import com.navin.filimo.R
import com.navin.filimo.api.Api
import com.navin.filimo.api.IService
import com.navin.filimo.config.AppConfig
import com.navin.filimo.databinding.ActivityLoginBinding
import com.navin.filimo.models.LoginModel
import com.navin.filimo.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appConfig =  AppConfig(this@LoginActivity)

        if(appConfig.islogin()){
            navigateToMain()
        }


        val iService = Api.retrofit.create(IService::class.java)


        binding.imgLogin.setOnClickListener{

            binding.progressbar.visibility = View.VISIBLE
            val name = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            iService.login(name,password).enqueue(object : Callback<LoginModel> {
                override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {

                    if(response.body()?.login?.get(0)?.success=="1") {
                        Log.e("","")
                        appConfig.setLogin(true)
                        val user = response.body()?.login?.get(0)
                        appConfig.setProfile(user!!)
                        binding.progressbar.visibility = View.GONE

                        navigateToMain()
                    }else {
                        Toast.makeText(applicationContext , "Error in login" , Toast.LENGTH_LONG).show()
                    }


                }

                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                    Log.e("","")
                    binding.progressbar.visibility = View.GONE
                }

            })



        }

        binding.imgSignUp.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun navigateToMain() {
        finish()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}
package com.navin.filimo.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.navin.filimo.R
import com.navin.filimo.api.Api
import com.navin.filimo.api.IService
import com.navin.filimo.databinding.ActivityRegisterBinding
import com.navin.filimo.models.LoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val s = "you are registerd successfully"

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val iService = Api.retrofit.create(IService::class.java)
        binding.imgLogin.setOnClickListener {
            finish()
        }

        binding.progressbar.bringToFront()

        binding.imgSignUp.setOnClickListener {



            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val phone = binding.edtPhone.text.toString()
            val password = binding.edtPassword.text.toString()

            if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()){
                Toast.makeText(applicationContext,"Fill All Fields", Toast.LENGTH_LONG).show()

            }
            else {
                binding.progressbar.visibility = View.VISIBLE
                iService.register(name,email,phone,password).enqueue(object : Callback<LoginModel>{
                    override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                        binding.progressbar.visibility = View.GONE
                        if(response.body()?.login?.get(0)?.success=="1"){
                            finish()
                            Toast.makeText(applicationContext,"you are registered successfully", Toast.LENGTH_LONG).show()
                        }else {
                            Toast.makeText(applicationContext,"Error in register, check your fields", Toast.LENGTH_LONG).show()

                        }

                        Log.e("","")
                    }

                    override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                        Log.e("","")
                        binding.progressbar.visibility = View.GONE
                    }

                })

            }





        }

    }
}
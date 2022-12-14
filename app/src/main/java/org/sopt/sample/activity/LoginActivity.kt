package org.sopt.sample.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.data.remote.RequestSignInDTO
import org.sopt.sample.data.remote.ServicePool
import org.sopt.sample.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    val loginService = ServicePool.authService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signUp()
        login()
    }

    private fun signUp() {
        binding.btnSignup.setOnClickListener() {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener() {
            var id = binding.etId.text.toString()
            var pw = binding.etPassword.text.toString()
            loginService.signIn(
                RequestSignInDTO(
                    id, pw
                )
            ).receive()

        }
    }

    private fun <T> Call<T>.receive() {
        this.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                if (response.isSuccessful) {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.e("LOGIN", t.message + "")
            }
        })
    }
}
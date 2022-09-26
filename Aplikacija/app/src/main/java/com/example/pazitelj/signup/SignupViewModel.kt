package com.example.pazitelj.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pazitelj.models.LoginResponse
import com.example.pazitelj.network.TestApi
import kotlinx.coroutines.*
import java.lang.Exception
import kotlinx.coroutines.Dispatchers.Main

class SignupViewModel : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    var currentUserIsAuthenticated: Boolean = false

    suspend fun authenticate(token: String): String {
            try {
                val result = TestApi.retrofitService.login(token)
                //_loginResponse.postValue(result)
                Log.d("loginauthres", result.toString())
                setResult(result)
                return result.UserId

            } catch (e: Exception) {
                Log.d("loginauthentfail", e.toString())
            }
        return "failed"

    }
    private suspend fun setResult(result: LoginResponse){
        withContext(Main){
            _loginResponse.value = result
        }
    }

}

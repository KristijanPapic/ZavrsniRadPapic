package com.example.pazitelj.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazitelj.models.User
import com.example.pazitelj.network.TestApi
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel : ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    suspend fun getUser(id: String) {
        Log.d("loginin",id)
        try {
            _user.value = TestApi.retrofitService.getUser(id)
        } catch (e: Exception) {
            Log.d("loginuserfail", e.toString())
        }
    }

    fun deleteUser(id: String){
        viewModelScope.launch {
            try {
                TestApi.retrofitService.deleteUser(id)
                Log.d("deleteuser", "good")
            } catch (e: Exception) {
                Log.d("deletuserfail", e.message!!)
            }
        }
    }
}
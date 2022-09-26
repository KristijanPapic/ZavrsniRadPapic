package com.example.pazitelj.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazitelj.models.AppliedUser
import com.example.pazitelj.network.TestApi
import kotlinx.coroutines.launch

class AppliedUsersViewModel : ViewModel() {

    public val users = MutableLiveData<MutableList<AppliedUser>>()


    suspend fun getAppliedUsers(adId: String,type: Int){
        try{
            users.value = TestApi.retrofitService.GetAppliedUsers(adId,type).toMutableList()
            Log.d("appuserok","ok")
        }
        catch (e: Exception){
            Log.d("appuserfail", e.message!!)
        }
    }

    fun putAppliedUser(adId: String,userId: String,status: String){
        viewModelScope.launch {
            try {
                TestApi.retrofitService.PutAppliedUser(userId,adId,status)
            }
            catch (e:Exception){
                Log.d("choosefail", e.message!!)
            }
        }
    }
}
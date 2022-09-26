package com.example.pazitelj.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazitelj.models.Ad
import com.example.pazitelj.models.AdInput
import com.example.pazitelj.models.Pet
import com.example.pazitelj.models.User
import com.example.pazitelj.network.TestApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CurrentUserViewModel: ViewModel() {
    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user



    fun setBio(newBio: String){
        _user.value!!.Bio = newBio
    }
    fun setPhone(newPhone: String){
        _user.value!!.PhoneNumber = newPhone
    }

    suspend fun getUser(id: String) {
    Log.d("loginin",id)
        try {
            val result = TestApi.retrofitService.getUser(id)
            //Log.d("loginuserres", _user.value!!.Username)
            setResult(result)
        } catch (e: Exception) {
            Log.d("loginuserfail", e.toString())
        }
    }

    private suspend fun setResult(result: User) {
        withContext(Dispatchers.Main) {
            _user.value = result
            Log.d("loginuserresult",user.value.toString())
        }


    }
     fun updateUser(){
        viewModelScope.launch {
            try {
                TestApi.retrofitService.updateUser(_user.value!!)
                Log.d("updatesucces","ok")
                _user.value!!.Pets.forEach {
                    if(it.Id == null){
                        postPet(it)
                    }
                }
            }catch (e: Exception) {
                Log.d("updatefail",e.message!!)
            }
            getUser(_user.value!!.Id)
        }
    }

     suspend fun postPet(pet: Pet) {
         try {
             TestApi.retrofitService.addPet(pet)
             Log.d("added pet", "added")
         } catch (e: Exception) {
             Log.d("petfail", "fail")
         }
     }

         fun addPet(pet: Pet) {
             _user.value!!.Pets.add(pet)
         }

         suspend fun deletePet(position: Int) {
             try {
                 if (_user.value!!.Pets[position].Id != null) {
                     TestApi.retrofitService.deletePet(user.value!!.Pets[position].Id!!)
                 }
                 _user.value!!.Pets.removeAt(position)
                 Log.d("delete pet", "added")
             } catch (e: Exception) {
                 Log.d("deletepetfail", e.message.toString())
             }

         }

         fun clearTempPets() {
             _user.value!!.Pets.removeIf { x: Pet -> x.Id == null }
         }

         suspend fun postAd(ad: AdInput) {
             try {
                 TestApi.retrofitService.PostAd(ad)
                 Log.d("post ad", "posted")
             } catch (e: Exception) {
                 Log.d("adfail", "fail")
             }
         }

    suspend fun putAd(ad: Ad) {
        try {
            TestApi.retrofitService.PutAd(ad)
            Log.d("post ad", "updated")
        } catch (e: Exception) {
            Log.d("upadfail", "fail")
        }
    }
     }
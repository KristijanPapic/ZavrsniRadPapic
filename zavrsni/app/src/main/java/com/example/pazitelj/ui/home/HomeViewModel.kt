package com.example.pazitelj.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazitelj.models.Ad
import com.example.pazitelj.models.AdFilter
import com.example.pazitelj.models.AppliedUserInput
import com.example.pazitelj.network.TestApi
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    val filter = MutableLiveData<Boolean>().apply {
        value = true
    }

    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */

    private val _ads = MutableLiveData<List<Ad>>()

    val ads: LiveData<List<Ad>> = _ads



    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    fun getModelAds(filter: AdFilter) {
        viewModelScope.launch {
            try {
                _ads.value = TestApi.retrofitService.GetAds(Type = filter.Type, CurrentUser = filter.CurrentUser, UserId = filter.UserId, ShowAppliedCount = filter.ShowAppliedCount, ShowStatus = filter.ShowStatus, PetType = filter.petType, JobType = filter.jobType, Rating = filter.rating)
                Log.d("adsstatus",ads.value.toString())
                reset()
            }catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.d("adsfail",e.message!!)
            }
        }
        }

    suspend fun getAd(id: String,currentUser: String) : Ad? {
        var ad : Ad? = null
            try {
                ad = TestApi.retrofitService.GetAd(id,currentUser)
                Log.d("fulladsstatus",ad.toString())
            }catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.d("fulladsfail",e.message!!)
            }

        return ad
    }
    fun deleteAd(id: String){
        viewModelScope.launch {
            try {
                TestApi.retrofitService.DeleteAd(id)
                Log.d("deletead", "good")
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.d("deleteadsfail", e.message!!)
            }
        }
    }

    fun deleteUser(id: String){
        viewModelScope.launch {
            try {
                TestApi.retrofitService.deleteUser(id)
                Log.d("deleteuser", "good")
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.d("deletuserfail", e.message!!)
            }
        }
    }

    fun postAppliedUser(appliedUser: AppliedUserInput) {
        viewModelScope.launch {
            try {
                TestApi.retrofitService.PostAppliedUser(appliedUser)
                Log.d("postausucc", "good")
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.d("postaubad", e.message!!)
            }
        }
    }
    fun deleteAppliedUser(userId: String,adId: String) {
        viewModelScope.launch {
            try {
                TestApi.retrofitService.DeleteAppliedUser(userId,adId)
                Log.d("postausucc", "good")
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.d("postaubad", e.message!!)
            }
        }
    }

    fun reset(){
        val list = _ads.value
        if(!list.isNullOrEmpty()){
            _ads.value = emptyList()
            _ads.value = list!!
        }

    }

    fun getAdAt(index: Int): Ad{
        return _ads.value!![index]
    }
    }

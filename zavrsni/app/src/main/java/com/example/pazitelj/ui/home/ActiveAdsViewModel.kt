package com.example.pazitelj.ui.home

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazitelj.models.Ad
import com.example.pazitelj.models.AdFilter
import com.example.pazitelj.models.CarerRating
import com.example.pazitelj.models.OwnerRating
import com.example.pazitelj.network.TestApi
import kotlinx.coroutines.launch

class ActiveAdsViewModel : ViewModel() {

    private val _owAds = MutableLiveData<MutableList<Ad>>()
    val owAds: LiveData<MutableList<Ad>> = _owAds

    private val _appAds = MutableLiveData<MutableList<Ad>>()
    val appAds: LiveData<MutableList<Ad>> = _appAds


    init {
        //getOwAds(AdFilter(Type = ))
    }
    fun getOwAds(filter: AdFilter) {
        viewModelScope.launch {
            try {
                _owAds.value = TestApi.retrofitService.GetAds(Type = filter.Type, CurrentUser = filter.CurrentUser, UserId = filter.UserId, ShowAppliedCount = filter.ShowAppliedCount, ShowStatus = filter.ShowStatus, PetType = "", JobType = "", Rating = 0).toMutableList()
                Log.d("owads",_owAds.value.toString())
                reset()
            }catch (e: Exception) {
                Log.d("owadsfail",e.message!!)
            }
        }
    }

    fun getAppAds(filter: AdFilter) {
        viewModelScope.launch {
            try {
                _appAds.value = TestApi.retrofitService.GetAds(Type = filter.Type, CurrentUser = filter.CurrentUser, UserId = filter.UserId, ShowAppliedCount = filter.ShowAppliedCount, ShowStatus = filter.ShowStatus,PetType = "", JobType = "", Rating = 0).toMutableList()
                reset()
            }catch (e: Exception) {
                Log.d("owadsfail",e.message!!)
            }
        }
    }

    fun adCarerRating(rating: CarerRating) {
        viewModelScope.launch {
            try {
                TestApi.retrofitService.addCarerReview(rating)
            }catch (e: Exception) {
                Log.d("carratfail",e.message!!)
            }
        }
    }
    fun adOwnerRating(rating: OwnerRating) {
        viewModelScope.launch {
            try {
                TestApi.retrofitService.addOwnerReview(rating)
            }catch (e: Exception) {
                Log.d("owratfail",e.message!!)
            }
        }
    }

    fun reset(){
        val applist = _appAds.value
        if(!applist.isNullOrEmpty()) {
            //_appAds.value = emptyList()
            //_appAds.value = applist
        }

            val owlist = _owAds.value
            if(!owlist.isNullOrEmpty()){
                //_owAds.value = emptyList()
                //_owAds.value = owlist
        }

    }

    fun getAdAt(index: Int): Ad {
        return _appAds.value!![index]
    }

    fun appliedUsers(count: Int) : String{
        if (count == 0){
            return "No applied users"
        }
        else{
            return "$count applied users"
        }

    }
    fun deleteLocalOwAd(position: Int){
        _owAds.value?.removeAt(position)

    }

    fun deleteLocalAppAd(position: Int){
        _appAds.value?.removeAt(position)

    }
}
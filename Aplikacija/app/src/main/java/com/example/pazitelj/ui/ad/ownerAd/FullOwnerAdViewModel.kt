package com.example.pazitelj.ui.ad.ownerAd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pazitelj.models.Ad

class FullOwnerAdViewModel : ViewModel() {
    private val _ad = MutableLiveData<Ad>()
    val ad: LiveData<Ad> = _ad

    fun setAd(ad: Ad){
        _ad.value = ad
    }

    fun updateAd(desc: String, job: String,petId: String){
        _ad.value!!.Description = desc
        _ad.value!!.Job = job
        _ad.value!!.PetId = petId
    }
}
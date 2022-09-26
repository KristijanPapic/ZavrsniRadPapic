package com.example.pazitelj.FirstTime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pazitelj.models.Pet

class FirstTimeModel : ViewModel() {
    private val _phone = MutableLiveData<String>()

    private val _bio = MutableLiveData<String>()

    private val _pets = MutableLiveData<MutableList<Pet>>()

    val pets: MutableLiveData<MutableList<Pet>> = _pets


    fun setPhone(phoneNmbr: String){
        _phone.value = phoneNmbr
    }

    fun setBio(bio: String){
        _bio.value = bio
    }

    fun addPet(pet: Pet){
        _pets.value!!.add(pet)
    }
}
package com.example.pazitelj.models

data class User (
    var Id: String,
    var Username: String,
    var Email: String,
    var PhoneNumber: String,
    var Role: Boolean,
    var PictureURL: String,
    var CarerOverallRating: Int,
    var PetHandlingRating: Int,
    var CarerCourtesyRating: Int,
    var OwnerOverallRating: Int,
    var PetRating: Int,
    var OwnerCourtesyRating: Int,
    var Bio: String,
    var Pets: MutableList<Pet>
        )

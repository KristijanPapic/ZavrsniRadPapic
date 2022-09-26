package com.example.pazitelj.models

import java.util.*

data class Ad(var Id: String = "",var Type: Int = 0,var Job: String = "",var Description: String = "",var PetId: String = "",var PetName: String = "",var PetType: String = "other",var UserId: String = "",var UserPhone: String = "",var UserEmail: String = "",var UserName: String = "",var UserRating: Int = 0,var PictureURL: String = "",var Active: Int = 0,var AppliedUserCount: Int = 0, var Status: String = "",var CurrentUserApplied: Boolean = false, var AppliedUser: SimpleAppliedUser? = null)

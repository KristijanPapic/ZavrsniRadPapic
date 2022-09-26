package com.example.pazitelj.ui.home

interface IAdListAdapter {
    fun openAd(id: String,type: Int)

    fun deleteAd(id: String,position: Int)

    fun cancelAd(adId: String,position: Int)

    fun openUser(userId: String)

    fun concludeAd(AdId: String,UserId: String,Type: Int,IsPoster: Boolean,Position: Int)

}

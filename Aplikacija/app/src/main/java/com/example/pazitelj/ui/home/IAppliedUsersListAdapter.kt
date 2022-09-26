package com.example.pazitelj.ui.home

interface IAppliedUsersListAdapter {
    fun chooseUser(userId: String)

    fun removeUser(userId: String,position: Int)
}
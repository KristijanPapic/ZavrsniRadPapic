package com.example.pazitelj.models

data class AdFilter(var Type: Int,var CurrentUser: String = "",var UserId: String = "",var ShowAppliedCount: Boolean = false,var ShowStatus: Boolean = false,var petType: String = "",var jobType: String = "",var rating: Int = 0) {

}

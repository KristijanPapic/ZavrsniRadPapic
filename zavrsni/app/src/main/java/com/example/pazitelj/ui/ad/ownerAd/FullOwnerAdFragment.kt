package com.example.pazitelj.ui.ad.ownerAd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentAvailableAdsBinding
import com.example.pazitelj.databinding.FragmentFullOwnerAdBinding
import com.example.pazitelj.models.Ad
import com.example.pazitelj.models.AppliedUserInput
import com.example.pazitelj.ui.CurrentUserViewModel
import com.example.pazitelj.ui.ad.carerAd.FullCarerAdFragmentDirections
import com.example.pazitelj.ui.ad.carerAd.FullCarerAdViewModel
import com.example.pazitelj.ui.home.HomeFragmentDirections
import com.example.pazitelj.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FullOwnerAdFragment : Fragment() {
    private var _binding: FragmentFullOwnerAdBinding? = null
    private val binding get() = _binding!!
    private val currentUserViewModel: CurrentUserViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private  val adModel: FullOwnerAdViewModel by activityViewModels()
    var ad: Ad? = Ad()
    var isApplied: Boolean = false
    var isSelected: Boolean = false
    var isOwner: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullOwnerAdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var adId: String? = null
        arguments?.let {
            adId = it.getString("id")
        }
        CoroutineScope(Dispatchers.Main).launch {
            ad = adId?.let { homeViewModel.getAd(adId!!,currentUserViewModel.user.value!!.Id) }
            adModel.setAd(ad!!)
            binding.ad = ad
            isApplied = ad!!.CurrentUserApplied
            if(ad!!.AppliedUser != null){
                if(currentUserViewModel.user.value!!.Id == (ad!!.AppliedUser!!.UserId)){
                    isSelected = true
                }
                }
            if(ad!!.UserId.equals(currentUserViewModel.user.value!!.Id)){
                isOwner = true
            }
            setStatus()
        }
        binding.applyBtn.setOnClickListener {
            applyToAd()
        }
        binding.unapplyBtn.setOnClickListener {
            unapplyToAd()
        }
        binding.profilePhoto.setOnClickListener {
            openUser(ad!!.UserId)
        }

        binding.editAdBtn.setOnClickListener {
            val editAction = FullOwnerAdFragmentDirections.actionFullOwnerAdFragmentToEditOwnerAdFragment()
            findNavController().navigate(editAction)
        }

        if(currentUserViewModel.user.value!!.Role &&  ad!!.UserId != currentUserViewModel.user.value!!.Id){
            binding.removeAdBtn.visibility = View.VISIBLE
            binding.removeAdBtn.setOnClickListener {
                removeAd()
            }
        }

        return root
    }

    private fun unapplyToAd() {
        homeViewModel.deleteAppliedUser(currentUserViewModel.user.value!!.Id,ad!!.Id)
        binding.statusText.text = getString(R.string.unaply)
        isApplied = false
        setStatus()
    }

    private fun applyToAd() {
        homeViewModel.postAppliedUser(AppliedUserInput(ad!!.Id,currentUserViewModel.user.value!!.Id))
        binding.statusText.text = getString(R.string.applied)
        isApplied = true
        setStatus()
    }

    private fun setStatus(){
        if(isSelected || isOwner){
            binding.applyBtn.visibility = View.GONE
            binding.unapplyBtn.visibility = View.GONE
            if(isOwner){
                binding.editAdBtn.visibility = View.VISIBLE
            }
        }
        else if (isApplied){
            binding.applyBtn.visibility = View.GONE
            binding.unapplyBtn.visibility = View.VISIBLE

        }
        else{
            binding.applyBtn.visibility = View.VISIBLE
            binding.unapplyBtn.visibility = View.GONE
        }
    }

    fun openUser(userId: String) {
        if (userId == currentUserViewModel.user.value!!.Id) {
            val action = FullOwnerAdFragmentDirections.actionFullOwnerAdFragmentToNavigationProfile(
                userId = "",
                isCurrent = true
            )
            findNavController().navigate(action)
        } else {
            val action = FullOwnerAdFragmentDirections.actionFullOwnerAdFragmentToNavigationProfile(
                userId = userId,
                isCurrent = false
            )
            findNavController().navigate(action)
        }
    }
    private fun removeAd() {
        CoroutineScope(Dispatchers.Main).launch {
            homeViewModel.deleteAd(ad!!.Id)
            val action = FullOwnerAdFragmentDirections.actionFullOwnerAdFragmentToFragmentHome()
            findNavController().navigate(action)
        }
        }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
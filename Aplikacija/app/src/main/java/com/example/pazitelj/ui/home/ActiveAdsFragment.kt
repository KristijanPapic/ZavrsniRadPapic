package com.example.pazitelj.ui.home

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentActiveAdsBinding
import com.example.pazitelj.models.AdFilter
import com.example.pazitelj.ui.CurrentUserViewModel
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pazitelj.databinding.AddPetPopupBinding
import com.example.pazitelj.databinding.AddRatingPopupBinding
import com.example.pazitelj.models.CarerRating
import com.example.pazitelj.models.OwnerRating
import com.example.pazitelj.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

class ActiveAdsFragment : Fragment(),IAdListAdapter,IOwnerAdsListAdapter {
    private var _binding: FragmentActiveAdsBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val activeViewModel: ActiveAdsViewModel by activityViewModels()
    private val currentUser: CurrentUserViewModel by activityViewModels()
    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog

    var owType = 0
    var appType = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveAdsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val userObserver = Observer<User> {getAds(owType,appType)}
        currentUser.user.observe(viewLifecycleOwner,userObserver)

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(homeViewModel.filter.value!!){
            owType = 0
            appType = 1
        }
        else{
            owType = 1
            appType = 0
        }
        getAds(owType,appType)

        binding.lifecycleOwner = super.getParentFragment()
        binding.activeModel = activeViewModel
        if(homeViewModel.filter.value!!){
            binding.yourAdsList.adapter = CarerAdActiveOwAdapter(this, this)
            binding.appliedToAdsList.adapter = OwnerAdActiveAppAdapter(this)
        }
        else{
            binding.yourAdsList.adapter = OwnerAdActiveOwAdapter(this, this)
            binding.appliedToAdsList.adapter = CarerAdActiveAppAdapter(this)
        }


        val filterObserver = Observer<Boolean> { newFilter -> changeAdapter(newFilter) }
        homeViewModel.filter.observe(viewLifecycleOwner, filterObserver)

        binding.checkOw.isChecked = true
        binding.checkApp.isChecked = true

        binding.checkOw.setOnClickListener {
            owChecked()
        }
        binding.checkApp.setOnClickListener {
            appChecked()
        }

        binding.refreshBtn.setOnClickListener {
            getAds(owType,appType)
        }
    }

    fun changeAdapter(filter: Boolean) {
        if (filter) {
            owType = 0
            appType = 1
            getAds(owType,appType)
            binding.yourAdsList.adapter = CarerAdActiveOwAdapter(this, this)
            binding.appliedToAdsList.adapter = OwnerAdActiveAppAdapter(this)
        } else {
            owType = 1
            appType = 0
            getAds(owType,appType)
            binding.yourAdsList.adapter = OwnerAdActiveOwAdapter(this, this)
            binding.appliedToAdsList.adapter = CarerAdActiveAppAdapter(this)
        }

    }

    fun getAds(owType: Int,appType: Int){
        if(currentUser.user.value != null){
            CoroutineScope(Dispatchers.Main).launch {
                activeViewModel.getOwAds(
                    AdFilter(
                        Type = owType,
                        UserId = currentUser.user.value!!.Id,
                        ShowAppliedCount = true
                    )
                )
                activeViewModel.getAppAds(
                    AdFilter(
                        Type = appType,
                        CurrentUser = currentUser.user.value!!.Id,
                        ShowStatus = true
                    )
                )
            }
        }

    }

    override fun openAd(adId: String, type: Int) {
        if (type == 1) {
            val action = HomeFragmentDirections.actionFragmentHomeToFullOwnerAdFragment(id = adId)
            findNavController().navigate(action)
        } else {
            val action = HomeFragmentDirections.actionFragmentHomeToFullCarerAdFragment(id = adId)
            findNavController().navigate(action)
        }
    }

    override fun deleteAd(id: String, position: Int) {
        homeViewModel.deleteAd(id)
        if (activeViewModel.owAds.value!!.size >= position) {
            activeViewModel.deleteLocalOwAd(position)
            binding.yourAdsList.adapter!!.notifyItemRemoved(position)
        } else {
            activeViewModel.deleteLocalOwAd(position - 1)
            binding.yourAdsList.adapter!!.notifyItemRemoved(position - 1)
        }
    }

    override fun cancelAd(adId: String, position: Int) {
        homeViewModel.deleteAppliedUser(currentUser.user.value!!.Id, adId)
        if (activeViewModel.appAds.value!!.size >= position) {
            activeViewModel.deleteLocalAppAd(position)
            binding.appliedToAdsList.adapter!!.notifyItemRemoved(position)
        } else {
            activeViewModel.deleteLocalAppAd(position - 1)
            binding.appliedToAdsList.adapter!!.notifyItemRemoved(position - 1)
        }

    }

    override fun openUser(userId: String) {
        TODO("Not yet implemented")
    }


    override fun concludeAd(
        AdId: String,
        UserId: String,
        Type: Int,
        IsPoster: Boolean,
        Position: Int
    ) {
        createNewRatingDialog(AdId, UserId, Type, IsPoster, Position)
    }

    override fun openAppliedUsers(adId: String, type: Int) {
        val action = HomeFragmentDirections.actionFragmentHomeToAppliedUsersFragment(
            adId = adId,
            type = type
        )
        findNavController().navigate(action)
    }


    fun createNewRatingDialog(
        AdId: String,
        UserId: String,
        Type: Int,
        IsPoster: Boolean,
        Position: Int
    ) {
        dialogBuilder = AlertDialog.Builder(requireContext())
        val binding: AddRatingPopupBinding =
            AddRatingPopupBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBuilder.setView(binding.root)
        dialog = dialogBuilder.create()
        dialog.show()

        binding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.submitButton.setOnClickListener {
            submitRating(
                AdId,
                UserId,
                Type,
                binding.overall.rating.toInt(),
                binding.pet.rating.toInt(),
                binding.courtesy.rating.toInt(),
                IsPoster,
                Position
            )
            dialog.dismiss()
        }
    }

    private fun submitRating(
        AdId: String,
        UserId: String,
        Type: Int,
        OverallRating: Int,
        PetRating: Int,
        CourtesyRating: Int,
        IsPoster: Boolean,
        Position: Int
    ) {
        if (homeViewModel.filter.value!!) {
            activeViewModel.adOwnerRating(
                OwnerRating(
                    Id = AdId,
                    OwnerId = UserId,
                    OverallRating = OverallRating,
                    PetRating = PetRating,
                    CourtesyRating = CourtesyRating,
                    IsPoster = IsPoster
                )
            )
            if (Type == 1) {
                activeViewModel.appAds.value!!.removeAt(Position)
                binding.appliedToAdsList.adapter!!.notifyItemRemoved(Position)
                Log.d("rate1","bb")
            }
            else {
                activeViewModel.owAds.value!!.removeAt(Position)
                binding.yourAdsList.adapter!!.notifyItemRemoved(Position)
                Log.d("rate2","bb")
            }
        } else {
            activeViewModel.adCarerRating(
                CarerRating(
                    Id = AdId,
                    CarerId = UserId,
                    OverallRating = OverallRating,
                    PetHandlingRating = PetRating,
                    CourtesyRating = CourtesyRating,
                    IsPoster = IsPoster
                )
            )
            if (Type == 1) {

                activeViewModel.owAds.value!!.removeAt(Position)
                binding.yourAdsList.adapter!!.notifyItemRemoved(Position)
                Log.d("rate3","bb")

            } else {
                    activeViewModel.appAds.value!!.removeAt(Position)
                    binding.appliedToAdsList.adapter!!.notifyItemRemoved(Position)
                Log.d("rate4","bb")
            }


        }



    }
    private fun owChecked(){
        if(binding.checkOw.isChecked){
            binding.yourAdsList.visibility = View.VISIBLE
            binding.owLabel.visibility = View.VISIBLE
        }
        else{
            if(binding.checkApp.isChecked){
                binding.yourAdsList.visibility = View.GONE
                binding.owLabel.visibility = View.GONE
            }
            else{
                binding.checkOw.isChecked = true
            }
        }

    }

    private fun appChecked(){
        if(binding.checkApp.isChecked){
            binding.appliedToAdsList.visibility = View.VISIBLE
            binding.appLabel.visibility = View.VISIBLE
        }
        else{
            if(binding.checkOw.isChecked){
                binding.appliedToAdsList.visibility = View.GONE
                binding.appLabel.visibility = View.GONE
            }
            else{
                binding.checkApp.isChecked = true
            }
        }

    }

}
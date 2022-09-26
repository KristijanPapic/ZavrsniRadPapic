package com.example.pazitelj.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pazitelj.R
import com.example.pazitelj.databinding.CarerFilterPopupBinding
import com.example.pazitelj.databinding.FragmentAvailableAdsBinding
import com.example.pazitelj.databinding.OwnerFilterPopupBinding
import com.example.pazitelj.models.AdFilter
import com.example.pazitelj.models.User
import com.example.pazitelj.ui.CurrentUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AvailableAdsFragment : Fragment(),IAdListAdapter {

    private var _binding: FragmentAvailableAdsBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val currentUser: CurrentUserViewModel by activityViewModels()
    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private var petTypeFilter: String = ""
    private var jobTypeFilter: String = ""
    private var ratingFilter: Int = 0
    private val jobList = arrayOf("All","Pet Walking","Pet Feeding at Owner home","Pet Feeding and care at Carer home")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAvailableAdsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        Log.d("created","crated")

        val userObserver = Observer<User> {getAds()}
        currentUser.user.observe(viewLifecycleOwner,userObserver)

        return root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser.user.value?.let {
            getAds()
        }
        val userObserver = Observer<User>{user -> getAds()}
        currentUser.user.observe(viewLifecycleOwner,userObserver)

        binding.petRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.petRecyclerView.adapter = PetAdAdapter(this)

        val filterObserver = Observer<Boolean> { newFilter -> changeAdapter(newFilter)  }
        homeViewModel.filter.observe(viewLifecycleOwner, filterObserver)

        binding.refreshBtn.setOnClickListener {
            changeAdapter(homeViewModel.filter.value!!)
        }
        binding.filterBtn.setOnClickListener {
            if (homeViewModel.filter.value!!){
                createNewCarerFilterDialog()
            }
            else{
                createNewOwnerFilterDialog()
            }
        }
        //homeViewModel.filter.observe(viewLifecycleOwner, Observer { changeAdapter() })
    }

    override fun onResume() {
        super.onResume()
        Log.d("resume","resume")
        Log.d("fragads",binding.homeModel.toString())
        Log.d("adapterdata",binding.petRecyclerView.adapter.toString())
        Log.d("adapter",binding.petRecyclerView.adapter.toString())
        homeViewModel.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        Log.d("des","des")
    }

    fun getAds()
    {
        if(currentUser.user.value != null){
            CoroutineScope(Dispatchers.Main).launch {
                homeViewModel.getModelAds(AdFilter(1,currentUser.user.value!!.Id))
                binding.lifecycleOwner = super.getParentFragment()
                binding.homeModel = homeViewModel
            }
        }

    }
    fun changeAdapter(filter: Boolean){
        if(currentUser.user.value != null){
            if(filter){
                homeViewModel.getModelAds(AdFilter(1,currentUser.user.value!!.Id, petType = petTypeFilter, jobType = jobTypeFilter, rating = ratingFilter))
                binding.petRecyclerView.adapter = PetAdAdapter(this)
                //homeViewModel.reset()
            }
            else{
                homeViewModel.getModelAds(AdFilter(0,currentUser.user.value!!.Id, jobType = jobTypeFilter, rating = ratingFilter))
                binding.petRecyclerView.adapter = CarerAdAdapter(this)
            }
            }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.more -> {
                Log.d("notify","notify")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun createNewCarerFilterDialog(){
        dialogBuilder = AlertDialog.Builder(requireContext())
        val binding: CarerFilterPopupBinding = CarerFilterPopupBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBuilder.setView(binding.root)
        if(jobTypeFilter == ""){
            binding.jobRadioGroup.check(R.id.joball_radio)
        }
        else{
            when(jobTypeFilter){
                "Pet Walking" -> binding.jobRadioGroup.check(R.id.pw_radio)
                "Pet Feeding at Owner home" -> binding.jobRadioGroup.check(R.id.pwaoh_radio)
                "Pet Feeding and care at Carer home" -> binding.jobRadioGroup.check(R.id.pwach_radio)
            }
        }
        if(petTypeFilter == ""){
            binding.radioGroup.check(R.id.all_radio)
        }
        else{
            when(petTypeFilter){
                "Dog" -> binding.radioGroup.check(R.id.dog_radio)
                "Cat" -> binding.radioGroup.check(R.id.cat_radio)
                else -> {
                    binding.radioGroup.check(R.id.other_radio)
                    binding.otherInput.setText(petTypeFilter)
                }
            }
        }
        binding.radioGroup.setOnCheckedChangeListener{group, checkedId ->
            if (checkedId == R.id.other_radio){
                binding.otherInputLayout.visibility = View.VISIBLE
            }
            else if (checkedId != R.id.other_radio && binding.otherInput.visibility == View.VISIBLE){
                binding.otherInputLayout.visibility = View.GONE
            }
        }
        dialog = dialogBuilder.create()
        dialog.show()

        binding.resetButton.setOnClickListener{
            petTypeFilter = ""
            jobTypeFilter = ""
            ratingFilter = 0
            changeAdapter(homeViewModel.filter.value!!)
            dialog.dismiss()
        }
        binding.filterButton.setOnClickListener {
            var checkedType = ""
            when(binding.radioGroup.checkedRadioButtonId){
                R.id.all_radio -> checkedType = ""
                R.id.dog_radio -> checkedType = "Dog"
                R.id.cat_radio -> checkedType = "Cat"
                R.id.other_radio -> checkedType = binding.otherInput.text.toString()
            }
            petTypeFilter = checkedType
            var jobCheckedType = ""
            when(binding.jobRadioGroup.checkedRadioButtonId){
                R.id.joball_radio -> jobCheckedType = ""
                R.id.pw_radio -> jobCheckedType = "Pet Walking"
                R.id.pwaoh_radio -> jobCheckedType = "Pet Feeding at Owner home"
                R.id.pwach_radio -> jobCheckedType = "Pet Feeding and care at Carer home"
            }
            jobTypeFilter = jobCheckedType
            ratingFilter = binding.ratingBar.rating.toInt()
            changeAdapter(homeViewModel.filter.value!!)
            val jobListAdapter = ArrayAdapter(requireContext(), R.layout.list_item, jobList)
            dialog.dismiss()
        }
    }

    fun createNewOwnerFilterDialog(){
        dialogBuilder = AlertDialog.Builder(requireContext())
        val binding: OwnerFilterPopupBinding = OwnerFilterPopupBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBuilder.setView(binding.root)
        if(jobTypeFilter == ""){
            binding.jobRadioGroup.check(R.id.joball_radio)
        }
        else{
            when(jobTypeFilter){
                "Pet Walking" -> binding.jobRadioGroup.check(R.id.pw_radio)
                "Pet Feeding at Owner home" -> binding.jobRadioGroup.check(R.id.pwaoh_radio)
                "Pet Feeding and care at Carer home" -> binding.jobRadioGroup.check(R.id.pwach_radio)
            }
        }
        dialog = dialogBuilder.create()
        dialog.show()

        binding.resetButton.setOnClickListener{
            petTypeFilter = ""
            jobTypeFilter = ""
            ratingFilter = 0
            changeAdapter(homeViewModel.filter.value!!)
            dialog.dismiss()
        }
        binding.filterButton.setOnClickListener {
            var jobCheckedType = ""
            when(binding.jobRadioGroup.checkedRadioButtonId){
                R.id.joball_radio -> jobCheckedType = ""
                R.id.pw_radio -> jobCheckedType = "Pet Walking"
                R.id.pwaoh_radio -> jobCheckedType = "Pet Feeding at Owner home"
                R.id.pwach_radio -> jobCheckedType = "Pet Feeding and care at Carer home"
            }
            jobTypeFilter = jobCheckedType
            ratingFilter = binding.ratingBar.rating.toInt()
            changeAdapter(homeViewModel.filter.value!!)
            val jobListAdapter = ArrayAdapter(requireContext(), R.layout.list_item, jobList)
            dialog.dismiss()
        }
    }

    override fun openAd(adId: String, type: Int) {
        if(type == 1){
            val action = HomeFragmentDirections.actionFragmentHomeToFullOwnerAdFragment(id = adId)
            findNavController().navigate(action)
        }
        else{
            val action = HomeFragmentDirections.actionFragmentHomeToFullCarerAdFragment(id = adId)
            findNavController().navigate(action)
        }
    }

    override fun deleteAd(id: String,position: Int) {
        TODO("Not yet implemented")
    }

    override fun cancelAd(adId: String, position: Int) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }


}
package com.example.pazitelj.ui.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentCarerPostBinding
import com.example.pazitelj.databinding.FragmentEditCarerAdBinding
import com.example.pazitelj.databinding.FragmentEditOwnerAdBinding
import com.example.pazitelj.models.AdInput
import com.example.pazitelj.ui.CurrentUserViewModel
import com.example.pazitelj.ui.ad.carerAd.FullCarerAdViewModel
import com.example.pazitelj.ui.ad.ownerAd.FullOwnerAdViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.indexOf


class EditOwnerAdFragment : Fragment() {
    private var _binding: FragmentEditOwnerAdBinding? = null
    private val binding get() = _binding!!
    private val currentUser: CurrentUserViewModel by activityViewModels()
    private val adModel: FullOwnerAdViewModel by activityViewModels()
    private val jobList = arrayOf("Šetnja ljubimca","Hranjenje ljubimca kod vlasnika","Hranjenje ljubimca kod pazitelja")
    private var petList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditOwnerAdBinding.inflate(inflater, container, false)

        val jobListAdapter = ArrayAdapter(requireContext(), R.layout.list_item, jobList)
        binding.jobSelectDropdown.setAdapter(jobListAdapter)
        for (pet in currentUser.user.value!!.Pets){
            petList.add(pet.Name)
        }
        val petListAdapter = ArrayAdapter(requireContext(), R.layout.list_item, petList)
        binding.petDropdown.setAdapter(petListAdapter)
        binding.ownerPostButton.setOnClickListener {
            postAd()
        }

        binding.descInput.setText(adModel.ad.value!!.Description)
        binding.jobSelectDropdown.setText(adModel.ad.value!!.Job,false)
        binding.petDropdown.setText(adModel.ad.value!!.PetName,false)


        return binding.root
    }

    private fun postAd() {
        CoroutineScope(Dispatchers.Main).launch {
            val pet = currentUser.user.value!!.Pets.filter { it.Name.equals(binding.petDropdown.text.toString()) }
            adModel.updateAd(binding.descInput.text.toString(),binding.jobSelectDropdown.text.toString(),pet[0].Id!!)
            currentUser.putAd(adModel.ad.value!!)
            val action = EditOwnerAdFragmentDirections.actionEditOwnerAdFragmentToFullOwnerAdFragment(adModel.ad.value!!.Id)
            view?.findNavController()?.navigate(action)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        binding.descInput.setText("")
    }


}
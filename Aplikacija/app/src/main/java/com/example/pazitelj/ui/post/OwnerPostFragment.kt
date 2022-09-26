package com.example.pazitelj.ui.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pazitelj.MainActivity
import com.example.pazitelj.models.AdInput
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentOwnerPostBinding
import com.example.pazitelj.ui.CurrentUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OwnerPostFragment : Fragment() {
    private var _binding: FragmentOwnerPostBinding? = null
    private val binding get() = _binding!!
    private val currentUser: CurrentUserViewModel by activityViewModels()
    private var petList = mutableListOf<String>()
    private val jobList = arrayOf("Šetnja ljubimca","Hranjenje ljubimca kod vlasnika","Hranjenje ljubimca kod pazitelja")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOwnerPostBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        for (pet in currentUser.user.value!!.Pets){
            petList.add(pet.Name)
        }
        val petListAdapter = ArrayAdapter(requireContext(), R.layout.list_item, petList)
        binding.petDropdown.setAdapter(petListAdapter)

        val jobListAdapter = ArrayAdapter(requireContext(),R.layout.list_item,jobList)
        binding.jobSelectDropdown.setAdapter(jobListAdapter)

        binding.ownerPostButton.setOnClickListener {
            postAd()
        }
        return binding.root
    }

    private  fun postAd(){
        val pet = currentUser.user.value!!.Pets.filter { it.Name.equals(binding.petDropdown.text.toString()) }
        val ad = AdInput(1,binding.descInput.text.toString(),binding.jobSelectDropdown.text.toString(),pet[0].Id!!,currentUser.user.value!!.Id)
        CoroutineScope(Dispatchers.Main).launch {
            currentUser.postAd(ad)
        }
        binding.descInput.text!!.clear()
        binding.petDropdown.text.clear()
        binding.jobSelectDropdown.text.clear()

        val action = PostFragmentDirections.actionFragmentPostToPostedFragment()
        findNavController().navigate(action)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.descInput.setText("")
        petList.clear()
        Log.d("postdestroy","destroy")
    }

    override fun onPause() {
        super.onPause()
        binding.descInput.setText("")
        petList.clear()
        binding.petDropdown.text.clear()
        binding.jobSelectDropdown.text.clear()
    }

}
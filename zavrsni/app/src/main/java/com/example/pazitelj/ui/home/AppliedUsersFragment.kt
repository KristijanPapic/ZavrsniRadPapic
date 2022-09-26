package com.example.pazitelj.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentAppliedUsersBinding
import com.example.pazitelj.databinding.FragmentFullOwnerAdBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppliedUsersFragment : Fragment(),IAppliedUsersListAdapter {
    private var _binding: FragmentAppliedUsersBinding? = null
    private val binding get() = _binding!!
    private val appliedUsersViewModel : AppliedUsersViewModel by activityViewModels()
    private  val homeViewModel : HomeViewModel by activityViewModels()
    var adId: String? = null
    var type: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppliedUsersBinding.inflate(inflater, container, false)
        val root: View = binding.root


        arguments?.let {
            adId = it.getString("adId")
            type = it.getInt("type")
        }

        CoroutineScope(Dispatchers.Main).launch {
            appliedUsersViewModel.getAppliedUsers(adId!!,type!!)
            binding.appUsersList.adapter = AppliedUsersAdapter(appliedUsersViewModel.users.value!!,this@AppliedUsersFragment)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun chooseUser(userId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            appliedUsersViewModel.putAppliedUser(adId!!,userId,"selected")
            getActivity()!!.onBackPressed();

        }
        }

    override fun removeUser(userId: String,position: Int) {
        appliedUsersViewModel.putAppliedUser(adId!!,userId,"rejected")
        appliedUsersViewModel.users.value!!.removeAt(position)
        binding.appUsersList.adapter = AppliedUsersAdapter(appliedUsersViewModel.users.value!!,this@AppliedUsersFragment)
    }

}
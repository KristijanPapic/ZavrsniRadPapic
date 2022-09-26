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
import com.example.pazitelj.models.AdInput
import com.example.pazitelj.ui.CurrentUserViewModel
import com.example.pazitelj.ui.ad.carerAd.FullCarerAdViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.indexOf


class EditCarerAdFragment : Fragment() {
    private var _binding: FragmentEditCarerAdBinding? = null
    private val binding get() = _binding!!
    private val currentUser: CurrentUserViewModel by activityViewModels()
    private val adModel: FullCarerAdViewModel by activityViewModels()
    private val jobList = arrayOf("Šetnja ljubimca","Hranjenje ljubimca kod vlasnika","Hranjenje ljubimca kod pazitelja")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditCarerAdBinding.inflate(inflater, container, false)

        val jobListAdapter = ArrayAdapter(requireContext(), R.layout.list_item, jobList)
        binding.jobSelectDropdown.setAdapter(jobListAdapter)

        binding.carerPostButton.setOnClickListener {
            postAd()
        }

        binding.descInput.setText(adModel.ad.value!!.Description)
        binding.jobSelectDropdown.setText(adModel.ad.value!!.Job,false)

        return binding.root
    }

    private fun postAd() {
        CoroutineScope(Dispatchers.Main).launch {
            adModel.updateAd(binding.descInput.text.toString(),binding.jobSelectDropdown.text.toString())
            currentUser.putAd(adModel.ad.value!!)
            val action = EditCarerAdFragmentDirections.actionEditCarerAdFragmentToFullCarerAdFragment(adModel.ad.value!!.Id)
            view?.findNavController()?.navigate(action)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        binding.descInput.setText("")
    }


}
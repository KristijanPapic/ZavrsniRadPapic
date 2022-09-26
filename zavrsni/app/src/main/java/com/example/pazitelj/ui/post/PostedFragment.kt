package com.example.pazitelj.ui.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentPostBinding
import com.example.pazitelj.databinding.FragmentPostedBinding
import com.example.pazitelj.ui.CurrentUserViewModel
import com.example.pazitelj.ui.home.ActiveAdsFragment
import com.example.pazitelj.ui.home.AvailableAdsFragment
import com.example.pazitelj.ui.home.ViewPagerAdapter

class PostedFragment : Fragment() {
    private var _binding: FragmentPostedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostedBinding.inflate(inflater,container,false)
        binding.finBtn.setOnClickListener {
            val actiton = PostedFragmentDirections.actionPostedFragmentToFragmentPost()
            findNavController().navigate(actiton)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
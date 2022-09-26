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
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentPostBinding
import com.example.pazitelj.ui.CurrentUserViewModel
import com.example.pazitelj.ui.home.ActiveAdsFragment
import com.example.pazitelj.ui.home.AvailableAdsFragment
import com.example.pazitelj.ui.home.ViewPagerAdapter

class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private val currentUser: CurrentUserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater,container,false)

        setUpTabs()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setUpTabs(){
        val adapter = PostViewPagerAdapter(childFragmentManager)
        adapter.addFragment(OwnerPostFragment(),"VLASNIK OGLAS")
        adapter.addFragment(CarerPostFragment(),"PAZITELJ OGLAS")
        binding.postViewPager.adapter = adapter
        binding.postTabLayout.setupWithViewPager(binding.postViewPager)
    }
}
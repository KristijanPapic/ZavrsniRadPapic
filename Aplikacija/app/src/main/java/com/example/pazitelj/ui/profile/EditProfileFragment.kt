package com.example.pazitelj.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pazitelj.IPetListAdapter
import com.example.pazitelj.models.Pet
import com.example.pazitelj.PetListAdapter
import com.example.pazitelj.R
import com.example.pazitelj.databinding.AddPetPopupBinding
import com.example.pazitelj.databinding.FragmentEditProfileBinding
import com.example.pazitelj.ui.CurrentUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment(),IPetListAdapter {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val currentUser: CurrentUserViewModel by activityViewModels()
    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private var petsToAdd: MutableList<Pet> = mutableListOf<Pet>()
    companion object {
        fun newInstance() = EditProfileFragment()
    }

    private lateinit var viewModel: EditProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //_binding = FragmentProfileBinding.inflate(inflater,container,false)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile,container,false)
        //binding.profilePhoto.load(currentUser.currentUser.value!!.PictureURL)
        binding.petList.adapter=PetListAdapter(currentUser.user.value!!.Pets,this)
        binding.addPetButton.setOnClickListener {
            createNewPetDialog()
        }
        binding.saveButton.setOnClickListener{
            currentUser.apply {
                setBio(binding.bioInput.text.toString())
                setPhone(binding.phoneInput.text.toString())
                updateUser()
                val action = EditProfileFragmentDirections.actionEditProfileFragmentToFragmentProfile(userId = "",isCurrent = true)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        // TODO: Use the ViewModel
        binding.apply {
            user = currentUser
        }
    }

    fun createNewPetDialog(){
        dialogBuilder = AlertDialog.Builder(requireContext())
        val binding: AddPetPopupBinding = AddPetPopupBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBuilder.setView(binding.root)
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

        binding.cancelButton.setOnClickListener{
            dialog.dismiss()
        }
        binding.addButton.setOnClickListener {
            var checkedType = ""
            when(binding.radioGroup.checkedRadioButtonId){
                R.id.dog_radio -> checkedType = "Dog"
                R.id.cat_radio -> checkedType = "Cat"
                R.id.other_radio -> checkedType = binding.otherInput.text.toString()
            }
            addPet(binding.petNameInput.text.toString(),checkedType,binding.petBreedInput.text.toString())
            dialog.dismiss()
        }
    }

    private fun addPet(name: String,type: String,breed: String){
            //currentUser.addPet(Pet(null,name,type,breed,currentUser.user.value!!.Id))
        currentUser.addPet(Pet(null,name,type,breed,currentUser.user.value!!.Id))
        updatePetList()


    }

    override fun onPetDelete(position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            currentUser.deletePet(position)
            binding.petList.adapter!!.notifyItemRemoved(position)
            //binding.petList.adapter=PetListAdapter(currentUser.user.value!!.Pets)
        }

    }
    private  fun updatePetList(){
        binding.petList.adapter = PetListAdapter(currentUser.user.value!!.Pets,this)
    }

    override fun onDestroy() {
        super.onDestroy()
        currentUser.clearTempPets()
    }

}
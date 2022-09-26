package com.example.pazitelj.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.databinding.AdCarerItemBinding
import com.example.pazitelj.databinding.AppliedUserItemBinding
import com.example.pazitelj.models.Ad
import com.example.pazitelj.models.AppliedUser

class AppliedUsersAdapter(private val users: List<AppliedUser>,private final val iAppliedUsersListAdapter: IAppliedUsersListAdapter) : RecyclerView.Adapter<AppliedUsersAdapter.AppliedUserViewHolder>() {


    class AppliedUserViewHolder(private var binding: AppliedUserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: AppliedUser){
            binding.user = userData
            binding.executePendingBindings()
        }
        val chooseBtn = binding.chooseBtn
        val removeBtn = binding.removeBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedUserViewHolder {
        Log.d("owner","owner")
        return AppliedUserViewHolder(AppliedUserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: AppliedUserViewHolder, position: Int) {
        val item = users[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            //iAdListAdapter.openAd(item.Id,item.Type)
        }
        holder.chooseBtn.setOnClickListener {
            iAppliedUsersListAdapter.chooseUser(item.UserId)
        }

        holder.removeBtn.setOnClickListener {
            iAppliedUsersListAdapter.removeUser(item.UserId,position)
        }
    }

    override fun getItemCount(): Int {
        return users.count()
    }
}
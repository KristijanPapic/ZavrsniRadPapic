package com.example.pazitelj.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.databinding.AdOwnerActiveOwItemBinding
import com.example.pazitelj.databinding.AdOwnerItemBinding
import com.example.pazitelj.models.Ad

class OwnerAdActiveOwAdapter(private final val iAdListAdapter: IAdListAdapter,private final val iOwnerAdsListAdapter: IOwnerAdsListAdapter) : ListAdapter<Ad, OwnerAdActiveOwAdapter.OwnerAdActiveOwViewHolder>(DiffCallback) {


    class OwnerAdActiveOwViewHolder(private var binding: AdOwnerActiveOwItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adData: Ad){
            binding.ad = adData
            binding.executePendingBindings()
        }
        val userCount = binding.appUsers
        val removeBtn = binding.removeAdBtn
        val usersBtn = binding.appUsersBtn
        val userLabel = binding.carerLabel
        val carer = binding.carer
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Ad>() {
        override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.Description == newItem.Description
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerAdActiveOwViewHolder {
        Log.d("owner","owner")
        return OwnerAdActiveOwViewHolder(AdOwnerActiveOwItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: OwnerAdActiveOwViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            iAdListAdapter.openAd(item.Id,item.Type)
        }
        if(item.Active == 0){
            holder.removeBtn.setText("Obriši")
        }
        else {
            holder.removeBtn.setText("Zatvori")
        }
        holder.removeBtn.setOnClickListener {
            if(item.Active == 1){
                iAdListAdapter.concludeAd(item.Id,item.AppliedUser!!.UserId,1,true,position)

            }
            else{
                iAdListAdapter.deleteAd(item.Id,position)
            }
        }
        holder.usersBtn.setOnClickListener {
            iOwnerAdsListAdapter.openAppliedUsers(item.Id,1)
        }
        holder.carer.setOnClickListener {
            iAdListAdapter.openUser(item.AppliedUser!!.UserId)
        }

        if(item.AppliedUserCount == 0){
            holder.usersBtn.isEnabled = false
        }

        if(item.Active > 0){
            holder.usersBtn.visibility = View.GONE
            holder.userCount.visibility = View.GONE

            holder.userLabel.visibility = View.VISIBLE
            holder.carer.visibility = View.VISIBLE
            holder.carer.text = item.AppliedUser?.UserName ?: ""
            holder.removeBtn.text = "ZATVORI"
        }


    }
}
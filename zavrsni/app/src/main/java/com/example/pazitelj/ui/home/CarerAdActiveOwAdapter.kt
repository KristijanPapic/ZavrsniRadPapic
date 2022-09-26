package com.example.pazitelj.ui.home

import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.R
import com.example.pazitelj.databinding.AdCarerActiveOwItemBinding
import com.example.pazitelj.databinding.AdCarerItemBinding
import com.example.pazitelj.databinding.AdOwnerItemBinding
import com.example.pazitelj.models.Ad

class CarerAdActiveOwAdapter(private final val iAdListAdapter: IAdListAdapter,private final val iOwnerAdsListAdapter: IOwnerAdsListAdapter) : ListAdapter<Ad, CarerAdActiveOwAdapter.CarerAdActiveOwViewHolder>(DiffCallback) {


    class CarerAdActiveOwViewHolder(private var binding: AdCarerActiveOwItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adData: Ad){
            Log.d("bindad",adData.toString())
            binding.ad = adData
            binding.executePendingBindings()
        }
        val userCount = binding.appUsers
        val usersBtn = binding.appUsersBtn
        val userLabel = binding.ownerLabel
        val owner = binding.owner
        val removeBtn = binding.removeAdBtn
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Ad>() {
        override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.Description == newItem.Description
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarerAdActiveOwViewHolder {
        Log.d("owner","owner")
        return CarerAdActiveOwViewHolder(AdCarerActiveOwItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: CarerAdActiveOwViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            iAdListAdapter.openAd(item.Id,item.Type)
        }

        holder.usersBtn.setOnClickListener {
            iOwnerAdsListAdapter.openAppliedUsers(item.Id,0)
        }
        if(item.Active == 0){
            holder.removeBtn.setText("Obriši")
        }
        else {
            holder.removeBtn.setText("Zatvori")
        }
        holder.removeBtn.setOnClickListener {
            if(item.Active == 0){
                iAdListAdapter.deleteAd(item.Id,position)
            }
            else{
                iAdListAdapter.concludeAd(item.Id,item.AppliedUser!!.UserId,0,true,position)
            }
        }

        holder.owner.setOnClickListener {
            iAdListAdapter.openUser(item.AppliedUser!!.UserId)
        }
        if(item.AppliedUserCount == 0){
            holder.usersBtn.isEnabled = false
        }

        if(item.Active > 0){
            holder.usersBtn.visibility = View.GONE
            holder.userCount.visibility = View.GONE

            holder.userLabel.visibility = View.VISIBLE
            holder.owner.visibility = View.VISIBLE
            holder.owner.text = item.AppliedUser?.UserName ?: ""
            holder.removeBtn.setText("Završi")
        }
    }
}
package com.example.pazitelj.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.databinding.AdOwnerItemBinding
import com.example.pazitelj.models.Ad

class PetAdAdapter(private final val iAdListAdapter: IAdListAdapter) : ListAdapter<Ad, PetAdAdapter.PetAdViewHolder>(DiffCallback) {


    class PetAdViewHolder(private var binding: AdOwnerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adData: Ad){
            binding.ad = adData
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Ad>() {
        override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.Description == newItem.Description
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetAdViewHolder {
        Log.d("owner","owner")
       return PetAdViewHolder(AdOwnerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: PetAdViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            iAdListAdapter.openAd(item.Id,item.Type)
        }
    }
}
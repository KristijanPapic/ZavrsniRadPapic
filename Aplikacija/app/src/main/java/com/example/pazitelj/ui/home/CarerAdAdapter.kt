package com.example.pazitelj.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.R
import com.example.pazitelj.databinding.AdCarerItemBinding
import com.example.pazitelj.databinding.AdOwnerItemBinding
import com.example.pazitelj.models.Ad

class CarerAdAdapter(private final val iAdListAdapter: IAdListAdapter) : ListAdapter<Ad, CarerAdAdapter.CarerAdViewHolder>(DiffCallback) {


    class CarerAdViewHolder(private var binding: AdCarerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adData: Ad){
            Log.d("bindad",adData.toString())
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarerAdViewHolder {
        Log.d("owner","owner")
        return CarerAdViewHolder(AdCarerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: CarerAdViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            iAdListAdapter.openAd(item.Id,item.Type)
        }
    }
}
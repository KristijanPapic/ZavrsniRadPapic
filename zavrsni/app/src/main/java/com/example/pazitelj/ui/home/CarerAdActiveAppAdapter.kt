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
import com.example.pazitelj.databinding.AdCarerActiveAppItemBinding
import com.example.pazitelj.databinding.AdCarerItemBinding
import com.example.pazitelj.databinding.AdOwnerItemBinding
import com.example.pazitelj.models.Ad

class CarerAdActiveAppAdapter(private final val iAdListAdapter: IAdListAdapter) : ListAdapter<Ad, CarerAdActiveAppAdapter.CarerAdActiveAppViewHolder>(DiffCallback) {


    class CarerAdActiveAppViewHolder(private var binding: AdCarerActiveAppItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adData: Ad){
            Log.d("bindad",adData.toString())
            binding.ad = adData
            binding.executePendingBindings()
        }
        val cancelBtn = binding.cancelBtn
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Ad>() {
        override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean {
            return oldItem.Description == newItem.Description
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarerAdActiveAppViewHolder {
        Log.d("owner","owner")
        return CarerAdActiveAppViewHolder(AdCarerActiveAppItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: CarerAdActiveAppViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            iAdListAdapter.openAd(item.Id,item.Type)
        }
        if(item.Active == 0){
            holder.cancelBtn.setText("Otkaži")
        }
        else {
            holder.cancelBtn.setText("Zatvori")
        }

        holder.cancelBtn.setOnClickListener {
            if(item.Active == 0 || item.Status == "declined"){
                iAdListAdapter.cancelAd(item.Id,position)
            }
            else{
                iAdListAdapter.concludeAd(item.Id,item.UserId,0,false,position)
            }


        }
    }
}
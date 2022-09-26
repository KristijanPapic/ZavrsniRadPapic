package com.example.pazitelj.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.databinding.AdOwnerActiveAppItemBinding
import com.example.pazitelj.databinding.AdOwnerItemBinding
import com.example.pazitelj.models.Ad

class OwnerAdActiveAppAdapter(private final val iAdListAdapter: IAdListAdapter) : ListAdapter<Ad, OwnerAdActiveAppAdapter.OwnerAdActiveAppViewHolder>(DiffCallback) {


    class OwnerAdActiveAppViewHolder(private var binding: AdOwnerActiveAppItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adData: Ad){
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerAdActiveAppViewHolder {
        Log.d("owner","owner")
        return OwnerAdActiveAppViewHolder(AdOwnerActiveAppItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: OwnerAdActiveAppViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        if(item.Active == 0){
            holder.cancelBtn.setText("Otkaži")
        }
        else {
            holder.cancelBtn.setText("Zatvori")
        }

        holder.itemView.setOnClickListener {
            iAdListAdapter.openAd(item.Id,item.Type)
        }
        holder.cancelBtn.setOnClickListener {
            Log.d("caaaaaa",item.Status)
            if(item.Active == 0 || item.Status == "rejected"){
                iAdListAdapter.cancelAd(item.Id,position)
            }
            else{
                iAdListAdapter.concludeAd(item.Id,item.UserId,1,false,position)
            }

        }

    }
}
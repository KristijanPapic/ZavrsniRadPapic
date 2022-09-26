package com.example.pazitelj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.models.Pet

class PetListAdapterViewOnly(
    private val pets: List<Pet>,
) : RecyclerView.Adapter<PetListAdapterViewOnly.PetItemViewHolder>() {

    class PetItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val petName = view.findViewById<TextView>(R.id.pet_item_name)
        val petType = view.findViewById<TextView>(R.id.pet_item_type)
        val petBreed = view.findViewById<TextView>(R.id.pet_item_breed)
        val petIcon = view.findViewById<ImageView>(R.id.pet_icon)
    }

    override fun getItemCount(): Int {
        return pets.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetItemViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pet_item_view_only, parent,false)

        return PetItemViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PetItemViewHolder, position: Int) {
        val item = pets[position]
        holder.petName.text = item.Name
        if(item.Kind == "Dog"){
            holder.petType.text = "Pas"
        }
        else if(item.Kind == "Cat"){
            holder.petType.text = "Mačka"
        }
        else{
            holder.petType.text = item.Kind
        }
        holder.petBreed.text = item.Breed
        when(item.Kind){
            "Dog" -> holder.petIcon.setImageResource(R.drawable.dog)
            "Cat" -> holder.petIcon.setImageResource(R.drawable.cat)
            else -> {holder.petIcon.setImageResource(R.drawable.other)}
        }
    }
}
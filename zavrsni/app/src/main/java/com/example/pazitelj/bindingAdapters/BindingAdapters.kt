package com.example.pazitelj.bindingAdapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pazitelj.models.Ad
import com.example.pazitelj.R
import com.example.pazitelj.ui.home.*

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Ad>?) {
    if(recyclerView.adapter is PetAdAdapter){
        val adapter = recyclerView.adapter as PetAdAdapter
        adapter.submitList(data)
    }
    else if(recyclerView.adapter is CarerAdAdapter){
        Log.d("carerbind",data.toString())
        val adapter = recyclerView.adapter as CarerAdAdapter
        adapter.submitList(data)
    }
    else if(recyclerView.adapter is CarerAdActiveAppAdapter){
        val adapter = recyclerView.adapter as CarerAdActiveAppAdapter
        adapter.submitList(data)
    }
    else if(recyclerView.adapter is CarerAdActiveOwAdapter){
        val adapter = recyclerView.adapter as CarerAdActiveOwAdapter
        adapter.submitList(data)
    }
    else if(recyclerView.adapter is OwnerAdActiveAppAdapter){
        val adapter = recyclerView.adapter as OwnerAdActiveAppAdapter
        adapter.submitList(data)
    }
    else if(recyclerView.adapter is OwnerAdActiveOwAdapter){
        val adapter = recyclerView.adapter as OwnerAdActiveOwAdapter
        adapter.submitList(data)
    }
}

/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        imgView.load(imgUrl) {
            placeholder(R.drawable.ic_download)
        }
    }
}

@BindingAdapter("petImage")
fun setImage(imgView: ImageView, petType: String?) {
    if (petType != null) {
        Log.d("bindingpettype",petType)
    }
    petType.let {
        when (petType) {
            "Dog" -> imgView.setImageResource(R.drawable.dog)
            "Cat" -> imgView.setImageResource(R.drawable.cat)
            else -> imgView.setImageResource(R.drawable.other)
        }
    }
    }

@BindingAdapter("appliedUserCount")
fun setCount(textView : TextView, count: Int) {
    if (count == 0){
        textView.text = "Nema prijava"
    }
    else if (count == 1){
        textView.text = "$count prijava"
    }
    else{
        textView.text = "$count prijava"
    }
}

@BindingAdapter("statusColor")
fun setStatusColor(textView: TextView,status:String){
    if(status == "selected"){
        textView.setTextColor(Color.GREEN)
    }
    else if(status == "pending"){
        textView.setTextColor(Color.rgb(242, 238, 114))
    }
    else if(status == "rejected"){
        textView.setTextColor(Color.RED)
    }
}

@BindingAdapter("statusText")
fun setStatusText(textView: TextView,status:String){
    if(status == "selected"){
        textView.setText("odabrano")
    }
    else if(status == "pending"){
        textView.setText("u tijeku")
    }
    else if(status == "rejected"){
        textView.setText("odbijeno")
    }
}

package com.example.cleanxyandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanxyandroid.R
import com.example.cleanxyandroid.model.addressInfoForBooking

class addressBookingAdapter (private var aContext : Context, private var isFragment : Boolean = false, private var aAddress : List<addressInfoForBooking>)
    : RecyclerView.Adapter<addressBookingAdapter.ViewHolder>(){

    private lateinit var aListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int) {

        }
    }

    fun setOnItemClickListener (listener : onItemClickListener) {
        aListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(aContext).inflate(R.layout.item_address_booking_activity, parent, false), aListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = aAddress[position]
        holder.addressText.text = address.addressText
    }

    override fun getItemCount(): Int {
        return aAddress.size
    }

    class ViewHolder(@NonNull itemView : View, listener : onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var addressText : TextView = itemView.findViewById(R.id.textItemAddressBookingActivity)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
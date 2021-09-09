package com.example.cleanxyandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleanxyandroid.R
import com.example.cleanxyandroid.model.serviceInfoForBooking

class ServiceBookingAdapter (private var sContext : Context, private var isFragment : Boolean = false, private var sService : List<serviceInfoForBooking>)
    : RecyclerView.Adapter<ServiceBookingAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(sContext).inflate(R.layout.item_service_booking_activity, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = sService[position]

        holder.service_type.text = service.serviceText
        holder.service_price.text ="Price (for 1 hour) : Rs.${service.servicePrice}"

        when (service.serviceType) {
            1 -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_vaccum_cleaner_cxy).into(holder.img_view)
            }
            2 -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_washing_utensils).into(holder.img_view)
            }
            3 -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_cleaning_kitchen).into(holder.img_view)
            }
            4 -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_washing_machine_cxy).into(holder.img_view)
            }
            5 -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_cleaning_bathroom).into(holder.img_view)
            }
            6 -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_grocery_shopping).into(holder.img_view)
            }
            else -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_cleaning_cxy).into(holder.img_view)
            }
        }
    }

    override fun getItemCount(): Int {
        return sService.size
    }

    class ViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView) {
        var service_type : TextView = itemView.findViewById(R.id.serviceTypeTextItemBookingActivity)
        var service_price : TextView = itemView.findViewById(R.id.servicePriceTextItemBookingActivity)
        var img_view : ImageView = itemView.findViewById(R.id.imgViewItemServiceBookingActivity)
    }
}

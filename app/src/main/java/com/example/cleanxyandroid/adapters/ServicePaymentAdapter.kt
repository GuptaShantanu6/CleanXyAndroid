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
import com.example.cleanxyandroid.model.ServiceInfoForPayment

class ServicePaymentAdapter (private var sContext : Context, private var isFragment : Boolean = false, private var sService : List<ServiceInfoForPayment>)
    : RecyclerView.Adapter<ServicePaymentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(sContext).inflate(R.layout.item_service_payment_activity, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = sService[position]

        holder.serviceName.text = service.serviceText

        when (service.serviceText) {
            "Floor Cleaning" -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_vaccum_cleaner_cxy).into(holder.serviceImg)
            }
            "Washing Utensils" -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_washing_utensils).into(holder.serviceImg)
            }
            "Cleaning Kitchen" -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_cleaning_kitchen).into(holder.serviceImg)
            }
            "Washing" -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_washing_machine_cxy).into(holder.serviceImg)
            }
            "Cleaning Bathroom" -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_cleaning_bathroom).into(holder.serviceImg)
            }
            "Grocery Shopping" -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_grocery_shopping).into(holder.serviceImg)
            }
            "Cleaning" -> {
                Glide.with(holder.itemView.context).load(R.drawable.ic_cleaning_cxy).into(holder.serviceImg)
            }
        }
    }

    override fun getItemCount(): Int {
        return sService.size
    }

    class ViewHolder(@NonNull itemView : View)  :RecyclerView.ViewHolder(itemView) {
        var serviceName : TextView = itemView.findViewById(R.id.textItemServicePaymentActivity)
        var serviceImg : ImageView = itemView.findViewById(R.id.imgViewItemServicePaymentActivity)
    }
}
package com.example.babycare

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class NannyAdapter(
    private var nannyList: List<Nanny>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<NannyAdapter.NannyViewHolder>() {

    inner class NannyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nannyName)
        val qualification: TextView = itemView.findViewById(R.id.nannyQualification)
        val experience: TextView = itemView.findViewById(R.id.nannyExperience)
        val rating: TextView = itemView.findViewById(R.id.nannyRating)
        val price: TextView = itemView.findViewById(R.id.nannyRate)
        val bookButton: Button = itemView.findViewById(R.id.bookButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NannyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.nanny_item, parent, false)
        return NannyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NannyViewHolder, position: Int) {
        val nanny = nannyList[position]

        holder.name.text = nanny.name
        holder.qualification.text = nanny.qualification
        holder.experience.text = nanny.experience
        holder.rating.text = "⭐ ${nanny.rating}"
        holder.price.text = "₹${nanny.pricePerHour}/hr"

        holder.bookButton.setOnClickListener {
            val bottomSheet = BookBottomSheet.newInstance(nanny)
            bottomSheet.show(fragmentManager, "BookBottomSheet")
        }
    }

    override fun getItemCount(): Int = nannyList.size

    fun updateData(newList: List<Nanny>) {
        nannyList = newList
        notifyDataSetChanged()
    }
}

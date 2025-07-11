package com.example.babycare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookingAdapter(private val bookingList: List<Booking>) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.bookingName)
        val specialization: TextView = view.findViewById(R.id.bookingSpecialization)
        val experience: TextView = view.findViewById(R.id.bookingExperience)
        val rating: TextView = view.findViewById(R.id.bookingRating)
        val dateTime: TextView = view.findViewById(R.id.bookingDateTime)
        val days: TextView = view.findViewById(R.id.bookingDays)  // 🆕 Days TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_item, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]
        holder.name.text = booking.providerName
        holder.specialization.text = booking.specialization
        holder.experience.text = booking.experience
        holder.rating.text = "⭐ ${booking.rating}"
        holder.dateTime.text = "📅 ${booking.date} ⏰ ${booking.time}"
        holder.days.text = "📆 Days: ${booking.days}"  // 🆕 Display number of days
    }

    override fun getItemCount(): Int = bookingList.size
}

package com.example.babycare

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookingHistoryActivity : AppCompatActivity() {

    companion object {
        var bookings: MutableList<Booking> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_history)

        val noBookingsText = findViewById<TextView>(R.id.noBookingsText)
        val ongoingTitle = findViewById<TextView>(R.id.ongoingTitle)
        val historyTitle = findViewById<TextView>(R.id.historyTitle)
        val recyclerOngoing = findViewById<RecyclerView>(R.id.recyclerOngoingBookings)
        val recyclerPast = findViewById<RecyclerView>(R.id.recyclerPastBookings)

        // Sort bookings by timestamp (latest first)
        val sortedList = bookings.sortedByDescending { it.timestamp }

        // Separate ongoing and past bookings
        val ongoing = sortedList.filter { it.isOngoing }
        val past = sortedList.filter { !it.isOngoing }

        if (bookings.isEmpty()) {
            noBookingsText.visibility = View.VISIBLE
        } else {
            noBookingsText.visibility = View.GONE

            if (ongoing.isNotEmpty()) {
                ongoingTitle.visibility = View.VISIBLE
                recyclerOngoing.visibility = View.VISIBLE
                recyclerOngoing.layoutManager = LinearLayoutManager(this)
                recyclerOngoing.adapter = BookingAdapter(ongoing)
            }

            if (past.isNotEmpty()) {
                historyTitle.visibility = View.VISIBLE
                recyclerPast.visibility = View.VISIBLE
                recyclerPast.layoutManager = LinearLayoutManager(this)
                recyclerPast.adapter = BookingAdapter(past)
            }
        }
    }
}

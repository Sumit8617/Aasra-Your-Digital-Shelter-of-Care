package com.example.babycare

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.babycare.databinding.BottomSheetBookBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class BookBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetBookBinding
    private var selectedDate: String? = null
    private var selectedTime: String? = null
    private var selectedDays: Int? = null

    private lateinit var nanny: Nanny

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nanny = Nanny(
                it.getString("name", ""),
                it.getString("qualification", ""),
                it.getString("experience", ""),
                it.getDouble("rating", 0.0),
                it.getInt("price", 0)
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetBookBinding.inflate(inflater, container, false)

        // 📅 Select Date
        binding.dateCard.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireContext(), { _, year, month, day ->
                selectedDate = "$day/${month + 1}/$year"
                binding.dateText.text = selectedDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        // ⏰ Select Time
        binding.timeCard.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePicker = TimePickerDialog(requireContext(), { _, hour, minute ->
                selectedTime = String.format("%02d:%02d", hour, minute)
                binding.timeText.text = selectedTime
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            timePicker.show()
        }

        // 📆 Select Days + Update Price
        binding.dayCard.setOnClickListener {
            val daysArray = (1..30).map { "$it Day${if (it > 1) "s" else ""}" }.toTypedArray()
            AlertDialog.Builder(requireContext())
                .setTitle("Select Duration")
                .setItems(daysArray) { _, which ->
                    selectedDays = which + 1
                    binding.dayText.text = daysArray[which]

                    val totalPrice = selectedDays!! * nanny.pricePerHour
                    binding.priceText.text = "Total: ₹$totalPrice"
                }
                .show()
        }

        // ✅ Confirm Booking
        binding.confirmBookingBtn.setOnClickListener {
            if (selectedDate == null || selectedTime == null || selectedDays == null) {
                Toast.makeText(requireContext(), "Please select date, time, and number of days", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showConfirmationDialog()
        }

        return binding.root
    }

    private fun showConfirmationDialog() {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_booking_confirmation, null)

        val dialog = AlertDialog.Builder(requireContext()).setView(view).create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val details = view.findViewById<TextView>(R.id.dialogDetails)
        val cancel = view.findViewById<Button>(R.id.cancelButton)
        val confirm = view.findViewById<Button>(R.id.confirmButton)

        val totalPrice = selectedDays!! * nanny.pricePerHour

        val message = """
            👤 Name: ${nanny.name}
            🧑‍🏫 Specialization: ${nanny.qualification}
            🏆 Experience: ${nanny.experience}
            ⭐ Rating: ${nanny.rating}
            
            📅 Date: $selectedDate
            ⏰ Time: $selectedTime
            📆 Duration: $selectedDays Day${if (selectedDays != 1) "s" else ""}
            💰 Total: ₹$totalPrice
        """.trimIndent()

        details.text = message

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        confirm.setOnClickListener {
            BookingHistoryActivity.bookings.add(
                Booking(
                    providerName = nanny.name,
                    specialization = nanny.qualification,
                    experience = nanny.experience,
                    rating = nanny.rating,
                    date = "$selectedDate ($selectedDays Days)",
                    time = selectedTime ?: "",
                    days = selectedDays ?: 1
                )
            )
            Toast.makeText(requireContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            dismiss()
            startActivity(Intent(requireContext(), BookingHistoryActivity::class.java))
        }

        dialog.show()
    }

    companion object {
        fun newInstance(nanny: Nanny): BookBottomSheet {
            val args = Bundle().apply {
                putString("name", nanny.name)
                putString("qualification", nanny.qualification)
                putString("experience", nanny.experience)
                putDouble("rating", nanny.rating)
                putInt("price", nanny.pricePerHour)
            }
            return BookBottomSheet().apply { arguments = args }
        }
    }
}

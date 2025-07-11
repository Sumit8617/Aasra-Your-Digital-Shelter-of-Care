package com.example.babycare

data class Booking(
    val providerName: String = "",
    val specialization: String = "",
    val experience: String = "",
    val rating: Double = 0.0,
    val date: String = "",
    val time: String = "",
    val days: Int = 1,
    val phone: String = "",
    val email: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isOngoing: Boolean = true
)


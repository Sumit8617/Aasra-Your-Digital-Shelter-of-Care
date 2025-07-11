package com.example.babycare.model

data class ProviderModel(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val experience: String = "",
    val gender: String = "" // 👈 ADD this if not present
)

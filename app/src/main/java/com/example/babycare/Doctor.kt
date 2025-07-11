package com.example.babycare

class Doctor : BaseCareActivity() {
    override val activityTitle = "Doctor Visit"
    override val subtitle = "Consult home visit doctors near you"
    override val providerList = listOf(
        Nanny("Dr. Suman Rao", "MBBS, General Physician", "10 years experience", 4.8, 1000),
        Nanny("Dr. Vikas Mehta", "MD Internal Medicine", "12 years experience", 4.6, 1200),
        Nanny("Dr. Anjali Verma", "MBBS, Gynecologist", "8 years experience", 4.7, 950),
        Nanny("Dr. Rajeev Kumar", "MBBS, General Physician", "15 years experience", 4.9, 1100),
        Nanny("Dr. Neha Sharma", "MD Dermatology", "7 years experience", 4.5, 1300),
        Nanny("Dr. Arjun Singh", "MBBS, Emergency Care", "9 years experience", 4.6, 1050),
        Nanny("Dr. Kavita Nair", "MBBS, Family Physician", "11 years experience", 4.8, 1150),
        Nanny("Dr. Deepak Chauhan", "MD Cardiology", "13 years experience", 4.7, 1500),
        Nanny("Dr. Priya Sinha", "MBBS, Gynecologist", "10 years experience", 4.6, 1250),
        Nanny("Dr. Rohan Gupta", "MBBS, General Practitioner", "6 years experience", 4.4, 900)
    )
}

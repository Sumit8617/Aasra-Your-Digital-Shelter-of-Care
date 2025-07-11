package com.example.babycare

class In_Home_Care : BaseCareActivity() {
    override val activityTitle = "In-Home Care"
    override val subtitle = "Personalized elder support at home"
    override val providerList = listOf(
        Nanny("Anita Mishra", "Certified Home Nurse", "5 years experience", 4.6, 850),
        Nanny("Karan Thakur", "Elderly Support Specialist", "4 years experience", 4.4, 900),
        Nanny("Neha Bansal", "Companion Caregiver", "6 years experience", 4.5, 870),
        Nanny("Ramesh Verma", "Dementia Care Expert", "7 years experience", 4.7, 950),
        Nanny("Lata Joshi", "Post-Hospitalization Care", "5 years experience", 4.3, 800),
        Nanny("Manoj Sharma", "Trained Home Attendant", "8 years experience", 4.8, 1000),
        Nanny("Sunita Mehta", "Basic Nursing Support", "3 years experience", 4.2, 780),
        Nanny("Ajay Khanna", "Mobility Assistance", "6 years experience", 4.6, 920),
        Nanny("Rekha Iyer", "Palliative Care Nurse", "9 years experience", 4.9, 1050),
        Nanny("Sonal Raj", "Incontinence Care Specialist", "4 years experience", 4.4, 860)
    )
}

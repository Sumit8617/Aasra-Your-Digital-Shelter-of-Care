package com.example.babycare

class Palliative_Care : BaseCareActivity() {
    override val activityTitle = "Palliative Care"
    override val subtitle = "End-of-life care with dignity and comfort"
    override val providerList = listOf(
        Nanny("Dr. Renu Sharma", "MD Palliative Medicine", "10 years experience", 4.9, 1500),
        Nanny("Amit Joshi", "Nursing Expert", "7 years experience", 4.7, 1300),
        Nanny("Dr. Kavita Menon", "Pain Management Specialist", "9 years experience", 4.8, 1450),
        Nanny("Sandeep Arora", "Certified Palliative Nurse", "6 years experience", 4.6, 1250),
        Nanny("Pooja Desai", "Emotional Support Caregiver", "5 years experience", 4.5, 1100),
        Nanny("Dr. Manish Patel", "Oncology Palliative Consultant", "8 years experience", 4.7, 1400),
        Nanny("Neha Gupta", "End-of-life Support Nurse", "4 years experience", 4.4, 1000),
        Nanny("Rahul Batra", "Hospice Care Assistant", "6 years experience", 4.6, 1200),
        Nanny("Meenal Jain", "Comfort Therapy Specialist", "7 years experience", 4.5, 1150),
        Nanny("Dr. Rajesh Tiwari", "Home-Based Palliative Consultant", "10 years experience", 4.8, 1550)
    )
}

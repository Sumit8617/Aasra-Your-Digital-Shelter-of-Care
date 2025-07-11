package com.example.babycare

class Chronic_Disease_Care : BaseCareActivity() {
    override val activityTitle = "Chronic Disease Care"
    override val subtitle = "Long-term care for chronic conditions"
    override val providerList = listOf(
        Nanny("Dr. Seema Desai", "MD Geriatrics", "15 years experience", 4.9, 1100),
        Nanny("Nidhi Taneja", "Chronic Illness Nurse", "6 years experience", 4.5, 850),
        Nanny("Dr. Rajeev Menon", "Diabetes & Hypertension Specialist", "12 years experience", 4.8, 1200),
        Nanny("Anita Raghav", "Home Care Nurse (COPD)", "8 years experience", 4.6, 900),
        Nanny("Dr. Kavita Rao", "Chronic Pain Management", "10 years experience", 4.7, 1050),
        Nanny("Saurabh Mishra", "Home Visit Caregiver", "6 years experience", 4.4, 780),
        Nanny("Meenal Shah", "Kidney & Dialysis Care", "7 years experience", 4.5, 990),
        Nanny("Dr. Sameer Kaul", "Cardiac Rehab Specialist", "9 years experience", 4.6, 1100),
        Nanny("Priya Chauhan", "Asthma & COPD Nurse", "5 years experience", 4.3, 850),
        Nanny("Rita Malhotra", "Elderly Chronic Support", "11 years experience", 4.7, 970)
    )
}

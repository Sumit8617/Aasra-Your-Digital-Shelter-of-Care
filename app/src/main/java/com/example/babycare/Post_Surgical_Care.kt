package com.example.babycare

class Post_Surgical_Care : BaseCareActivity() {
    override val activityTitle = "Post Surgical Care"
    override val subtitle = "Recover safely with expert care at home"
    override val providerList = listOf(
        Nanny("Anjali Bhatt", "Post-Op Nurse", "5 years experience", 4.6, 900),
        Nanny("Ramesh Solanki", "Home Healthcare Expert", "7 years experience", 4.7, 950),
        Nanny("Sunita Chauhan", "Senior Recovery Nurse", "8 years experience", 4.8, 1000),
        Nanny("Vikram Patel", "Post Surgery Attendant", "6 years experience", 4.5, 850),
        Nanny("Poonam Kaur", "Orthopedic Recovery Care", "7 years experience", 4.6, 970),
        Nanny("Rahul Desai", "Wound Dressing Specialist", "4 years experience", 4.4, 820),
        Nanny("Meena Sood", "Senior Home Nurse", "10 years experience", 4.9, 1100),
        Nanny("Jatin Verma", "ICU-Trained Assistant", "5 years experience", 4.7, 980),
        Nanny("Neha Sharma", "Surgical Rehab Nurse", "6 years experience", 4.5, 890),
        Nanny("Dilip Rana", "Geriatric Post-Op Care", "9 years experience", 4.6, 940)
    )
}

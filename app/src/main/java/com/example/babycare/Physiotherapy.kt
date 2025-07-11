package com.example.babycare

class Physiotherapy : BaseCareActivity() {
    override val activityTitle = "Physiotherapy"
    override val subtitle = "Find certified physiotherapists near you"
    override val providerList = listOf(
        Nanny("Dr. Meena Gupta", "MPT, Ortho Specialist", "8 years experience", 4.8, 1200),
        Nanny("Sandeep Rai", "BPT, Neuro Physio", "6 years experience", 4.5, 1000),
        Nanny("Anjali Verma", "BPT, Geriatric Care", "5 years experience", 4.6, 950),
        Nanny("Ravi Deshmukh", "MPT, Sports Injury Expert", "7 years experience", 4.7, 1100),
        Nanny("Poonam Singh", "Ortho & Neuro Physio", "6 years experience", 4.4, 980),
        Nanny("Dr. Ritu Awasthi", "Rehab Specialist", "9 years experience", 4.9, 1300),
        Nanny("Nikhil Joshi", "BPT, Pediatric Physio", "4 years experience", 4.3, 900),
        Nanny("Deepika Kumari", "Post-Surgical Therapy", "5 years experience", 4.5, 1050),
        Nanny("Dr. Tarun Paul", "Manual Therapy Expert", "8 years experience", 4.6, 1150),
        Nanny("Reema Shah", "Certified Physio Trainer", "6 years experience", 4.4, 990)
    )
}

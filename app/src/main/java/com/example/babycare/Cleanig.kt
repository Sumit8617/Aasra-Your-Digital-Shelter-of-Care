package com.example.babycare

class Cleaning : BaseCareActivity() {
    override val activityTitle = "Cleaning Service"
    override val subtitle = "Deep home cleaning & sanitation services"
    override val providerList = listOf(
        Nanny("Kavita Saini", "Home Cleaner", "4 years experience", 4.3, 600),
        Nanny("Arun Kumar", "Sanitation Expert", "6 years experience", 4.5, 750),
        Nanny("Ritu Yadav", "Kitchen & Bathroom Cleaner", "5 years experience", 4.6, 700),
        Nanny("Mohit Chauhan", "Deep Cleaning Specialist", "7 years experience", 4.7, 850),
        Nanny("Simran Kaur", "Housekeeping Service", "8 years experience", 4.4, 900),
        Nanny("Vikas Sharma", "Commercial Cleaning", "10 years experience", 4.8, 1050),
        Nanny("Manju Bhardwaj", "Sofa & Carpet Cleaning", "5 years experience", 4.5, 780),
        Nanny("Devansh Patil", "Window & Glass Cleaning", "3 years experience", 4.1, 620),
        Nanny("Rina Bose", "Apartment Deep Cleaner", "6 years experience", 4.6, 830),
        Nanny("Nitesh Tiwari", "Daily Cleaning Staff", "4 years experience", 4.3, 700)
    )
}

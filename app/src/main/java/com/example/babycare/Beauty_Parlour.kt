package com.example.babycare

class Beauty_Parlour : BaseCareActivity() {
    override val activityTitle = "Beauty Parlour"
    override val subtitle = "Beauty and grooming services at your doorstep"
    override val providerList = listOf(
        Nanny("Pooja Sinha", "Makeup Artist", "6 years experience", 4.7, 950),
        Nanny("Anjali Kapoor", "Hair Stylist", "8 years experience", 4.6, 1000),
        Nanny("Rekha Joshi", "Facial & Cleanup Expert", "5 years experience", 4.5, 850),
        Nanny("Simran Arora", "Bridal Makeup", "10 years experience", 4.9, 1500),
        Nanny("Nikita Bansal", "Manicure & Pedicure", "4 years experience", 4.4, 700),
        Nanny("Tanya Mehta", "Waxing & Threading", "6 years experience", 4.3, 650),
        Nanny("Preeti Sahni", "Spa & Massage", "7 years experience", 4.8, 1200),
        Nanny("Ritika Deshmukh", "Hair Color Specialist", "5 years experience", 4.5, 950),
        Nanny("Kavya Rao", "Skincare & Glow Therapy", "6 years experience", 4.6, 890),
        Nanny("Neha Shah", "Glamour Look Expert", "3 years experience", 4.2, 780)
    )
}

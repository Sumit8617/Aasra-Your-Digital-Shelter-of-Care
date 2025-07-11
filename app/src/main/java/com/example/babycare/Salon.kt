package com.example.babycare

class Salon : BaseCareActivity() {
    override val activityTitle = "Beauty Parlour"
    override val subtitle = "Expert beauty services by professionals"
    override val providerList = listOf(
        Nanny("Anjali Sharma", "Hair & Makeup Artist", "5 years experience", 4.6, 499),
        Nanny("Neha Kapoor", "Skincare Specialist", "6 years experience", 4.4, 599),
        Nanny("Ritika Mehra", "Bridal Makeup Expert", "7 years experience", 4.5, 899),
        Nanny("Simran Kaur", "Facial & Cleanup Specialist", "4 years experience", 4.3, 449),
        Nanny("Priya Joshi", "Hair Spa & Styling", "8 years experience", 4.7, 749),
        Nanny("Kavita Rani", "Nail Art & Manicure", "6 years experience", 4.6, 599),
        Nanny("Poonam Verma", "Waxing & Threading", "3 years experience", 4.2, 399),
        Nanny("Aarti Yadav", "Home Parlour Services", "5 years experience", 4.5, 499),
        Nanny("Sneha Dubey", "Makeup & Saree Draping", "9 years experience", 4.8, 999),
        Nanny("Jyoti Singh", "Complete Beauty Services", "7 years experience", 4.4, 649)
    )
}

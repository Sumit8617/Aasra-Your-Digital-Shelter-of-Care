package com.example.babycare

class Plumbing : BaseCareActivity() {
    override val activityTitle = "Plumbing Service"
    override val subtitle = "Reliable plumbing and pipe repair solutions"
    override val providerList = listOf(
        Nanny("Rahul Vaidya", "Pipe Leak Specialist", "9 years experience", 4.6, 900),
        Nanny("Karan Thakur", "Bathroom Plumbing", "6 years experience", 4.5, 850),
        Nanny("Manoj Patel", "Water Tank Cleaning", "5 years experience", 4.4, 780),
        Nanny("Ajay Sharma", "Drainage Expert", "8 years experience", 4.7, 950),
        Nanny("Aman Khatri", "Tap & Shower Repair", "4 years experience", 4.3, 750),
        Nanny("Ravi Bhushan", "Toilet Repair", "7 years experience", 4.5, 800),
        Nanny("Rajeev Nair", "Water Motor Fitting", "10 years experience", 4.8, 980),
        Nanny("Nitin Reddy", "Kitchen Sink Fixer", "6 years experience", 4.6, 890),
        Nanny("Shyam Rawat", "Sewer Blockage Clearer", "5 years experience", 4.3, 820),
        Nanny("Imran Khan", "General Plumber", "3 years experience", 4.2, 700)
    )
}

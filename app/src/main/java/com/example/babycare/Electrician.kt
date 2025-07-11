package com.example.babycare

class Electrician : BaseCareActivity() {
    override val activityTitle = "Electrician Service"
    override val subtitle = "Expert electrical installations and repairs"
    override val providerList = listOf(
        Nanny("Sandeep Yadav", "Certified Electrician", "7 years experience", 4.6, 950),
        Nanny("Harish Gupta", "Home Wiring Expert", "8 years experience", 4.8, 1050),
        Nanny("Deepak Mehra", "Fan & Light Fitting", "6 years experience", 4.5, 880),
        Nanny("Ankush Thakur", "Inverter Installation", "5 years experience", 4.4, 850),
        Nanny("Reena Mishra", "Switchboard Expert", "4 years experience", 4.3, 780),
        Nanny("Vishal Dubey", "Appliance Wiring", "6 years experience", 4.5, 920),
        Nanny("Ramesh Prasad", "Short Circuit Fixer", "9 years experience", 4.7, 980),
        Nanny("Kishore Nayak", "Power Backup Setup", "7 years experience", 4.6, 940),
        Nanny("Mehul Shah", "Electrical Consultant", "10 years experience", 4.9, 1150),
        Nanny("Naina Joshi", "Emergency Electrician", "3 years experience", 4.2, 720)
    )
}

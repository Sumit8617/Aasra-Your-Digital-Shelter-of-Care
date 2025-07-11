package com.example.babycare

class AC_Repair : BaseCareActivity() {
    override val activityTitle = "AC Repair Service"
    override val subtitle = "Air conditioning installation and maintenance"
    override val providerList = listOf(
        Nanny("Amit Verma", "HVAC Technician", "8 years experience", 4.8, 950),
        Nanny("Preeti Mehra", "AC Mechanic", "5 years experience", 4.4, 800),
        Nanny("Rakesh Kumar", "Cooling Systems Expert", "10 years experience", 4.9, 1150),
        Nanny("Anjali Singh", "Split & Window AC Repair", "6 years experience", 4.5, 890),
        Nanny("Naveen Chauhan", "Freon Specialist", "7 years experience", 4.6, 970),
        Nanny("Tanya Suri", "Airflow Balancer", "4 years experience", 4.2, 750),
        Nanny("Harshit Dube", "AC Installation Expert", "9 years experience", 4.7, 1100),
        Nanny("Kritika Joshi", "Duct Cleaning & Service", "5 years experience", 4.3, 820),
        Nanny("Umesh Bansal", "Commercial AC Repair", "11 years experience", 4.9, 1250),
        Nanny("Divya Kapoor", "AC Smart Control Setup", "3 years experience", 4.1, 790)
    )
}

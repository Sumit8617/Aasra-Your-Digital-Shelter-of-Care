package com.example.babycare

class ApplianceService : BaseCareActivity() {
    override val activityTitle = "Appliance Service"
    override val subtitle = "Repair & servicing of household appliances"
    override val providerList = listOf(
        Nanny("Neeraj Malhotra", "Appliance Technician", "9 years experience", 4.6, 950),
        Nanny("Priya Nair", "Electronics Specialist", "5 years experience", 4.2, 850),
        Nanny("Suresh Mehta", "Microwave Repair", "6 years experience", 4.5, 800),
        Nanny("Divya Sharma", "Refrigerator Technician", "7 years experience", 4.7, 970),
        Nanny("Aakash Gupta", "Washing Machine Expert", "8 years experience", 4.8, 1050),
        Nanny("Swati Menon", "Mixer & Grinder Repair", "4 years experience", 4.3, 780),
        Nanny("Harsh Rawat", "Home Appliance Generalist", "10 years experience", 4.9, 1150),
        Nanny("Shruti Chhabra", "Induction & Oven Repair", "5 years experience", 4.4, 830),
        Nanny("Ravindra Singh", "Chimney Servicing", "6 years experience", 4.5, 920),
        Nanny("Aditi Jain", "Dishwasher Maintenance", "3 years experience", 4.1, 750)
    )
}

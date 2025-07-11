package com.example.babycare

class Home_Shifting : BaseCareActivity() {
    override val activityTitle = "Home Shifting"
    override val subtitle = "Safe & fast relocation services"
    override val providerList = listOf(
        Nanny("Vikas Relocators", "Domestic Packers & Movers", "10 years experience", 4.8, 3500),
        Nanny("Suhana Logistics", "Intercity Shifting Expert", "7 years experience", 4.6, 3000),
        Nanny("Kartik Movers", "Loading & Unloading", "6 years experience", 4.4, 2800),
        Nanny("Radhika Packers", "Household Transport", "8 years experience", 4.7, 3200),
        Nanny("SafeShift Co.", "Premium Packing Services", "5 years experience", 4.3, 3100),
        Nanny("City Express Movers", "Home & Office Shifting", "9 years experience", 4.5, 3600),
        Nanny("Global Trans", "Furniture Relocation", "7 years experience", 4.6, 3300),
        Nanny("Secure Handlers", "Fragile Goods Specialist", "6 years experience", 4.4, 2950),
        Nanny("Reliable Cargo", "All India Shifting", "10 years experience", 4.9, 3900),
        Nanny("Neha Movers", "Budget Shifting Services", "4 years experience", 4.2, 2700)
    )
}

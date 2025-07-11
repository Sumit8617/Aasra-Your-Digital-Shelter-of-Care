package com.example.babycare

class Blood_Test : BaseCareActivity() {
    override val activityTitle = "Blood Test"
    override val subtitle = "Book at-home diagnostic blood tests"
    override val providerList = listOf(
        Nanny("Labcorp Diagnostic", "Home Sample Collection", "Available Daily", 4.4, 500),
        Nanny("PathCare Services", "Certified Lab Technicians", "3 years experience", 4.5, 600),
        Nanny("Dr. Lal PathLabs", "NABL Accredited Lab", "5 years experience", 4.6, 700),
        Nanny("Thyrocare Home Lab", "Fasting Blood Tests", "Available Daily", 4.3, 550),
        Nanny("Healthians", "Full Body Checkups", "7 years experience", 4.7, 800),
        Nanny("Metropolis Labs", "Rapid Test Reports", "6 years experience", 4.5, 650),
        Nanny("Redcliffe Labs", "Expert Sample Collection", "5 years experience", 4.4, 600),
        Nanny("1mg Diagnostics", "Book via App", "Available All Week", 4.2, 500),
        Nanny("SRL Diagnostics", "Wide Test Coverage", "10 years experience", 4.8, 750),
        Nanny("Max Lab", "Covid & Blood Panels", "4 years experience", 4.3, 580)
    )
}

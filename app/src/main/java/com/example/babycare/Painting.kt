package com.example.babycare

class Painting : BaseCareActivity() {
    override val activityTitle = "Painting Service"
    override val subtitle = "Professional painting for home interiors & exteriors"
    override val providerList = listOf(
        Nanny("Rajiv Sharma", "Interior Painter", "10 years experience", 4.7, 1200),
        Nanny("Sonal Gupta", "Home Decor Painter", "7 years experience", 4.6, 1000),
        Nanny("Deepak Yadav", "Residential Painter", "5 years experience", 4.4, 900),
        Nanny("Meena Rathi", "Wall Texture Specialist", "8 years experience", 4.8, 1300),
        Nanny("Nikhil Verma", "Modern Art Painter", "6 years experience", 4.5, 950),
        Nanny("Rashmi Jha", "Interior Designer Painter", "4 years experience", 4.3, 850),
        Nanny("Rohan Khurana", "Commercial Painting", "9 years experience", 4.7, 1100),
        Nanny("Neha Pandey", "Accent Wall Specialist", "5 years experience", 4.4, 980),
        Nanny("Sunil Mishra", "Exterior Paint Expert", "10 years experience", 4.9, 1400),
        Nanny("Shalini Kaul", "Children Room Painting", "3 years experience", 4.2, 790)
    )
}

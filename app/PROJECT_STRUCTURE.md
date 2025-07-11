# 🗂️ Aasra App – Project Structure & File Overview

This document describes the core functionality of each Kotlin file used in the Aasra  Android caregiving app.

---

## 🔐 Authentication & Onboarding

- **SignUpActivity.kt** – Handles user registration, Firebase user creation, and redirection to role selection.
- **SplashActivity.kt** – Entry point of the app; checks login state and navigates accordingly.
- **RoleSelectionActivity.kt** – Lets the user choose between Care Seeker and Care Provider roles after signing up.

---

## 🏠 Core Activities

- **Dashboard.kt** – Main dashboard screen showing care categories for both care seekers and care providers.
- **MainActivity.kt** – May serve as a container for fragments or initial navigation setup.
- **SettingsFragment.kt** – Contains app preferences, logout, account settings, etc.

---

## 🧑‍⚕️ Care Categories (Provider Listings)

### 👶 Child Care
- **Child_Care_Activity.kt** – Displays child care subtypes (e.g., Nanny, Nutrition).
- **ChildCareAdapter.kt** – Adapter to display filtered caregivers under child care.

### 👵 Elder Care
- **Elder_Care.kt** – Displays elder care subtypes (e.g., Chronic Care, Elder Nutrition).
- **ElderCareAdapter.kt** – Adapter for elder care provider listings.

### 🏥 Medical Care
- **Medical_Care.kt** – Displays medical care subtypes (Doctor, PT Pediatric, etc.).
- **MedicalCareAdapter.kt** – Adapter for displaying medical caregivers.

### 🧹 Home Services
- **HomeServicesActivity.kt** – Shows home service subtypes (e.g., AC Repair, Cleaning).
- **HomeServiceAdapter.kt** – Adapter for filtered home service providers.

---

## 📄 Care Subtype Activities

Each file below represents a specialized care type listing or registration page:
- **Nanny.kt**, **Nanny_Care.kt**, **NannyAdapter.kt** – Manages Nanny care listing and filtering.
- **Physiotherapy.kt**, **Doctor.kt**, **PT_Pediatric_Care.kt** – Represent specific medical care types.
- **AC_Repair.kt**, **Cleaning.kt**, **Electrician.kt**, **Plumbing.kt**, **Painting.kt**, **Home_Shifting.kt** – Represent various home services.
- **Chronic_Disease_Care.kt**, **Elder_Nutrition.kt** – Specific elder care services.

---

## 📋 Booking System

- **Booking.kt** – Booking model or form handler.
- **BookBottomSheet.kt** – Bottom sheet UI for booking selection (date, time, duration).
- **BookingAdapter.kt** – Adapter to list current bookings.
- **BookingHistoryActivity.kt** – Shows booking history to the user.
- **MyBookingsActivity.kt** – Displays all user bookings in a categorized format.

---

## 👤 User Profile & Support

- **ProfileActivity.kt** – Manages user profile (view/edit name, phone, photo, etc.).
- **HelpSupportActivity.kt** – Displays FAQs and support options including WhatsApp integration.
- **FeedbackActivity.kt** – Collects user feedback and ratings for the platform or providers.

---

## 👨‍⚕️ Provider-Specific

- **ProviderDashboard.kt** – Dashboard for caregivers showing their profile, bookings, etc.
- **ProviderModel.kt** – Firebase data model for storing care provider details.

---

## 🍽️ Health & Nutrition

- **Child_Nutrition.kt**, **Post_Surgical_Care.kt**, **Beauty_Parlour.kt**, **Salon.kt**, **Blood_Test.kt** – Supportive health and lifestyle services for users.


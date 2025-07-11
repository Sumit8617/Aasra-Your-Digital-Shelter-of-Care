# 📱 Aasra – Android Caregiving App Documentation

---

## 📌 App Overview

**Aasra** is an Android-based caregiving platform designed to connect care seekers with verified care providers. The app supports four major caregiving categories: **Medical Care**, **Home Services**, **Elder Care**, and **Child Care**.

---

## 🚀 App User Flow

### 1. 🔐 **Login Page**
- **First screen** on app launch.
- **Two options**:
    - Existing users can log in using email/password or Google.
    - New users can click **“Don’t have an account? Sign up”**.

### 2. 📝 **Sign-Up Page**
- Offers **two registration methods**:
    1. **Google Sign-In**
    2. **Form-based registration**:
        - Name
        - Email
        - Password
        - Confirm Password
- Upon successful registration, navigates to the **Role Selection** screen.

### 3. 👥 **Role Selection**
- User selects their role:
    - **Care Seeker**
    - **Care Provider**
- Redirects to respective **Dashboards** based on selection.

---

## 🧭 Dashboard & Navigation

### 4. 🧑‍🦼 **Care Seeker Dashboard**
- **Top**: Geolocation icon – detects and shows current location.
- **Middle**: Four caregiving category cards:
    - **Medical Care**
    - **Home Services**
    - **Elder Care**
    - **Child Care**
- **Bottom Navigation Bar**:
    - **Home**
    - **Profile**
    - **Booking**
- **Top-Right Hamburger Menu**:
    - Duplicate access to caregiving categories for quick navigation.

#### 📂 Category Navigation
When a care seeker clicks on a category, it opens subcategories:

##### 🏥 Medical Care
- Doctors
- Blood Test Professionals
- Post-Surgical Care Providers
- Chronic Disease Care Providers

##### 🏠 Home Services
- AC Repair, Plumbing, and Electronics Technicians
- Cleaning and Painting Staff
- Appliances and Shifting Experts
- Salon and Beauty Professionals

##### 👵 Elder Care
- Physiotherapists
- In-Home Care Providers
- Palliative Care Specialists
- Elder Nutrition Experts

##### 👶 Child Care
- Nannies
- Pediatric Physiotherapists (PT Pediatric)
- Daycare Professionals
- Child Nutritionists

---

## 🔍 Provider Listings

### 5. 👨‍⚕️ Filtered Listings Page
- Shows a **list of care providers** for the selected sub-category.
- Includes:
    - 🔎 **Search bar** (filter by name)
    - ⚙️ **Filter icon** (filter by gender)
    - 📅 **“Book Now” button** for each provider

---

## 📆 Booking Flow

### 6. 🧾 Booking a Provider
- Clicking “Book Now” opens a **Bottom Sheet Popup**:
    - Select **Date**
    - Select **Time**
    - Choose **Number of Days**
- Click **“Book”** to proceed.
- Shows **“Confirm Booking” Popup**.
- On confirming, the booking appears in:
    - 📄 **Booking History** or
    - 📆 **Booking Section** in Dashboard

---

## 🧑‍⚕️ Care Provider Flow

### 7. 🧍 Care Provider Registration
- After choosing "Care Provider" role, a form opens:
    - Fills required details based on category (e.g., name, care type, experience)
- After submission:
    - Redirected to **Provider Dashboard**

### 8. 🏠 Provider Dashboard
- Options:
    - 📋 **View Bookings**
    - 👤 **View Profile**
    - 🗣️ **Feedback Section**

---

## 💬 Additional Features

### 9. 👤 Profile Management
- Edit name, phone number, address, and profile photo
- Upload picture from storage
- Delete or logout from account

### 10. 💬 Help & Support
- Accessible from dashboard
- Includes:
    - **Expandable FAQ Section**
    - **WhatsApp Live Chat Support**

### 11. 📩 Feedback
- Seeker can leave feedback for the platform or provider
- Provider can view feedback given by seekers

---

## 🔔 Real-Time Notifications (Upcoming/Implemented)
- Care Providers get notified when a seeker books them.
- Booking details reflect on provider’s booking page.

---

## 📁 File Structure Reference
Refer to [`PROJECT_STRUCTURE.md`](PROJECT_STRUCTURE.md) for file-wise code descriptions.

---

## 🧠 Future Scope

- 💳 Online payments
- 🗺️ Map-based caregiver discovery
- 🔔 Push notifications
- 🌐 Language localization

---


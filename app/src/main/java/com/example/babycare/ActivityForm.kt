package com.example.babycare

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ActivityForm : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var experienceEditText: EditText
    private lateinit var careTypeSpinner: Spinner
    private lateinit var subTypeSpinner: Spinner
    private lateinit var genderTextView: TextView
    private lateinit var submitButton: Button

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        // Initialize views
        nameEditText = findViewById(R.id.etName)
        phoneEditText = findViewById(R.id.etPhone)
        emailEditText = findViewById(R.id.etEmail)
        experienceEditText = findViewById(R.id.etExperience)
        careTypeSpinner = findViewById(R.id.spinnerCareType)
        subTypeSpinner = findViewById(R.id.spinnerSubType)
        genderTextView = findViewById(R.id.tvGender)
        submitButton = findViewById(R.id.btnSubmit)

        // Spinner data setup
        val careTypes = listOf("ChildCare", "ElderCare", "MedicalCare", "HomeService")
        val childCareSubTypes = listOf("Nanny Care", "PT Pediatric", "Day Care", "Child Nutrition")
        val elderCareSubTypes = listOf("In-Home Care", "Physiotherapy", "Palliative Care", "Elder Nutrition")
        val medicalCareSubTypes = listOf("Doctor", "Blood Test", "Post-Surgical Care", "Chronic Disease Care")
        val homeServiceSubTypes = listOf("AC Repair", "Painting", "Cleaning", "Appliances", "Beauty", "Plumbing", "Electronics", "Shifting", "Salon")

        careTypeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, careTypes)

        // Update subType spinner based on selected care type
        careTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedType = careTypes[position]
                val subTypeList = when (selectedType) {
                    "ChildCare" -> childCareSubTypes
                    "ElderCare" -> elderCareSubTypes
                    "MedicalCare" -> medicalCareSubTypes
                    "HomeService" -> homeServiceSubTypes
                    else -> listOf()
                }
                subTypeSpinner.adapter = ArrayAdapter(this@ActivityForm, android.R.layout.simple_spinner_dropdown_item, subTypeList)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // 👤 Gender selection logic
        genderTextView.setOnClickListener {
            val genderOptions = arrayOf("Male", "Female", "Other")
            AlertDialog.Builder(this)
                .setTitle("Select Gender")
                .setItems(genderOptions) { _, which ->
                    genderTextView.text = genderOptions[which]
                }
                .show()
        }

        // Submit button logic
        submitButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val experience = experienceEditText.text.toString().trim()
            val mainCategory = careTypeSpinner.selectedItem.toString()
            val careType = subTypeSpinner.selectedItem.toString()
            val gender = genderTextView.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || experience.isEmpty() || gender == "Select Gender") {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val providerData = hashMapOf(
                "name" to name,
                "phone" to phone,
                "email" to email,
                "experience" to experience,
                "mainCategory" to mainCategory,
                "careType" to careType,
                "gender" to gender
            )

            firestore.collection(mainCategory)
                .add(providerData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile submitted successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ProviderDashboard::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to submit: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

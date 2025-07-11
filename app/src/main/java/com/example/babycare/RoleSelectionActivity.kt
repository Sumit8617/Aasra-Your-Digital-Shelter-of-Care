package com.example.babycare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RoleSelectionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var customerCard: CardView
    private lateinit var providerCard: CardView
    private lateinit var careSeekerCheck: ImageView
    private lateinit var careProviderCheck: ImageView
    private lateinit var backButton: ImageView

    // Realtime Database URL
    private val databaseUrl =
        "https://login-example-22994-default-rtdb.asia-southeast1.firebasedatabase.app/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selection)

        auth = FirebaseAuth.getInstance()

        // Initialize views
        customerCard = findViewById(R.id.careSeekerCard)
        providerCard = findViewById(R.id.careProviderCard)
        careSeekerCheck = findViewById(R.id.careSeekerCheck)
        careProviderCheck = findViewById(R.id.careProviderCheck)
        backButton = findViewById(R.id.backButton)

        // Care Seeker Selection
        customerCard.setOnClickListener {
            resetSelection()
            customerCard.setCardBackgroundColor(getColor(R.color.teal_200))
            careSeekerCheck.visibility = View.VISIBLE
            saveUserRoleAndRedirect("care_seeker")
        }

        // Care Provider Selection
        providerCard.setOnClickListener {
            resetSelection()
            providerCard.setCardBackgroundColor(getColor(R.color.teal_200))
            careProviderCheck.visibility = View.VISIBLE
            saveUserRoleAndRedirect("care_provider")
        }

        // Back button action
        backButton.setOnClickListener { finish() }
    }

    // Reset visual selections
    private fun resetSelection() {
        customerCard.setCardBackgroundColor(getColor(android.R.color.white))
        providerCard.setCardBackgroundColor(getColor(android.R.color.white))
        careSeekerCheck.visibility = View.GONE
        careProviderCheck.visibility = View.GONE
    }

    // Save role in Realtime DB and redirect to respective screen
    private fun saveUserRoleAndRedirect(role: String) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RoleSelection", "Saving role: $role for user: $uid")

        // Disable buttons to prevent multiple taps
        customerCard.isEnabled = false
        providerCard.isEnabled = false

        val userRef = FirebaseDatabase
            .getInstance(databaseUrl)
            .getReference("users/$uid")

        userRef.child("role").setValue(role)
            .addOnSuccessListener {
                Log.d("RoleSelection", "Role saved successfully!")
                Toast.makeText(this, "Role selected: $role", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    // Redirect based on role
                    if (role == "care_provider") {
                        startActivity(Intent(this, ActivityForm::class.java)) // Provider form
                    } else {
                        startActivity(Intent(this, Dashboard::class.java))    // Normal dashboard
                    }
                    finish()
                }, 1000)
            }
            .addOnFailureListener {
                Log.e("RoleSelection", "Failed to save role: ${it.message}")
                Toast.makeText(this, "Failed to save role: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

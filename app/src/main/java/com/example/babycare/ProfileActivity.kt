package com.example.babycare

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.babycare.databinding.ActivityProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private val databaseUrl = "https://login-example-22994-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        val uid = user?.uid ?: return finishWithError("User not logged in")

        databaseRef = FirebaseDatabase.getInstance(databaseUrl).getReference("users/$uid")

        // Check if user came to edit directly
        isEditMode = intent.getBooleanExtra("edit_mode", false)
        setFieldsEnabled(isEditMode)
        binding.editButton.text = if (isEditMode) "Cancel" else "Edit"

        // Load Google Account Info
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val nameFromGoogle = account?.displayName
        val emailFromGoogle = account?.email
        val photoUri = account?.photoUrl

        // Load profile photo
        if (photoUri != null) {
            Glide.with(this)
                .load(photoUri)
                .transform(CircleCrop())
                .into(binding.profileImageView)
        }

        // Load from Firebase (fallback to Google data if empty)
        databaseRef.get().addOnSuccessListener { snapshot ->
            val name = snapshot.child("name").value?.toString() ?: nameFromGoogle ?: ""
            val phone = snapshot.child("phone").value?.toString() ?: ""
            val address = snapshot.child("address").value?.toString() ?: ""

            // Get email from FirebaseAuth directly
            val email = user.email ?: emailFromGoogle ?: ""

            // Set values to fields
            binding.nameEditText.setText(name)
            binding.phoneEditText.setText(phone)
            binding.addressEditText.setText(address)
            binding.emailEditText.setText(email)

            // Save name/email to Firebase if not saved already
            if (!snapshot.exists()) {
                val profileMap = mapOf(
                    "name" to name,
                    "email" to email
                )
                databaseRef.setValue(profileMap)
            }
        }

        // Edit/Cancel Toggle
        binding.editButton.setOnClickListener {
            isEditMode = !isEditMode
            setFieldsEnabled(isEditMode)
            binding.editButton.text = if (isEditMode) "Cancel" else "Edit"
        }

        // Save Button
        binding.saveButton.setOnClickListener {
            val updates = mapOf(
                "name" to binding.nameEditText.text.toString(),
                "phone" to binding.phoneEditText.text.toString(),
                "address" to binding.addressEditText.text.toString()
            )

            databaseRef.updateChildren(updates).addOnSuccessListener {
                Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                setFieldsEnabled(false)
                binding.editButton.text = "Edit"
                isEditMode = false
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to update: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // Back Button
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }
    }

    private fun setFieldsEnabled(enabled: Boolean) {
        binding.nameEditText.isEnabled = enabled
        binding.phoneEditText.isEnabled = enabled
        binding.addressEditText.isEnabled = enabled
        binding.emailEditText.isEnabled = false // Email is always read-only
    }

    private fun finishWithError(message: String): Nothing {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
        throw IllegalStateException(message)
    }
}

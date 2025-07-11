package com.example.babycare

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.babycare.databinding.ActivityFeedbackBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FeedbackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedbackBinding
    private lateinit var auth: FirebaseAuth
    private val databaseUrl = "https://login-example-22994-default-rtdb.asia-southeast1.firebasedatabase.app/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.submitFeedbackButton.setOnClickListener {
            val feedbackText = binding.feedbackEditText.text.toString().trim()

            if (feedbackText.isEmpty()) {
                Toast.makeText(this, "Please enter your feedback", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = auth.currentUser?.uid ?: return@setOnClickListener
            val feedbackRef = FirebaseDatabase.getInstance(databaseUrl).getReference("feedbacks").push()

            val feedbackData = mapOf(
                "userId" to userId,
                "feedback" to feedbackText,
                "timestamp" to System.currentTimeMillis()
            )

            feedbackRef.setValue(feedbackData).addOnSuccessListener {
                showSuccessDialog()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to send feedback", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Thank you!")
            .setMessage("Your feedback has been submitted successfully.")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                binding.feedbackEditText.text.clear()
            }
            .show()
    }
}

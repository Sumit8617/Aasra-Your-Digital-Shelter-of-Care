package com.example.babycare

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class HelpSupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_support)

        val helpButton = findViewById<Button>(R.id.btnHelpSupport)

        helpButton.setOnClickListener {
            showSupportDialog()
        }
    }

    private fun showSupportDialog() {
        // Inflate the custom dialog layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_support_contact, null)

        // Set phone and email values
        val phoneText = dialogView.findViewById<TextView>(R.id.textPhone)
        val emailText = dialogView.findViewById<TextView>(R.id.textEmail)

        phoneText.text = "📞 Phone: +91-9876543210"
        emailText.text = "📧 Email: support@aasra.com"

        // Build and show the alert dialog
        AlertDialog.Builder(this)
            .setTitle("Contact Support")
            .setView(dialogView)
            .setPositiveButton("Close", null)
            .show()
    }
}

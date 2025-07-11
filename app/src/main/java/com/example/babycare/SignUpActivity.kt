package com.example.babycare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.babycare.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.backArrow.setOnClickListener { finish() }

        binding.loginText.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Google Sign-Up
        binding.googleSignupIcon.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // Email/Password Sign-Up
        binding.continueButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                        val userMap = mapOf(
                            "name" to name,
                            "email" to email
                        )

                        FirebaseDatabase.getInstance().getReference("users").child(uid)
                            .setValue(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, RoleSelectionActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Sign Up Successful ", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    val uid = currentUser?.uid ?: return@addOnCompleteListener
                    val name = currentUser.displayName ?: ""
                    val email = currentUser.email ?: ""

                    val userMap = mapOf(
                        "name" to name,
                        "email" to email
                    )

                    FirebaseDatabase.getInstance().getReference("users").child(uid)
                        .setValue(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Google Sign-Up Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, RoleSelectionActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Firebase auth failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}

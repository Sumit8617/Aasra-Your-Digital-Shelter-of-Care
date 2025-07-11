package com.example.babycare

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.babycare.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val databaseUrl = "https://login-example-22994-default-rtdb.asia-southeast1.firebasedatabase.app/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // ✅ If already logged in
        if (auth.currentUser != null) {
            checkUserRoleAndRedirect()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.loginButton.setOnClickListener {
            val email = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        checkUserRoleAndRedirect()
                    } else {
                        Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.signupText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.googleLoginIcon.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
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

                    val userMap = mapOf("name" to name, "email" to email)

                    FirebaseDatabase.getInstance(databaseUrl)
                        .getReference("users").child(uid)
                        .updateChildren(userMap)
                        .addOnSuccessListener {
                            checkUserRoleAndRedirect()
                        }
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun checkUserRoleAndRedirect() {
        val uid = auth.currentUser?.uid ?: return
        val ref = FirebaseDatabase.getInstance(databaseUrl).getReference("users/$uid/role")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val role = snapshot.getValue(String::class.java)
                if (role != null) {
                    // ✅ Already selected role → go to Dashboard
                    startActivity(Intent(this@MainActivity, Dashboard::class.java))
                } else {
                    // 🔄 No role yet → go to role selection
                    startActivity(Intent(this@MainActivity, RoleSelectionActivity::class.java))
                }
                finish()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Database error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun linkEmailPassword(email: String, password: String) {
        val user = auth.currentUser
        val credential = EmailAuthProvider.getCredential(email, password)
        user?.linkWithCredential(credential)?.addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Linked successfully", Toast.LENGTH_LONG).show()
            }
        }
    }
}

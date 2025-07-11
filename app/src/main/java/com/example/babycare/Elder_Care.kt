package com.example.babycare

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class Elder_Care : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elder_care)
        title = "Elder Care"

        // 🔐 Firebase Auth & Google Sign-In
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 🧭 Toolbar & Drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Elder Care"

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 👤 Load Profile Info
        val profileImage = findViewById<ImageView>(R.id.top_profile_image)
        val profileName = findViewById<TextView>(R.id.top_user_name)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        val firebaseUser = auth.currentUser

        val name = account?.displayName ?: firebaseUser?.displayName
        val photoUri = account?.photoUrl ?: firebaseUser?.photoUrl

        profileName.text = name ?: "User"
        if (photoUri != null) {
            Glide.with(this)
                .load(photoUri)
                .transform(CircleCrop())
                .into(profileImage)
        }

        // ✅ Subtype Navigation → FilteredCareListActivity
        findViewById<ImageView>(R.id.inhomeCare).setOnClickListener {
            openFilteredCareList("In-Home Care")
        }

        findViewById<ImageView>(R.id.physiotherapy).setOnClickListener {
            openFilteredCareList("Physiotherapy")
        }

        findViewById<ImageView>(R.id.palliative).setOnClickListener {
            openFilteredCareList("Palliative Care")
        }

        findViewById<ImageView>(R.id.ElderNutrition).setOnClickListener {
            openFilteredCareList("Elder Nutrition")
        }

        // 🔻 Bottom Navigation
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> startActivity(Intent(this, Dashboard::class.java))
                R.id.nav_profile -> startActivity(Intent(this, ProfileActivity::class.java))
                R.id.nav_booking -> startActivity(Intent(this, BookingHistoryActivity::class.java))
            }
            true
        }

        // ☰ Navigation Drawer
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            drawerLayout.closeDrawers()
            when (item.itemId) {
                R.id.nav_home -> startActivity(Intent(this, Dashboard::class.java))
                R.id.nav_child_care -> startActivity(Intent(this, Child_Care_Activity::class.java))
                R.id.nav_medical_care -> startActivity(Intent(this, Medical_Care::class.java))
                R.id.nav_elder_care -> Toast.makeText(this, "Already in Elder Care", Toast.LENGTH_SHORT).show()
                R.id.nav_house_keeping -> startActivity(Intent(this, HomeServicesActivity::class.java))
                R.id.nav_profile -> startActivity(Intent(this, ProfileActivity::class.java))
                R.id.nav_help -> Toast.makeText(this, "Help & Support clicked", Toast.LENGTH_SHORT).show()
                R.id.nav_feedback -> Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> {
                    auth.signOut()
                    googleSignInClient.signOut().addOnCompleteListener {
                        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                }
            }
            true
        }

        // 🔙 Back button handling
        onBackPressedDispatcher.addCallback(this) {
            when {
                drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
                drawerLayout.isDrawerOpen(GravityCompat.END) -> drawerLayout.closeDrawer(GravityCompat.END)
                else -> finish()
            }
        }
    }

    private fun openFilteredCareList(subType: String) {
        val intent = Intent(this, FilteredCareListActivity::class.java)
        intent.putExtra("mainCategory", "ElderCare")  // Firestore Collection
        intent.putExtra("careType", subType)          // Field to filter by
        startActivity(intent)
    }
}

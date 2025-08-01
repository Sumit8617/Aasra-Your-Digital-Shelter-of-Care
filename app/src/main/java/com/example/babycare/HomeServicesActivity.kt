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

class HomeServicesActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_services)
        title = "Home Services"

        // 🔐 Auth setup
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 🧭 Toolbar + Drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home Services"
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 👤 Profile Image + Name
        val profileImage = findViewById<ImageView>(R.id.top_profile_image)
        val profileName = findViewById<TextView>(R.id.top_user_name)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val firebaseUser = auth.currentUser
        val name = account?.displayName ?: firebaseUser?.displayName
        val photoUri = account?.photoUrl ?: firebaseUser?.photoUrl

        if (name != null) profileName.text = name
        if (photoUri != null) {
            Glide.with(this)
                .load(photoUri)
                .transform(CircleCrop())
                .into(profileImage)
        }

        // 🔗 Click Listeners
        findViewById<ImageView>(R.id.acRepair).setOnClickListener {
            openFilteredCareList("AC Repair")
        }
        findViewById<ImageView>(R.id.painting).setOnClickListener {
            openFilteredCareList("Painting")
        }
        findViewById<ImageView>(R.id.cleaning).setOnClickListener {
            openFilteredCareList("Cleaning")
        }
        findViewById<ImageView>(R.id.appliance).setOnClickListener {
            openFilteredCareList("Appliances")
        }
        findViewById<ImageView>(R.id.beauty).setOnClickListener {
            openFilteredCareList("Beauty")
        }
        findViewById<ImageView>(R.id.plumbing).setOnClickListener {
            openFilteredCareList("Plumbing")
        }
        findViewById<ImageView>(R.id.electronic).setOnClickListener {
            openFilteredCareList("Electronics")
        }
        findViewById<ImageView>(R.id.shift).setOnClickListener {
            openFilteredCareList("Shifting")
        }
        findViewById<ImageView>(R.id.salon).setOnClickListener {
            openFilteredCareList("Salon")
        }

        // 🔽 Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> startActivity(Intent(this, Dashboard::class.java))
                R.id.nav_profile -> startActivity(Intent(this, ProfileActivity::class.java))
                R.id.nav_booking -> startActivity(Intent(this, BookingHistoryActivity::class.java))
            }
            true
        }

        // ☰ Navigation Drawer Actions
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            drawerLayout.closeDrawers()
            when (item.itemId) {
                R.id.nav_home -> startActivity(Intent(this, Dashboard::class.java))
                R.id.nav_child_care -> startActivity(Intent(this, Child_Care_Activity::class.java))
                R.id.nav_medical_care -> startActivity(Intent(this, Medical_Care::class.java))
                R.id.nav_elder_care -> startActivity(Intent(this, Elder_Care::class.java))
                R.id.nav_house_keeping -> Toast.makeText(this, "Already in Home Services", Toast.LENGTH_SHORT).show()
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

        // 🔙 Back Press with Drawer
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
        intent.putExtra("careType", subType)
        intent.putExtra("mainCategory", "HomeService")
        startActivity(intent)
    }
}

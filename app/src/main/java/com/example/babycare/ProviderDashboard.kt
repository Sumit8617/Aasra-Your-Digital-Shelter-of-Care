package com.example.babycare

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ProviderDashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var profileImage: ImageView
    private lateinit var userNameText: TextView
    private lateinit var greetingText: TextView
    private lateinit var currentLocationText: TextView
    private lateinit var btnLocation: ImageButton

    private lateinit var btnViewBookings: Button
    private lateinit var btnEditProfile: Button

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_providerdashboard)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Setup Toolbar and Drawer
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        // Bottom Navigation
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener {
            // Future handling for bottom nav
            true
        }

        // Views
        profileImage = findViewById(R.id.top_profile_image)
        userNameText = findViewById(R.id.top_user_name)
        greetingText = findViewById(R.id.top_user_greeting)
        currentLocationText = findViewById(R.id.current_location_text)
        btnLocation = findViewById(R.id.btn_location)

        btnViewBookings = findViewById(R.id.btnViewBookings)
        btnEditProfile = findViewById(R.id.btnEditProfile)

        // Load user data
        loadUserProfile()

        // Location button
        btnLocation.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                getLastLocation()
            }
        }

        // Edit Profile Button
        btnEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("edit_mode", true)
            startActivity(intent)
        }

        // Optional: View Bookings action
        btnViewBookings.setOnClickListener {
            startActivity(Intent(this, BookingHistoryActivity::class.java))
        }
    }

    private fun loadUserProfile() {
        val user: FirebaseUser? = auth.currentUser
        if (user != null) {
            userNameText.text = user.displayName ?: "User"
            greetingText.text = "Hello"
            val photoUrl = user.photoUrl
            if (photoUrl != null) {
                Glide.with(this).load(photoUrl).into(profileImage)
            } else {
                profileImage.setImageResource(R.drawable.acc1)
            }
        } else {
            Toast.makeText(this, "User not signed in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude
                val address = getAddressFromLocation(lat, lon)
                currentLocationText.text = address
                Toast.makeText(this, "Location fetched!", Toast.LENGTH_SHORT).show()
            } else {
                currentLocationText.text = "Location not available"
            }
        }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        return try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1) ?: emptyList()
            if (addresses.isNotEmpty()) {
                addresses[0].getAddressLine(0) ?: "No address found"
            } else {
                "No address found"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error getting location"
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                startActivity(Intent(this, Dashboard::class.java))
                finish()
            }
            R.id.nav_help -> {
                startActivity(Intent(this, HelpSupportActivity::class.java))
            }
            R.id.nav_logout -> {
                auth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
            .closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

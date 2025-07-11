package com.example.babycare

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView

class Child_Nutrition : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: NannyAdapter

    private val nutritionistList = listOf(
        Nanny("Dr. Sneha Kapoor", "Pediatric Nutritionist", "6 years experience",
            4.9, 900),
        Nanny("Meenal Joshi", "Child Diet Consultant", "5 years experience", 4.7, 850),
        Nanny("Amit Rawat", "Certified Nutritionist", "3 years experience", 4.3, 700),
        Nanny("Priya Sharma", "M.Sc in Food & Nutrition", "4 years experience", 4.5, 750),
        Nanny("Neelam Das", "Dietician", "7 years experience", 4.8, 950),
        Nanny("Divya Nair", "Child Obesity Specialist", "6 years experience", 4.6, 800),

    )


    private var filteredRateRange: IntRange? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_nutrition)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        val recyclerView = findViewById<RecyclerView>(R.id.nutritionistRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Show bottom sheet instead of toast
        adapter = NannyAdapter(nutritionistList, supportFragmentManager)
        recyclerView.adapter = adapter

        val searchBox = findViewById<EditText>(R.id.searchBox)
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        findViewById<ImageButton>(R.id.filterButton).setOnClickListener {
            showFilterBottomSheet()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        onBackPressedDispatcher.addCallback(this) {
            when {
                drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
                drawerLayout.isDrawerOpen(GravityCompat.END) -> drawerLayout.closeDrawer(GravityCompat.END)
                else -> finish()
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> showToast("Dashboard clicked")
                R.id.nav_child_care -> showToast("Child Care clicked")
                R.id.nav_medical_care -> showToast("Medical Care clicked")
                R.id.nav_elder_care -> showToast("Elder Care clicked")
                R.id.nav_house_keeping -> showToast("Housekeeping clicked")
                R.id.nav_logout -> showToast("Logout clicked")
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun showFilterBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.filter_botttom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)

        val rateGroup = dialogView.findViewById<RadioGroup>(R.id.rateGroup)
        val applyButton = dialogView.findViewById<Button>(R.id.applyFilterBtn)
        val clearButton = dialogView.findViewById<Button>(R.id.clearFilterBtn)

        applyButton.setOnClickListener {
            filteredRateRange = when (rateGroup.checkedRadioButtonId) {
                R.id.rate1 -> 500..800
                R.id.rate2 -> 800..1000
                R.id.rate3 -> 1000..1500
                else -> null
            }
            val currentSearchText = findViewById<EditText>(R.id.searchBox).text.toString()
            applyFilters(currentSearchText)
            dialog.dismiss()
        }

        clearButton.setOnClickListener {
            filteredRateRange = null
            findViewById<EditText>(R.id.searchBox).setText("")
            adapter.updateData(nutritionistList)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun applyFilters(searchQuery: String) {
        val filteredList = nutritionistList.filter { nutritionist ->
            val matchesSearch = nutritionist.name.contains(searchQuery, ignoreCase = true)
            val matchesRate = filteredRateRange?.contains(nutritionist.pricePerHour) ?: true
            matchesSearch && matchesRate
        }
        adapter.updateData(filteredList)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

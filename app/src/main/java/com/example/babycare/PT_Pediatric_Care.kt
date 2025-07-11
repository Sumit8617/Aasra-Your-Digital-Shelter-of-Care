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

class PT_Pediatric_Care : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: NannyAdapter


    private val pediatricList = listOf(
        Nanny("Dr. Meena Rao", "MPT Pediatric", "6 years experience", 4.8, 900),
        Nanny("Kavita Nair", "BPT + Pediatric Cert.", "4 years experience", 4.5, 750),
        Nanny("Anil Grover", "Pediatric Physio", "5 years experience", 4.3, 850),
        Nanny("Dr. Ritu Sharma", "MPT in Child Rehab", "7 years experience", 4.7, 920),
        Nanny("Siddharth Sen", "Certified Pediatric Physio", "6 years experience", 4.6, 880),
        Nanny("Priyanka Bajaj", "Neuro-Pediatric Physio", "8 years experience", 4.9, 950),
        Nanny("Amit Trivedi", "Ortho & Pediatric Specialist", "5 years experience", 4.4, 800),
        Nanny("Rashmi Iyer", "Child Mobility Expert", "6 years experience", 4.5, 870),
        Nanny("Dr. Alok Mishra", "Developmental Physio", "9 years experience", 4.8, 1000),
        Nanny("Tanvi Kapoor", "Home Pediatric Physio", "4 years experience", 4.3, 780)
    )


    private var filteredGender: String? = null
    private var filteredRateRange: IntRange? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pediatric_care)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        val recyclerView = findViewById<RecyclerView>(R.id.nannyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Show BookBottomSheet on button click
        adapter = NannyAdapter(pediatricList, supportFragmentManager)
        recyclerView.adapter = adapter

        findViewById<EditText>(R.id.searchBox).addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        findViewById<ImageButton>(R.id.filterButton).setOnClickListener {
            showFilterBottomSheet()
        }

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        onBackPressedDispatcher.addCallback(this) {
            when {
                drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
                else -> finish()
            }
        }

        navigationView.setNavigationItemSelectedListener {
            showToast("Clicked: ${it.title}")
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun showFilterBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.filter_botttom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)

        val genderGroup = dialogView.findViewById<RadioGroup>(R.id.genderGroup)
        val rateGroup = dialogView.findViewById<RadioGroup>(R.id.rateGroup)

        dialogView.findViewById<Button>(R.id.applyFilterBtn).setOnClickListener {
            filteredGender = when (genderGroup.checkedRadioButtonId) {
                R.id.maleOption -> "Male"
                R.id.femaleOption -> "Female"
                else -> null
            }

            filteredRateRange = when (rateGroup.checkedRadioButtonId) {
                R.id.rate1 -> 500..1000
                R.id.rate2 -> 1000..1500
                R.id.rate3 -> 1500..3000
                else -> null
            }

            val query = findViewById<EditText>(R.id.searchBox).text.toString()
            applyFilters(query)
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.clearFilterBtn).setOnClickListener {
            filteredGender = null
            filteredRateRange = null
            findViewById<EditText>(R.id.searchBox).setText("")
            adapter.updateData(pediatricList)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun applyFilters(searchQuery: String) {
        val filteredList = pediatricList.filter {
            it.name.contains(searchQuery, true)
                    && (filteredGender == null || it.name.contains(filteredGender!!, true))
                    && (filteredRateRange?.contains(it.pricePerHour) ?: true)
        }
        adapter.updateData(filteredList)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

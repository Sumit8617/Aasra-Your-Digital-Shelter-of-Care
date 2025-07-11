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

abstract class BaseCareActivity : AppCompatActivity() {

    abstract val activityTitle: String
    abstract val subtitle: String
    abstract val providerList: List<Nanny>

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: NannyAdapter

    private var filteredGender: String? = null
    private var filteredRateRange: IntRange? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nanny_care)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.title = activityTitle

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val recyclerView = findViewById<RecyclerView>(R.id.nannyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Updated: show BookBottomSheet on Book button click
        adapter = NannyAdapter(providerList, supportFragmentManager)
        recyclerView.adapter = adapter

        val searchBox = findViewById<EditText>(R.id.searchBox)
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        val filterButton = findViewById<ImageButton>(R.id.filterButton)
        filterButton.setOnClickListener { showFilterBottomSheet() }

        onBackPressedDispatcher.addCallback(this) {
            when {
                drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
                else -> finish()
            }
        }

        navigationView.setNavigationItemSelectedListener {
            Toast.makeText(this, "${it.title} clicked", Toast.LENGTH_SHORT).show()
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
        val applyButton = dialogView.findViewById<Button>(R.id.applyFilterBtn)
        val clearButton = dialogView.findViewById<Button>(R.id.clearFilterBtn)

        applyButton.setOnClickListener {
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
            applyFilters(findViewById<EditText>(R.id.searchBox).text.toString())
            dialog.dismiss()
        }

        clearButton.setOnClickListener {
            filteredGender = null
            filteredRateRange = null
            findViewById<EditText>(R.id.searchBox).setText("")
            adapter.updateData(providerList)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun applyFilters(searchQuery: String) {
        val filteredList = providerList.filter { nanny ->
            val matchesSearch = nanny.name.contains(searchQuery, ignoreCase = true)
            val matchesGender = filteredGender?.let {
                if (it == "Male") nanny.name.contains("Rajiv", true) || nanny.name.contains("Ankit", true)
                else !nanny.name.contains("Rajiv", true) && !nanny.name.contains("Ankit", true)
            } ?: true
            val matchesRate = filteredRateRange?.contains(nanny.pricePerHour) ?: true

            matchesSearch && matchesGender && matchesRate
        }
        adapter.updateData(filteredList)
    }
}

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

class Nanny_Care : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: NannyAdapter

    private val nannyList = listOf(
        Nanny("Aisha Sharma", "B.Sc in Child Development", "5 years experience", 4.7, 800),
        Nanny("Neha Verma", "Diploma in Early Childhood", "3 years experience", 4.3, 750),
        Nanny("Ritika Sinha", "M.A. in Child Psychology", "6 years experience", 4.9, 900),
        Nanny("Pooja Mehra", "Certified Caregiver", "4 years experience", 4.5, 850),
        Nanny("Rajiv Kumar", "B.A. in Psychology", "5 years experience", 4.4, 1000),
        Nanny("Ankit Sharma", "Diploma in Child Welfare", "2 years experience", 4.1, 1200)
    )

    private var filteredGender: String? = null
    private var filteredRateRange: IntRange? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nanny_care)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        val serviceName = intent.getStringExtra("SERVICE_NAME") ?: "Nanny Care"
        supportActionBar?.title = serviceName

        val headerTextView = findViewById<TextView>(R.id.headerText)
        headerTextView.text = serviceName

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        val recyclerView = findViewById<RecyclerView>(R.id.nannyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Pass supportFragmentManager to open BookBottomSheet on button click
        adapter = NannyAdapter(nannyList, supportFragmentManager)
        recyclerView.adapter = adapter

        val searchBox = findViewById<EditText>(R.id.searchBox)
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val filterButton = findViewById<ImageButton>(R.id.filterButton)
        filterButton.setOnClickListener {
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
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialog)
        dialog.setContentView(dialogView)

        val genderGroup = dialogView.findViewById<RadioGroup>(R.id.genderGroup)
        val rateGroup = dialogView.findViewById<RadioGroup>(R.id.rateGroup)
        val applyButton = dialogView.findViewById<Button>(R.id.applyFilterBtn)
        val clearButton = dialogView.findViewById<Button>(R.id.clearFilterBtn)

        applyButton.setOnClickListener {
            val selectedGenderId = genderGroup.checkedRadioButtonId
            val selectedRateId = rateGroup.checkedRadioButtonId

            filteredGender = when (selectedGenderId) {
                R.id.maleOption -> "Male"
                R.id.femaleOption -> "Female"
                else -> null
            }

            filteredRateRange = when (selectedRateId) {
                R.id.rate1 -> 500..1000
                R.id.rate2 -> 1000..1500
                R.id.rate3 -> 1500..3000
                else -> null
            }

            val currentSearchText = findViewById<EditText>(R.id.searchBox).text.toString()
            applyFilters(currentSearchText)

            dialog.dismiss()
        }

        clearButton.setOnClickListener {
            filteredGender = null
            filteredRateRange = null
            findViewById<EditText>(R.id.searchBox).setText("")
            adapter.updateData(nannyList)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun applyFilters(searchQuery: String) {
        val filteredList = nannyList.filter { nanny ->
            val matchesSearch = nanny.name.contains(searchQuery, ignoreCase = true)
            val matchesGender = filteredGender?.let {
                if (it == "Male") nanny.name.contains("Rajiv", ignoreCase = true) || nanny.name.contains("Ankit", ignoreCase = true)
                else nanny.name.contains("Aisha", true) || nanny.name.contains("Neha", true) ||
                        nanny.name.contains("Pooja", true) || nanny.name.contains("Ritika", true)
            } ?: true
            val matchesRate = filteredRateRange?.contains(nanny.pricePerHour) ?: true

            matchesSearch && matchesGender && matchesRate
        }

        adapter.updateData(filteredList)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

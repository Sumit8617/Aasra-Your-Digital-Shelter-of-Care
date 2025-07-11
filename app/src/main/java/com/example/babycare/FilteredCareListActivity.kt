package com.example.babycare

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.babycare.adapter.*
import com.example.babycare.model.ProviderModel
import com.google.firebase.firestore.FirebaseFirestore

class FilteredCareListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyText: TextView
    private lateinit var searchEditText: EditText
    private lateinit var btnFilter: ImageView

    private val firestore = FirebaseFirestore.getInstance()
    private var mainCategory: String = ""
    private var careType: String = ""

    private var fullProviderList = mutableListOf<ProviderModel>()
    private lateinit var currentAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_care_list)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        emptyText = findViewById(R.id.emptyText)
        searchEditText = findViewById(R.id.searchEditText)
        btnFilter = findViewById(R.id.btnFilter)

        recyclerView.layoutManager = LinearLayoutManager(this)

        mainCategory = intent.getStringExtra("mainCategory") ?: ""
        careType = intent.getStringExtra("careType") ?: ""

        if (mainCategory.isEmpty() || careType.isEmpty()) {
            Toast.makeText(this, "Invalid care category", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnFilter.setOnClickListener { showGenderFilterDialog() }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = filterList(s.toString())
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        loadProviders()
    }

    private fun showGenderFilterDialog() {
        val genderOptions = arrayOf("All", "Male", "Female", "Other")
        var selectedOption = 0

        AlertDialog.Builder(this)
            .setTitle("Filter by Gender")
            .setSingleChoiceItems(genderOptions, selectedOption) { _, which ->
                selectedOption = which
            }
            .setPositiveButton("Apply") { dialog, _ ->
                val selectedGender = genderOptions[selectedOption]
                if (selectedGender == "All") {
                    loadProviders()
                } else {
                    loadProvidersByGender(selectedGender)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun loadProviders() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyText.visibility = View.GONE

        firestore.collection(mainCategory)
            .whereEqualTo("careType", careType)
            .get()
            .addOnSuccessListener { result ->
                progressBar.visibility = View.GONE
                fullProviderList.clear()

                for (doc in result) {
                    val provider = ProviderModel(
                        name = doc.getString("name") ?: "",
                        phone = doc.getString("phone") ?: "",
                        email = doc.getString("email") ?: "",
                        experience = doc.getString("experience") ?: "",
                        gender = doc.getString("gender") ?: ""
                    )
                    fullProviderList.add(provider)
                }

                updateAdapter(fullProviderList)
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                emptyText.visibility = View.VISIBLE
                emptyText.text = "Failed to load data"
            }
    }

    private fun loadProvidersByGender(gender: String) {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyText.visibility = View.GONE

        firestore.collection(mainCategory)
            .whereEqualTo("careType", careType)
            .whereEqualTo("gender", gender)
            .get()
            .addOnSuccessListener { result ->
                progressBar.visibility = View.GONE
                val filteredList = mutableListOf<ProviderModel>()

                for (doc in result) {
                    val provider = ProviderModel(
                        name = doc.getString("name") ?: "",
                        phone = doc.getString("phone") ?: "",
                        email = doc.getString("email") ?: "",
                        experience = doc.getString("experience") ?: "",
                        gender = doc.getString("gender") ?: ""
                    )
                    filteredList.add(provider)
                }

                updateAdapter(filteredList)
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                emptyText.visibility = View.VISIBLE
                emptyText.text = "Failed to load data"
            }
    }

    private fun updateAdapter(list: List<ProviderModel>) {
        if (list.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyText.visibility = View.GONE
            currentAdapter = getAdapterForCategory(list, mainCategory)
            recyclerView.adapter = currentAdapter
        }
    }

    private fun filterList(query: String) {
        val filtered = fullProviderList.filter {
            it.name.contains(query, ignoreCase = true)
        }
        updateAdapter(filtered)
    }

    private fun getAdapterForCategory(
        providers: List<ProviderModel>,
        category: String
    ): RecyclerView.Adapter<*> {
        return when (category) {
            "ChildCare" -> ChildCareAdapter(providers, supportFragmentManager)
            "ElderCare" -> ElderCareAdapter(providers, supportFragmentManager)
            "MedicalCare" -> MedicalCareAdapter(providers, supportFragmentManager)
            "HomeService" -> HomeServiceAdapter(providers, supportFragmentManager)
            else -> ChildCareAdapter(providers, supportFragmentManager) // fallback
        }
    }
}

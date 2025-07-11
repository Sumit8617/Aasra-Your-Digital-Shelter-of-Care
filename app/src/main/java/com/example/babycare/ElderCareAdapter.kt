package com.example.babycare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.babycare.BookBottomSheet
import com.example.babycare.Nanny
import com.example.babycare.R
import com.example.babycare.model.ProviderModel

class ElderCareAdapter(
    private val providers: List<ProviderModel>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<ElderCareAdapter.ProviderViewHolder>() {

    inner class ProviderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.tvName)
        val phoneText: TextView = view.findViewById(R.id.tvPhone)
        val emailText: TextView = view.findViewById(R.id.tvEmail)
        val experienceText: TextView = view.findViewById(R.id.tvExperience)
        val bookNowBtn: Button = view.findViewById(R.id.btnBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_provider, parent, false)
        return ProviderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val provider = providers[position]

        holder.nameText.text = provider.name
        holder.phoneText.text = "Phone: ${provider.phone}"
        holder.emailText.text = "Email: ${provider.email}"
        holder.experienceText.text = "Experience: ${provider.experience} yrs"

        holder.bookNowBtn.setOnClickListener {
            val bottomSheet = BookBottomSheet.newInstance(
                Nanny(
                    name = provider.name,
                    qualification = "Elder Caregiver",
                    experience = provider.experience,
                    rating = 4.5,
                    pricePerHour = 300
                )
            )
            bottomSheet.show(fragmentManager, "ElderCareBooking")
        }
    }

    override fun getItemCount(): Int = providers.size
}

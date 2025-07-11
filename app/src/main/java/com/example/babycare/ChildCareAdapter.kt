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

class ChildCareAdapter(
    private val providers: List<ProviderModel>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<ChildCareAdapter.ProviderViewHolder>() {

    class ProviderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.tvName)
        val phoneText: TextView = itemView.findViewById(R.id.tvPhone)
        val emailText: TextView = itemView.findViewById(R.id.tvEmail)
        val experienceText: TextView = itemView.findViewById(R.id.tvExperience)
        val bookBtn: Button = itemView.findViewById(R.id.btnBook)
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

        holder.bookBtn.setOnClickListener {
            val bottomSheet = BookBottomSheet.newInstance(
                Nanny(
                    name = provider.name,
                    qualification = "Child Care Specialist",
                    experience = provider.experience,
                    rating = 4.5,
                    pricePerHour = 300
                )
            )
            bottomSheet.show(fragmentManager, "BookBottomSheet")
        }
    }

    override fun getItemCount(): Int = providers.size
}

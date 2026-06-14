package com.example.travelrecord.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelrecord.databinding.ItemTravelBinding
import com.example.travelrecord.model.Travel

class TravelAdapter(
    private var travelList: List<Travel>,
    private val onItemClick: (Travel) -> Unit,
    private val onEditClick: (Travel) -> Unit,
    private val onDeleteClick: (Travel) -> Unit
) : RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

    inner class TravelViewHolder(
        private val binding: ItemTravelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(travel: Travel) {
            binding.textPlace.text = travel.place
            binding.textDate.text = travel.visitDate

            if (!travel.photoUri.isNullOrBlank()) {
                try {
                    binding.imageTravel.setImageURI(
                        Uri.parse(travel.photoUri)
                    )
                } catch (e: Exception) {
                    binding.imageTravel.setImageResource(
                        android.R.drawable.ic_menu_gallery
                    )
                }
            } else {
                binding.imageTravel.setImageResource(
                    android.R.drawable.ic_menu_gallery
                )
            }

            // 아이템 클릭 (상세 화면)
            binding.root.setOnClickListener {
                onItemClick(travel)
            }

            // 수정 버튼 클릭
            binding.buttonEdit.setOnClickListener {
                onEditClick(travel)
            }

            // 삭제 버튼 클릭
            binding.buttonDelete.setOnClickListener {
                onDeleteClick(travel)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TravelViewHolder {
        val binding = ItemTravelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TravelViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TravelViewHolder,
        position: Int
    ) {
        holder.bind(travelList[position])
    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    fun updateData(newList: List<Travel>) {
        travelList = newList
        notifyDataSetChanged()
    }
}
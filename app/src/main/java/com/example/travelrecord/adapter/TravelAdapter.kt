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
            binding.textVisitDate.text = travel.visitDate

            if (!travel.photoUri.isNullOrBlank()) {
                try {
                    binding.imageThumbnail.setImageURI(
                        Uri.parse(travel.photoUri)
                    )
                } catch (e: Exception) {
                    binding.imageThumbnail.setImageResource(
                        android.R.drawable.ic_menu_gallery
                    )
                }
            } else {
                binding.imageThumbnail.setImageResource(
                    android.R.drawable.ic_menu_gallery
                )
            }

            // 짧게 누르면 상세 화면
            binding.root.setOnClickListener {
                onItemClick(travel)
            }

            // 길게 누르면 컨텍스트 메뉴 생성
            binding.root.setOnCreateContextMenuListener { menu, _, _ ->
                menu.setHeaderTitle(travel.place)

                menu.add("수정").setOnMenuItemClickListener {
                    onEditClick(travel)
                    true
                }

                menu.add("삭제").setOnMenuItemClickListener {
                    onDeleteClick(travel)
                    true
                }
            }

            // 길게 누르면 ContextMenu가 열리도록 설정
            binding.root.setOnLongClickListener {
                binding.root.showContextMenu()
                true
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
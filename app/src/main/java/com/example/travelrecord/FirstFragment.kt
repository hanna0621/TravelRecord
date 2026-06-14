package com.example.travelrecord

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelrecord.activity.AddTravelActivity
import com.example.travelrecord.activity.DetailActivity
import com.example.travelrecord.activity.EditTravelActivity
import com.example.travelrecord.activity.MapActivity
import com.example.travelrecord.adapter.TravelAdapter
import com.example.travelrecord.database.DBHelper
import com.example.travelrecord.databinding.FragmentFirstBinding
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DBHelper
    private lateinit var travelAdapter: TravelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBHelper(requireContext())

        requireActivity().supportFragmentManager.setFragmentResultListener(
            "sort_request",
            viewLifecycleOwner
        ) { _, bundle ->

            val sortType = bundle.getString("sort_type")

            val travelList = if (sortType == "oldest") {
                dbHelper.getAllTravelsOldest()
            } else {
                dbHelper.getAllTravels()
            }

            travelAdapter.updateData(travelList)

            if (travelList.isEmpty()) {
                binding.textEmpty.visibility = View.VISIBLE
                binding.recyclerTravel.visibility = View.GONE
            } else {
                binding.textEmpty.visibility = View.GONE
                binding.recyclerTravel.visibility = View.VISIBLE
            }
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            "refresh_request",
            viewLifecycleOwner
        ) { _, _ ->
            loadTravelList()
        }

        travelAdapter = TravelAdapter(
            travelList = emptyList(),

            onItemClick = { travel ->
                val intent = Intent(
                    requireContext(),
                    DetailActivity::class.java
                )

                intent.putExtra("travel_id", travel.no)
                startActivity(intent)
            },

            onEditClick = { travel ->
                val intent = Intent(
                    requireContext(),
                    EditTravelActivity::class.java
                )

                intent.putExtra("travel_id", travel.no)
                startActivity(intent)
            },

            onDeleteClick = { travel ->
                showDeleteDialog(travel.no)
            }
        )

        binding.recyclerTravel.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = travelAdapter
        }

        binding.buttonFirst.setOnClickListener {
            val intent = Intent(requireContext(), AddTravelActivity::class.java)
            startActivity(intent)
        }

        binding.buttonInfo.setOnClickListener {
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment
            )
        }

        binding.buttonMap.setOnClickListener {
            val intent = Intent(requireContext(), MapActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadTravelList()


    }
    private fun showDeleteDialog(travelId: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("여행 기록 삭제")
            .setMessage("정말 이 여행 기록을 삭제하시겠습니까?")
            .setNegativeButton("취소", null)
            .setPositiveButton("삭제") { _, _ ->

                val result = dbHelper.deleteTravel(travelId)

                if (result > 0) {
                    loadTravelList()
                }
            }
            .show()
    }

    private fun loadTravelList() {
        val travelList = dbHelper.getAllTravels()

        travelAdapter.updateData(travelList)

        if (travelList.isEmpty()) {
            binding.textEmpty.visibility = View.VISIBLE
            binding.recyclerTravel.visibility = View.GONE
        } else {
            binding.textEmpty.visibility = View.GONE
            binding.recyclerTravel.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
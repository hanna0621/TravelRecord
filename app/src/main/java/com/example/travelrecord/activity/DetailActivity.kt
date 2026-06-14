package com.example.travelrecord.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.travelrecord.database.DBHelper
import com.example.travelrecord.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var dbHelper: DBHelper

    private var travelId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        travelId = intent.getIntExtra("travel_id", -1)

        if (travelId == -1) {
            Toast.makeText(
                this,
                "여행 기록을 불러올 수 없습니다.",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        loadTravel()

        binding.buttonEdit.setOnClickListener {
            val intent = Intent(
                this,
                EditTravelActivity::class.java
            )

            intent.putExtra("travel_id", travelId)
            startActivity(intent)
        }

        binding.buttonDelete.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun loadTravel() {
        val travel = dbHelper.getTravelById(travelId)

        if (travel == null) {
            Toast.makeText(
                this,
                "여행 기록이 존재하지 않습니다.",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        binding.textDetailPlace.text = travel.place
        binding.textDetailDate.text = travel.visitDate
        binding.textDetailMemo.text =
            if (travel.memo.isBlank()) "작성된 메모가 없습니다." else travel.memo

        if (!travel.photoUri.isNullOrBlank()) {
            try {
                binding.imageDetailPhoto.setImageURI(
                    Uri.parse(travel.photoUri)
                )
            } catch (e: Exception) {
                binding.imageDetailPhoto.setImageResource(
                    android.R.drawable.ic_menu_gallery
                )
            }
        } else {
            binding.imageDetailPhoto.setImageResource(
                android.R.drawable.ic_menu_gallery
            )
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("여행 기록 삭제")
            .setMessage("정말 이 여행 기록을 삭제하시겠습니까?")
            .setNegativeButton("취소", null)
            .setPositiveButton("삭제") { _, _ ->

                val result = dbHelper.deleteTravel(travelId)

                if (result > 0) {
                    Toast.makeText(
                        this,
                        "여행 기록이 삭제되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "삭제에 실패했습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .show()
    }

    override fun onResume() {
        super.onResume()

        if (travelId != -1) {
            loadTravel()
        }
    }
}
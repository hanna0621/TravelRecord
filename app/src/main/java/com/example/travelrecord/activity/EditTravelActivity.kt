package com.example.travelrecord.activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.travelrecord.database.DBHelper
import com.example.travelrecord.databinding.ActivityEditTravelBinding
import com.example.travelrecord.model.Travel

class EditTravelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTravelBinding
    private lateinit var dbHelper: DBHelper

    private var travelId: Int = -1
    private var currentPhotoUri: String? = null

    private val photoPickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->

            if (uri != null) {
                currentPhotoUri = uri.toString()

                try {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }

                binding.imagePhoto.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditTravelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        travelId = intent.getIntExtra("travel_id", -1)

        if (travelId == -1) {
            Toast.makeText(
                this,
                "수정할 여행 기록을 불러올 수 없습니다.",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        loadTravel()

        binding.buttonSelectPhoto.setOnClickListener {
            photoPickerLauncher.launch(
                arrayOf("image/*")
            )
        }

        binding.buttonUpdate.setOnClickListener {
            updateTravel()
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

        binding.editPlace.setText(travel.place)
        binding.editVisitDate.setText(travel.visitDate)
        binding.editMemo.setText(travel.memo)

        currentPhotoUri = travel.photoUri

        if (!currentPhotoUri.isNullOrBlank()) {
            try {
                binding.imagePhoto.setImageURI(
                    Uri.parse(currentPhotoUri)
                )
            } catch (e: Exception) {
                binding.imagePhoto.setImageResource(
                    android.R.drawable.ic_menu_gallery
                )
            }
        } else {
            binding.imagePhoto.setImageResource(
                android.R.drawable.ic_menu_gallery
            )
        }
    }

    private fun updateTravel() {
        val place = binding.editPlace.text.toString().trim()
        val visitDate = binding.editVisitDate.text.toString().trim()
        val memo = binding.editMemo.text.toString().trim()

        if (place.isEmpty()) {
            binding.editPlace.error = "여행지명을 입력해주세요."
            binding.editPlace.requestFocus()
            return
        }

        if (visitDate.isEmpty()) {
            binding.editVisitDate.error = "방문 날짜를 입력해주세요."
            binding.editVisitDate.requestFocus()
            return
        }

        val travel = Travel(
            no = travelId,
            place = place,
            visitDate = visitDate,
            memo = memo,
            photoUri = currentPhotoUri
        )

        val result = dbHelper.updateTravel(travel)

        if (result > 0) {
            Toast.makeText(
                this,
                "여행 기록이 수정되었습니다.",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        } else {
            Toast.makeText(
                this,
                "수정에 실패했습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
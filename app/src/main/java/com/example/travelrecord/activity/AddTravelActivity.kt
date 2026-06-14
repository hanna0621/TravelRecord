package com.example.travelrecord.activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.travelrecord.database.DBHelper
import com.example.travelrecord.databinding.ActivityAddTravelBinding
import com.example.travelrecord.model.Travel

class AddTravelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTravelBinding
    private lateinit var dbHelper: DBHelper

    private var selectedPhotoUri: Uri? = null

    private val photoPickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->

            if (uri != null) {
                selectedPhotoUri = uri

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

        binding = ActivityAddTravelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonSelectPhoto.setOnClickListener {
            photoPickerLauncher.launch(
                arrayOf("image/*")
            )
        }

        binding.buttonSave.setOnClickListener {
            saveTravel()
        }
    }

    private fun saveTravel() {
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
            place = place,
            visitDate = visitDate,
            memo = memo,
            photoUri = selectedPhotoUri?.toString()
        )

        val result = dbHelper.insertTravel(travel)

        if (result != -1L) {
            Toast.makeText(
                this,
                "여행 기록이 저장되었습니다.",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        } else {
            Toast.makeText(
                this,
                "저장에 실패했습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
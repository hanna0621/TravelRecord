package com.example.travelrecord

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.travelrecord.databinding.ActivityMainBinding
import com.example.travelrecord.database.DBHelper
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val dbHelper = DBHelper(this)
        dbHelper.writableDatabase

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menuNewest -> {
                supportFragmentManager.setFragmentResult(
                    "sort_request",
                    Bundle().apply {
                        putString("sort_type", "newest")
                    }
                )

                Toast.makeText(
                    this,
                    "최신순으로 정렬했습니다.",
                    Toast.LENGTH_SHORT
                ).show()

                true
            }

            R.id.menuOldest -> {
                supportFragmentManager.setFragmentResult(
                    "sort_request",
                    Bundle().apply {
                        putString("sort_type", "oldest")
                    }
                )

                Toast.makeText(
                    this,
                    "오래된순으로 정렬했습니다.",
                    Toast.LENGTH_SHORT
                ).show()

                true
            }

            R.id.menuDeleteAll -> {
                showDeleteAllDialog()
                true
            }

            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteAllDialog() {
        AlertDialog.Builder(this)
            .setTitle("전체 삭제")
            .setMessage("모든 여행 기록을 삭제하시겠습니까?")
            .setNegativeButton("취소", null)
            .setPositiveButton("삭제") { _, _ ->

                val dbHelper = DBHelper(this)
                val result = dbHelper.deleteAllTravels()

                supportFragmentManager.setFragmentResult(
                    "refresh_request",
                    Bundle()
                )

                Toast.makeText(
                    this,
                    if (result > 0) {
                        "전체 기록이 삭제되었습니다."
                    } else {
                        "삭제할 기록이 없습니다."
                    },
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
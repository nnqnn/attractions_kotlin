package com.nnqnn.attractions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.view.WindowCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.nnqnn.attractions.databinding.ActivityMainBinding
import com.nnqnn.attractions.domain.ThemeManager
import com.nnqnn.attractions.ui.AttractionsListFragment
import com.nnqnn.attractions.ui.MapFragment
import com.nnqnn.attractions.ui.SettingsFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val themeManager: ThemeManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        themeManager.applyCurrent()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        if (savedInstanceState == null) {
            openFragment(AttractionsListFragment(), "list")
            binding.bottomNav.selectedItemId = R.id.nav_list
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_list -> openFragment(AttractionsListFragment(), "list")
                R.id.nav_map -> openFragment(MapFragment(), "map")
                R.id.nav_settings -> openFragment(SettingsFragment(), "settings")
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        val current = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, current ?: fragment, tag)
            .commit()
    }
}


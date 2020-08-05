package com.tkpd.movieapp.feature.movielist

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.tkpd.movieapp.R
import com.tkpd.movieapp.constant.MovieConstant.DARK_MODE_KEY
import com.tkpd.movieapp.constant.MovieConstant.DARK_MODE_PREF
import com.tkpd.movieapp.feature.movielist.view.MovieListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MovieListActivity : AppCompatActivity() {

    private val darkModePref by lazy {
        this.getSharedPreferences(DARK_MODE_PREF, MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content_view, MovieListFragment())
            .commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menuShowDue)
        val textView = searchItem.actionView?.findViewById<TextView>(R.id.txt_menu)

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                darkModePref.edit().putString(DARK_MODE_KEY, "Dark").apply()
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                darkModePref.edit().putString(DARK_MODE_KEY, "Light").apply()
            }
        }

        textView?.apply {
            val mode = darkModePref.getString(DARK_MODE_KEY, "")
            text = mode
            setOnClickListener {
                setDarkMode(this)
            }
        }

        return true
    }

    private fun setDarkMode(textView: TextView) {
        val editor = darkModePref.edit()
        when (textView.text) {
            "Dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            "Light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        editor.putString(DARK_MODE_KEY, textView.text.toString())
        editor.apply()
    }
}

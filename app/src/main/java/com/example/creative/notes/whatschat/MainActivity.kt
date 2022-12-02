package com.example.creative.notes.whatschat

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.creative.notes.whatschat.Activities.PhoneActivity
import com.example.creative.notes.whatschat.Adapters.ViewPagerAdapter
import com.example.creative.notes.whatschat.UI.CallFragment
import com.example.creative.notes.whatschat.UI.ChatFragment
import com.example.creative.notes.whatschat.UI.StatusFragment
import com.example.creative.notes.whatschat.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            startActivity(Intent(this, PhoneActivity::class.java))
            finish()
        }


        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(ChatFragment())
        fragmentList.add(StatusFragment())
        fragmentList.add(CallFragment())

        val adapter = ViewPagerAdapter(this, supportFragmentManager, fragmentList)
        binding.viewPager.adapter = adapter

        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    override fun onBackPressed() {
        showSnackbar()
    }


    fun showSnackbar() {
        val view : View = findViewById(R.id.constraint)
        val snackbar: Snackbar = Snackbar.make(view,
            "Exit Application ?",
            Snackbar.LENGTH_LONG)
            .setAction("YES", View.OnClickListener {
                finish()
            }).setActionTextColor(ContextCompat.getColor(this@MainActivity, R.color.yellow_new))
        snackbar.setBackgroundTint(ContextCompat.getColor(this@MainActivity, R.color.dark_grey))
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
    }


}
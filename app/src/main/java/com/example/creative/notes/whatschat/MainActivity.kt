package com.example.creative.notes.whatschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.creative.notes.whatschat.Activities.PhoneActivity
import com.example.creative.notes.whatschat.Adapters.ViewPagerAdapter
import com.example.creative.notes.whatschat.UI.CallFragment
import com.example.creative.notes.whatschat.UI.ChatFragment
import com.example.creative.notes.whatschat.UI.StatusFragment
import com.example.creative.notes.whatschat.databinding.ActivityMainBinding
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


        val fragementList = ArrayList<Fragment>()
        fragementList.add(ChatFragment())
        fragementList.add(StatusFragment())
        fragementList.add(CallFragment())

        val adapter = ViewPagerAdapter(this, supportFragmentManager, fragementList)
        binding.viewPager.adapter = adapter

        binding.tabs.setupWithViewPager(binding.viewPager)
    }
}
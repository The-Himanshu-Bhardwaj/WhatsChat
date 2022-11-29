package com.example.creative.notes.whatschat.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.creative.notes.whatschat.MainActivity
import com.example.creative.notes.whatschat.R
import com.example.creative.notes.whatschat.databinding.ActivityPhoneBinding
import com.google.firebase.auth.FirebaseAuth

class PhoneActivity : AppCompatActivity() {

    lateinit var binding : ActivityPhoneBinding
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.continueBtn.setOnClickListener {
            if (binding.nuberBox.text!!.isEmpty())  {
                Toast.makeText(this, "enter something", Toast.LENGTH_LONG).show()
                TODO() //  CHECK 10 DIGIT NUMBER
            }
            else {
                var intent = Intent(this, OTPActivity::class.java)
                intent.putExtra("number", binding.nuberBox.text.toString()  )
                startActivity(intent)
                finish()
            }
        }
    }
}
package com.example.creative.notes.whatschat.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.creative.notes.whatschat.MainActivity
import com.example.creative.notes.whatschat.R
import com.example.creative.notes.whatschat.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    lateinit var binding: ActivityOtpactivityBinding
    lateinit var auth: FirebaseAuth
    lateinit var verificationID: String
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpactivity)

        auth = FirebaseAuth.getInstance()

        val dialogMsg = AlertDialog.Builder(this)
        dialogMsg.setMessage("Please Wait")
        dialogMsg.setTitle("Loading")
        dialogMsg.setCancelable(false)

        dialog = dialogMsg.create()
        dialog.show()

        val phoneNumber = "+91"+intent.getStringExtra("number")
        binding.textView.append(" $phoneNumber")

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OTPActivity, p0.toString(), Toast.LENGTH_LONG).show()
                    Log.d("@@", p0.toString())
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    Toast.makeText(this@OTPActivity, "Code Sent !", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                    verificationID = p0
                }

            }).build()

        // verify phone number process starts here

        PhoneAuthProvider.verifyPhoneNumber(options)

        // btn click

        binding.continueBtn.setOnClickListener {

            if (binding.nuberBox.text!!.isEmpty()) {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_LONG).show()
            } else {
                dialog.show()
                // verification ID and OTP passed
                val credential = PhoneAuthProvider.getCredential(verificationID,
                    binding.nuberBox.text.toString())

                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            dialog.dismiss()
                            Toast.makeText(this, "Success ", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, ProfileActivity::class.java))
                            finish()
                        } else {
                            dialog.dismiss()
                            Toast.makeText(this, "Error ${task.exception}", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
        }
    }
}
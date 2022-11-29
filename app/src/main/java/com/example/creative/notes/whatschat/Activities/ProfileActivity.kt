package com.example.creative.notes.whatschat.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.creative.notes.whatschat.MainActivity
import com.example.creative.notes.whatschat.Model.UserModel
import com.example.creative.notes.whatschat.R
import com.example.creative.notes.whatschat.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImageURI: Uri
    private lateinit var dialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        dialog = AlertDialog.Builder(this)
            .setTitle("Please Wait...")
            .setMessage("Working on it...")
            .setCancelable(false)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        binding.userImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.continueBtn.setOnClickListener {
            if (binding.nameBox.text!!.isEmpty()) {
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_LONG).show()
            } else if (selectedImageURI == null) {
                Toast.makeText(this, "Please Enter Profile Photo", Toast.LENGTH_LONG).show()
            } else {
                uploadUserData()
            }
        }
    }

    private fun uploadUserData() {

        val reference = storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(selectedImageURI).addOnCompleteListener {
            if (it.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())
                    Log.d("@@", task.toString())
                }
            }
        }.addOnFailureListener{
            Log.d("@@", it.toString())
        }
    }

    private fun uploadInfo(imageUrl: String) {
        val user = UserModel(auth.uid.toString(),
            binding.nameBox.text.toString(),
            auth.currentUser!!.phoneNumber.toString(),
            imageUrl)

        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener { task ->
                Toast.makeText(this, "user saved !!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Log.d("@@", it.toString())
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                selectedImageURI = data.data!!
                binding.userImage.setImageURI(selectedImageURI)
                Log.d("@@", data.data!!.toString())
            } else {
                Toast.makeText(this, "Error Picking an Image", Toast.LENGTH_LONG).show()
            }
        }
    }
}
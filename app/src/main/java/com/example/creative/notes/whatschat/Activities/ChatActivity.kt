package com.example.creative.notes.whatschat.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.creative.notes.whatschat.Model.MessageModel
import com.example.creative.notes.whatschat.R
import com.example.creative.notes.whatschat.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.Date

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase

    private lateinit var senderUID: String
    private lateinit var receiverUID: String

    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)

        database = FirebaseDatabase.getInstance()

        senderUID = FirebaseAuth.getInstance().uid.toString()

        // "check here that getinstance.cuuretnuser.uid or gi.uid"
        receiverUID = intent.getStringExtra("uid").toString()

        // storing messages for both rooms i.e. sender and receiver
        senderRoom = senderUID + receiverUID
        receiverRoom = receiverUID + senderUID

        binding.sendBtn.setOnClickListener {
            if (binding.msgBox.text.isEmpty()) {
                Toast.makeText(this, "Empty messages are not allowed", Toast.LENGTH_LONG).show()
            } else {
                val message = MessageModel(binding.msgBox.text.toString(), senderUID, Date().time)
                val randomKey = database.reference.push().key

                database.reference.child("chats")
                    .child(senderRoom)
                    .child("messages")
                    .child(randomKey!!)
                    .setValue(message).addOnSuccessListener {
                        database.reference.child("chats")
                            .child(receiverRoom)
                            .child("messages")
                            .child(randomKey!!)
                            .setValue(message).addOnSuccessListener {
                                Toast.makeText(this, "Message Sent !", Toast.LENGTH_LONG).show()
                                binding.msgBox.setText("")
                            }
                    }

            }
        }
    }
}
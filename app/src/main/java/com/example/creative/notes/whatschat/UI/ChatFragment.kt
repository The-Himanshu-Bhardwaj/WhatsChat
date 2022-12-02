package com.example.creative.notes.whatschat.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.creative.notes.whatschat.Adapters.ChatAdapter
import com.example.creative.notes.whatschat.Model.UserModel
import com.example.creative.notes.whatschat.R
import com.example.creative.notes.whatschat.databinding.FragmentChatBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {

    lateinit var binding : FragmentChatBinding
    lateinit var database: FirebaseDatabase
    lateinit var userList : ArrayList<UserModel>
    lateinit var shimmer : ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        shimmer = ShimmerFrameLayout(requireContext())
        shimmer.startShimmer()

        database = FirebaseDatabase.getInstance()
        userList = ArrayList()

        database.reference.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for (snap in snapshot.children) {
                        val user = snap.getValue(UserModel::class.java)
                        if (user!!.uid != FirebaseAuth.getInstance().uid) {
                                userList.add(user)
                        }
                    }
                    binding.userListRecycler.adapter = ChatAdapter(requireContext(), userList)
                    shimmer.stopShimmer()
                    binding.userListRecycler.visibility = View.VISIBLE
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        return binding.root
    }
}
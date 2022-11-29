package com.example.creative.notes.whatschat.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.creative.notes.whatschat.Model.UserModel
import com.example.creative.notes.whatschat.R
import com.example.creative.notes.whatschat.databinding.UserListLayoutBinding

class ChatAdapter(var context: Context, var list : ArrayList<UserModel>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var binding = UserListLayoutBinding.bind(itemView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        var user = list[position]
        Glide.with(context).load(user.imageUrl).into(holder.binding.userImg)
        holder.binding.userName.text = user.name
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
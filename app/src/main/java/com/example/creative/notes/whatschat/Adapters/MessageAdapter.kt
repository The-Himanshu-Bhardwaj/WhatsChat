package com.example.creative.notes.whatschat.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.creative.notes.whatschat.Model.MessageModel
import com.example.creative.notes.whatschat.R
import com.example.creative.notes.whatschat.databinding.ReceivedMsgLayoutBinding
import com.example.creative.notes.whatschat.databinding.SentMsgLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.NonDisposableHandle.parent

class MessageAdapter(val context: Context, val list : ArrayList<MessageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ITEM_SENT = 1
    var ITEM_RECEIVED = 2

    inner class SentViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var binding = SentMsgLayoutBinding.bind(view)
    }

    inner class RcvdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ReceivedMsgLayoutBinding.bind(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (FirebaseAuth.getInstance().uid == list[position].senderID) ITEM_SENT else ITEM_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT)
            SentViewHolder(
                LayoutInflater.from(context).inflate(R.layout.sent_msg_layout, parent, false))
        else RcvdViewHolder(
            LayoutInflater.from(context).inflate(R.layout.received_msg_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]

        if (holder.itemViewType == ITEM_SENT) {
            val viewHolder = holder as SentViewHolder
            viewHolder.binding.sentMsgLabel.text = message.message
        }
        else {
            val viewHolder = holder as RcvdViewHolder
            viewHolder.binding.receivedMsgLabel.text = message.message
        }
    }
}
package com.learning.messenger.ui.messages

import android.text.format.DateUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.learning.messenger.R
import com.learning.messenger.data.Message

class MessagesAdapter(val userId: Int) : RecyclerView.Adapter<MessageViewHolder>() {
    var messages: List<Message> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.message, parent, false)
        return MessageViewHolder(userId, view as ConstraintLayout)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}

class MessageViewHolder(val userId: Int, messageView: ConstraintLayout) : RecyclerView.ViewHolder(messageView) {
    private val messageTextView: TextView = messageView.findViewById(R.id.messageText)
    private val messageTimeView: TextView = messageView.findViewById(R.id.messageTime)
    fun bind(message: Message) {
        if (message.senderId == userId) {
            messageTextView.gravity = Gravity.END
            messageTimeView.gravity = Gravity.END
        }
        messageTextView.text = message.message
        messageTimeView.text = DateUtils.getRelativeTimeSpanString(
            message.timestamp, System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE
        )
    }

}

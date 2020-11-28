package com.learning.messenger.data

data class Message(
    val id: Int,
    val senderId: Int,
    val recipientId: Int,
    val message: String,
    val attachmentUrl: String?,
    val timestamp: Long
)
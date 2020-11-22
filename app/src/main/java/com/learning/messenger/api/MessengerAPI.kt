package com.learning.messenger.api

import android.graphics.Bitmap
import com.learning.messenger.data.Message
import com.learning.messenger.data.Person
import io.reactivex.Single

interface IMessengerAPI {
    fun login(username: String, password: String): Single<Int>
    fun changePassword(userId: Int, password: String): Single<Boolean>
    fun getContacts(userId: Int): Single<List<Person>>
    fun getMessages(userId: Int, personId: Int, numberOfLastMessages: Int): Single<List<Message>>
    fun sendMessage(userId: Int, recipientId: Int, message: String, attachment: Bitmap?): Single<Boolean>
}
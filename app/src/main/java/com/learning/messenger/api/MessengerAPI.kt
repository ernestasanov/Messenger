package com.learning.messenger.api

import android.graphics.Bitmap
import com.learning.messenger.data.Message
import com.learning.messenger.data.Person
import io.reactivex.Observable

interface IMessengerAPI {
    fun login(username: String, password: String): Observable<Int>
    fun changePassword(userId: Int, password: String): Observable<Boolean>
    fun getContacts(userId: Int): Observable<List<Person>>
    fun getMessages(userId: Int, personId: Int, numberOfLastMessages: Int): Observable<List<Message>>
    fun sendMessage(userId: Int, recipientId: Int, message: String, attachment: Bitmap): Observable<Boolean>
}
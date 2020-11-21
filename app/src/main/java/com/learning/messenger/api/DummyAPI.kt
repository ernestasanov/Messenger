package com.learning.messenger.api

import android.graphics.Bitmap
import com.learning.messenger.data.Message
import com.learning.messenger.data.Person
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class DummyAPI : IMessengerAPI {
    override fun login(username: String, password: String): Observable<Int> {
        if (username != "Ernest") {
            return Observable.error(IllegalArgumentException("Wrong username"))
        } else if (password != "password") {
            return Observable.error(IllegalArgumentException("Wrong password"))
        }
        return Observable.just(1).delay(1, TimeUnit.SECONDS)
    }

    override fun changePassword(userId: Int, password: String): Observable<Boolean> {
        return Observable.just(true)
    }

    override fun getContacts(userId: Int): Observable<List<Person>> {
        return Observable.just(
            listOf(
                Person(1, "Ernest", "Ernest", "Asanov", ""),
                Person(2, "Jovan", "Jovan", "Velanac", ""),
                Person(3, "Leo", "Leonardo", "Fanchini", ""),
                Person(4, "Riva", "Riva", "Fan", "")
            )
        )
    }

    override fun getMessages(
        userId: Int,
        personId: Int,
        numberOfLastMessages: Int
    ): Observable<List<Message>> {
        return Observable.just(
            listOf(
                Message(
                    1, 1, 2, "Hi!", null, System.currentTimeMillis() -
                            5 * 60 * 1000
                ),

                Message(
                    2, 2, 1, "Hi!", "", System.currentTimeMillis() -
                            4 * 60 * 1000
                )
            )
        )
    }

    override fun sendMessage(
        userId: Int,
        recipientId: Int,
        message: String,
        attachment: Bitmap
    ): Observable<Boolean> {
        return Observable.just(true)
    }
}
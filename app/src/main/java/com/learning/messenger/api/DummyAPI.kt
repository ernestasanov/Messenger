package com.learning.messenger.api

import android.graphics.Bitmap
import com.learning.messenger.data.Message
import com.learning.messenger.data.Person
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class DummyAPI : IMessengerAPI {
    override fun login(username: String, password: String): Single<Int> {
        if (username != "Ernest") {
            return Single.error(IllegalArgumentException("Wrong username"))
        } else if (password != "password") {
            return Single.error(IllegalArgumentException("Wrong password"))
        }
        return Single.just(1).delay(1, TimeUnit.SECONDS)
    }

    override fun changePassword(userId: Int, password: String): Single<Boolean> {
        return Single.just(true)
    }

    override fun getContacts(userId: Int): Single<List<Person>> {
        return Single.just(
            listOf(
                Person(1, "Ernest", "Asanov", ""),
                Person(2, "Jovan", "Velanac", ""),
                Person(3, "Leonardo", "Fanchini", ""),
                Person(4, "Riva", "Fan", "")
            )
        )
    }

    override fun getMessages(
        userId: Int,
        personId: Int,
        numberOfLastMessages: Int
    ): Single<List<Message>> {
        return Single.just(
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
        attachment: Bitmap?
    ): Single<Boolean> {
        return Single.just(true)
    }
}
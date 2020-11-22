package com.learning.messenger.api

object ApiRepository {
    val api : IMessengerAPI
        get() = RealAPI()
}
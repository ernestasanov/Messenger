package com.learning.messenger.data

data class Person(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val photoUrl: String = ""
)
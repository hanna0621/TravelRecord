package com.example.travelrecord.model

data class Travel(
    val no: Int = 0,
    val place: String,
    val visitDate: String,
    val memo: String,
    val photoUri: String?
)
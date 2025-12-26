package com.mikes.customerrewardprograms.domain

data class User (
    val id: String,
    val name: String,
    val email: String,
    val points: Int,
    val isMaster: Boolean = false,
    val qrCode: String,
)
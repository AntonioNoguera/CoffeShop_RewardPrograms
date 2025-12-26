package com.mikes.customerrewardprograms.domain

data class Promotion (
    val id: String,
    val title: String,
    val description: String,
    val pointsRequired: Int,
    val imageUrl: String,
    val validUntil: String,
    val isActive: Boolean = true,
)
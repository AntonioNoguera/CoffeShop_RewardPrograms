package com.mikes.customerrewardprograms.domain

data class Transaction (
    val id: String,
    val userId: String,
    val points: Int,
    val description: String,
    val timestamp: Long,
    val type: TransactionType,
)

enum class TransactionType {
    EARNED,
    REDEEMED,
}
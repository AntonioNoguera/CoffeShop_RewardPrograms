package com.mikes.customerrewardprograms.data.repository

import com.mikes.customerrewardprograms.domain.Transaction
import com.mikes.customerrewardprograms.domain.TransactionType
import com.mikes.customerrewardprograms.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class UserRepository {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    //Mock for users
    private val users = mutableMapOf(
        "USER001" to User(
            id = "1",
            name = "Juan Pérez",
            email = "juan@example.com",
            points = 150,
            isMaster = false,
            qrCode = "USER001"
        ),
        "USER002" to User(
            id = "2",
            name = "María González",
            email = "maria@example.com",
            points = 320,
            isMaster = false,
            qrCode = "USER002"
        ),
        "MASTER001" to User(
            id = "3",
            name = "Admin Cafetería",
            email = "admin@cafeteria.com",
            points = 0,
            isMaster = true,
            qrCode = "MASTER001"
        )
    )

    //Durísimo a spring no?
    fun loginWithQR(qrCode: String) : Result<User> {
        return users[qrCode]?.let { user ->
            _currentUser.value = user
            //loadTransactions
            Result.success(user)
        } ?: Result.failure(Exception("User not found"))
    }

    fun getUsersByQR(qrCode: String) : User? {
        return users[qrCode]
    }

    fun addPoints(targetUserId: String, points: Int, description: String): Result<Unit> {
        val currentUserValue = _currentUser.value

        if (currentUserValue?.isMaster != true) {
            return Result.failure(Exception("NOT AUTHORIZED"))
        }

        users.values.find{ it.id == targetUserId }?.let { targetUser ->
            val updatedUser = targetUser.copy(points = targetUser.points + points)

            users[updatedUser.qrCode] = updatedUser

            val transaction = Transaction(
                id = UUID.randomUUID().toString(),
                userId = targetUserId,
                points = points,
                description = description,
                timestamp = System.currentTimeMillis(),
                type = TransactionType.EARNED,
            )

            _transactions.value = (_transactions.value + transaction).toList()

            return Result.success(Unit)
        }

        return Result.failure(Exception("USER NOT FOUND"))
    }

    fun logout() {
        _currentUser.value = null
        _transactions.value = emptyList()
    }

    private fun loadTransactionsForUser(userId: String) {
        // Mock transactions
        _transactions.value = listOf(
            Transaction(
                id = "1",
                userId = userId,
                points = 50,
                description = "Compra de café latte",
                timestamp = System.currentTimeMillis() - 86400000,
                type = TransactionType.EARNED
            ),
            Transaction(
                id = "2",
                userId = userId,
                points = 100,
                description = "Compra múltiple",
                timestamp = System.currentTimeMillis() - 172800000,
                type = TransactionType.EARNED
            )
        )
    }
}
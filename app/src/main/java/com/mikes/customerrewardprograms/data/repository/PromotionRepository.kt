package com.mikes.customerrewardprograms.data.repository

import com.mikes.customerrewardprograms.domain.Promotion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PromotionRepository {
    fun getPromotions(): Flow<List<Promotion>> {
        return flowOf(
            listOf(
                Promotion(
                    id = "1",
                    title = "Café Gratis",
                    description = "Canjea un café de cualquier tamaño",
                    pointsRequired = 100,
                    imageUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93",
                    validUntil = "31 Dic 2024"
                ),
                Promotion(
                    id = "2",
                    title = "2x1 en Pasteles",
                    description = "Compra un pastel y lleva otro gratis",
                    pointsRequired = 150,
                    imageUrl = "https://images.unsplash.com/photo-1578985545062-69928b1d9587",
                    validUntil = "15 Ene 2025"
                ),
                Promotion(
                    id = "3",
                    title = "Sandwich Premium",
                    description = "Un sandwich de la casa gratis",
                    pointsRequired = 200,
                    imageUrl = "https://images.unsplash.com/photo-1528735602780-2552fd46c7af",
                    validUntil = "28 Feb 2025"
                ),
                Promotion(
                    id = "4",
                    title = "Cappuccino Grande",
                    description = "Cappuccino grande con arte latte",
                    pointsRequired = 120,
                    imageUrl = "https://images.unsplash.com/photo-1572442388796-11668a67e53d",
                    validUntil = "31 Ene 2025"
                ),
                Promotion(
                    id = "5",
                    title = "Combo Desayuno",
                    description = "Café + Croissant + Jugo natural",
                    pointsRequired = 250,
                    imageUrl = "https://images.unsplash.com/photo-1533910534207-90f31029a78e",
                    validUntil = "31 Mar 2025"
                )
            )
        )
    }
}
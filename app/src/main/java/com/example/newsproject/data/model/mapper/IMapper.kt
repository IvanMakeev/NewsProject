package com.example.newsproject.data.model.mapper

/**
 * Базовый интерфейс для создания Маппера
 */
interface IMapper<J, R> {

    fun mapFromJsonToRoom(type: J): R

}

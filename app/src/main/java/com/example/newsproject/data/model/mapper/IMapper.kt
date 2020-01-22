package com.example.newsproject.data.model.mapper

interface IMapper<J, R> {

    fun mapFromJsonToRoom(type: J): R

}

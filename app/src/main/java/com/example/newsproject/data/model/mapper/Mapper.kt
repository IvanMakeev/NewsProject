package com.example.newsproject.data.model.mapper

interface Mapper<J, R> {

    fun mapFromJsonToRoom(type: J): R

}

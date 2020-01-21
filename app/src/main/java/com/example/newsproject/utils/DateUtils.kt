package com.example.newsproject.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun format(date: String): String {
        val unParseDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'", Locale.getDefault())
            .run { parse(date) }

        val sdf = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
        return sdf.format(unParseDate!!)
    }

}


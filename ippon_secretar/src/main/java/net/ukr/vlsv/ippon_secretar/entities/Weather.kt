package net.ukr.vlsv.ippon_secretar.entities

data class Weather(
        val id: Long,
        val main: String,
        val description: String,
        val icon: String
)
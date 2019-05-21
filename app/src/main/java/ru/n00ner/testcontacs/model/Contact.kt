package ru.n00ner.testcontacs.model

import android.arch.persistence.room.PrimaryKey
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class Contact(
    val id: String,
    val name: String,
    val phone: String,
    val biography: String,
    val height: Double,
    val temperament: String,
    val educationPeriod: EducationPeriod
)

data class EducationPeriod(val end: String, val start: String){
    override fun toString(): String{
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val startRaw = df.parse(start)
        val endRaw = df.parse(end)
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(startRaw) + " - " + formatter.format(endRaw)
    }
}
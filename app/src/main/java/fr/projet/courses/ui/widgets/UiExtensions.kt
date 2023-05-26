package fr.projet.courses.ui.widgets

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val dateFormatted = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

fun String.toFormatInFrance(): String {
    if(this.isEmpty()) return "non renseigné"

    val date = Date(this)
    return dateFormatted.format(date)
}

fun Date.toFormatInFrance(): String = dateFormatted.format(this)

fun String.toIconText() = this.slice(0..1)
package fr.projet.courses.data.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Courses(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val done: Boolean = false,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdTime: String = "",
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val lastModifiedTime: String = ""
)

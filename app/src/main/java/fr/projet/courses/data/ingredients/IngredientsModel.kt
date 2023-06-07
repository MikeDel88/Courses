package fr.projet.courses.data.ingredients

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import fr.projet.courses.data.courses.Courses

@Entity
data class Ingredients(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "course_id")
    val courseId: Int = -1,
    val name: String,
    val done: Boolean = false,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdTime: String = "",
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val lastModifiedTime: String = ""
)
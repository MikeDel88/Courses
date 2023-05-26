package fr.projet.courses.database.courses

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fr.projet.courses.data.courses.Courses

@Dao
interface CoursesDao {

    @Query("SELECT * FROM courses ORDER BY courses.createdTime")
    fun getAll(): LiveData<List<Courses>>

    @Query("SELECT * FROM courses WHERE id = :coursesId")
    fun getCourseById(coursesId: Int): Courses

    @Insert
    suspend fun insert(courses: Courses)

    @Delete
    fun delete(courses: Courses)

    @Update
    fun update(courses: Courses)
}
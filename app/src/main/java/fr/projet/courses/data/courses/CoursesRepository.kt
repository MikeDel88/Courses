package fr.projet.courses.data.courses

import androidx.lifecycle.LiveData

interface CoursesRepository {
    fun getAll(): LiveData<List<Courses>>
    suspend fun getById(courseId: Int): Courses
    suspend fun insert(courses: Courses)
    suspend fun update(courses: Courses)
    suspend fun delete(courses: Courses)
}
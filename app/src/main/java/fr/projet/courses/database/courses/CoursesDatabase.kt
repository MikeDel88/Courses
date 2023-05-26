package fr.projet.courses.database.courses

import androidx.lifecycle.LiveData
import fr.projet.courses.data.courses.Courses
import fr.projet.courses.data.courses.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoursesDatabase(private val coursesDao: CoursesDao): CoursesRepository {

    override fun getAll(): LiveData<List<Courses>> = coursesDao.getAll()
    override suspend fun getById(courseId: Int): Courses = withContext(Dispatchers.Default) {coursesDao.getCourseById(courseId) }
    override suspend fun insert(courses: Courses) = withContext(Dispatchers.Default) {coursesDao.insert(courses) }
    override suspend fun update(courses: Courses) = withContext(Dispatchers.Default) {coursesDao.update(courses) }
    override suspend fun delete(courses: Courses) = withContext(Dispatchers.Default) {coursesDao.delete(courses) }
}
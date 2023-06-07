package fr.projet.courses.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.projet.courses.data.courses.CoursesRepository
import fr.projet.courses.ui.courses.CourseViewModel
import fr.projet.courses.ui.courses.list.CoursesListViewModel
import java.lang.IllegalArgumentException

class ViewModelUiFactory(private val repository: CoursesRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CoursesListViewModel::class.java) -> CoursesListViewModel(repository)
            modelClass.isAssignableFrom(CourseViewModel::class.java) -> CourseViewModel(coursesRepository = repository)
            else -> throw IllegalArgumentException("Unexpected modelClas: $modelClass")
        } as T
    }
}
package fr.projet.courses.ui.courses.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import fr.projet.courses.data.courses.Courses
import fr.projet.courses.data.courses.CoursesRepository

data class CoursesListUiState(
    var hasChanged: Boolean = false,
    var courses: List<Courses>
)
class CoursesListViewModel(private val coursesRepository: CoursesRepository): ViewModel() {

    private var viewState = MediatorLiveData<CoursesListUiState>()
    val getViewState: LiveData<CoursesListUiState> = viewState

    init {

        viewState.addSource(coursesRepository.getAll()) { listCourses ->
            val oldState = viewState.value!!
            viewState.value = oldState.copy(
                hasChanged = true,
                courses = listCourses
            )
        }

        viewState.value = CoursesListUiState(
            hasChanged = false,
            courses = emptyList()
        )
    }

}
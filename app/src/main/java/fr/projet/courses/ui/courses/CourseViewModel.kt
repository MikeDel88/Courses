package fr.projet.courses.ui.courses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.projet.courses.data.courses.Courses
import fr.projet.courses.data.courses.CoursesRepository
import fr.projet.courses.ui.widgets.toFormatInFrance
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.util.Date

sealed class UiState {
    object UPDATE : UiState()
    object INSERT : UiState()
}

sealed class RequestState {
    object ERROR: RequestState()
    object SUCCESS: RequestState()
}

class CourseViewModel(courseId: Int = 0, private val coursesRepository: CoursesRepository): ViewModel() {

    private val uiState = MutableLiveData<UiState>()
    fun getUiState() = uiState

    private val requestState = MutableLiveData<RequestState>()
    fun getRequestState() = requestState

    private val course = MutableLiveData<Courses>()
    fun getCourse() = course

    private val isModeUpdate = courseId > 0

    init {
        Timber.i("init CourseViewModel")
        uiState.value = if(isModeUpdate) UiState.UPDATE else UiState.INSERT
        Timber.i("State : ${uiState.value}")

        if(isModeUpdate) updateState(courseId)
    }

    private fun updateState(courseId: Int) {
        Timber.i("UpdateState : get course $courseId")
        uiState.value = UiState.UPDATE
        viewModelScope.launch {
            course.value = coursesRepository.getById(courseId)
        }

    }

    fun saveCourse(nom: String, date: String, done: Boolean) = viewModelScope.launch {
        try {
            require(nom.isNotEmpty() && date.isNotEmpty() && nom.length >= 2)

            when (uiState.value!!) {
                UiState.INSERT -> {
                    coursesRepository.insert(
                        Courses(
                            name = nom,
                            createdTime = date
                        )
                    )
                }
                UiState.UPDATE -> {
                    require(course.value!!.id > 0)
                    coursesRepository.update(
                        Courses(
                            id = course.value!!.id,
                            name = nom,
                            lastModifiedTime = Date(System.currentTimeMillis()).toFormatInFrance(),
                            createdTime = date,
                            done = done
                        )
                    )
                }
            }

            requestState.value = RequestState.SUCCESS

        } catch (e: Exception) {
            Timber.e("Les champs ne sont pas remplis : $nom | $date")
            requestState.value = RequestState.ERROR
            return@launch
        }
    }

    fun deleteCourse() = viewModelScope.launch {
        require(course.value!!.id > 0)
        coursesRepository.delete(courses = course.value!!)
        requestState.value = RequestState.SUCCESS
    }

}
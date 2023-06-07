package fr.projet.courses.ui.courses

import androidx.lifecycle.LiveData
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

class CourseViewModel(private val coursesRepository: CoursesRepository): ViewModel() {

    private val uiState = MutableLiveData<UiState>()
    fun getUiState() = uiState

    private val requestState = MutableLiveData<RequestState>()
    fun getRequestState() = requestState

    private val course = MutableLiveData<Courses>()
    fun getCourse(courseId: Int): LiveData<Courses> {
        if(courseId > 0)
        {
            viewModelScope.launch {
                uiState.value = UiState.UPDATE
                course.value = coursesRepository.getById(courseId)
            }
        }
        else {
            uiState.value = UiState.INSERT
            course.value = Courses(0, "")
        }

        return course
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
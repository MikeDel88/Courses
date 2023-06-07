package fr.projet.courses.ui.courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import fr.projet.courses.App
import fr.projet.courses.databinding.FragmentModifyCourseBinding
import fr.projet.courses.ui.MainActivity
import fr.projet.courses.ui.ViewModelUiFactory

class ModifyCourseFragment : Fragment() {

    private lateinit var viewModel: CourseViewModel
    private var _binding: FragmentModifyCourseBinding? = null
    private val binding get() = _binding!!

    private val args: ModifyCourseFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentModifyCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendar.setOnClickListener { showDatePicker() }
        binding.valideButton.setOnClickListener { setValidation() }
        binding.deleteButton.setOnClickListener { setDelete() }


        viewModel = ViewModelProvider(this,  ViewModelUiFactory(App.repositoryListeCourses))[CourseViewModel::class.java]

        viewModel.getUiState().observe(viewLifecycleOwner) { state -> updateUi(state) }
        viewModel.getCourse(args.courseId).observe(viewLifecycleOwner) {  course ->
            binding.nameEditText.setText(course.name)
            binding.dateCreatedTextView.text = course.createdTime
            binding.doneSwitch.isChecked = course.done

            (activity as MainActivity).supportActionBar?.title = "Course : ${course.name}"
        }
        viewModel.getRequestState().observe(viewLifecycleOwner) { request ->
            when(request) {
                RequestState.ERROR -> Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_SHORT).show()
                RequestState.SUCCESS -> findNavController().popBackStack()
            }
        }
    }

    private fun setDelete() = viewModel.deleteCourse()

    private fun updateUi(state: UiState) {
        binding.doneSwitch.visibility = when(state) {
            UiState.INSERT -> GONE
            UiState.UPDATE -> VISIBLE
        }
        binding.deleteButton.visibility = when(state) {
            UiState.INSERT -> GONE
            UiState.UPDATE -> VISIBLE
        }
    }

    private fun setValidation() = viewModel.saveCourse(
        binding.nameEditText.text.toString(),
        binding.dateCreatedTextView.text.toString(),
        binding.doneSwitch.isChecked
    )


    private fun showDatePicker() {

        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()

        materialDateBuilder.setTitleText("Choisir une date")

        val materialDatePicker = materialDateBuilder.build()
        materialDatePicker.addOnPositiveButtonClickListener {
            binding.dateCreatedTextView.text = materialDatePicker.headerText
        }

        materialDatePicker.show(parentFragmentManager, "Calendar")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
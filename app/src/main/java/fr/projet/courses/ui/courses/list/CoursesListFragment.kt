package fr.projet.courses.ui.courses.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fr.projet.courses.App
import fr.projet.courses.data.courses.Courses
import fr.projet.courses.databinding.FragmentCoursesListBinding
import fr.projet.courses.ui.courses.EXTRA_COURSE_ID
import fr.projet.courses.ui.ViewModelUiFactory
import timber.log.Timber


class CoursesListFragment : Fragment(), CoursesListAdapter.CoursesListAdapterListener {

    private lateinit var viewModel: CoursesListViewModel
    private lateinit var adapter: CoursesListAdapter
    private var courses = mutableListOf<Courses>()

    private var _binding: FragmentCoursesListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCoursesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CoursesListAdapter(requireActivity(), courses, this)
        binding.recyclerView.layoutManager =  LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener { navigateToAddCourse() }

        viewModel = ViewModelProvider(requireActivity(), ViewModelUiFactory(repository = App.repositoryListeCourses))[CoursesListViewModel::class.java]
        viewModel.getViewState.observe(requireActivity()) { state -> updateUi(state!!) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun updateUi(state: CoursesListUiState) {
        Timber.i("New state : $state")

        if(state.hasChanged)
        {
            courses.clear()
            courses.addAll(state.courses)
            adapter.notifyDataSetChanged()
        }
    }

    private fun navigateToAddCourse() {
        Timber.i("navigateToAddCourse")
        val action = CoursesListFragmentDirections.actionCoursesListFragmentToAddCourseFragment()
        findNavController().navigate(action)
    }

    override fun onClickCourseItem(courses: Courses) {
        Timber.i("onClickCourseItem : $courses")
        val action = CoursesListFragmentDirections.actionCoursesListFragmentToIngredientsListFragment()
        with(action.arguments) {
            putInt(EXTRA_COURSE_ID, courses.id)
        }
        findNavController().navigate(action)
    }

    override fun onLongClickCourseItem(courses: Courses) {
        Timber.i("onLongClickCourseItem : $courses")
        val action = CoursesListFragmentDirections.actionCoursesListFragmentToAddCourseFragment()
        with(action.arguments) {
            putInt(EXTRA_COURSE_ID, courses.id)
        }
        findNavController().navigate(action)
    }

}
package fr.projet.courses.ui.courses.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.projet.courses.R
import fr.projet.courses.data.courses.Courses
import fr.projet.courses.databinding.ItemCoursesBinding
import fr.projet.courses.ui.widgets.toIconText

class CoursesListAdapter(context: Context, private val listeCourses: List<Courses>, private val listener: CoursesListAdapterListener? = null): RecyclerView.Adapter<CoursesListAdapter.ViewHolder>() {

    private val statutDoneColor = context.getColor(R.color.status_done)
    private val statutReadyColor = context.getColor(R.color.status_ready)

    interface CoursesListAdapterListener {
        fun onClickCourseItem(courses: Courses)
        fun onLongClickCourseItem(courses: Courses)
    }

    inner class ViewHolder(val binding: ItemCoursesBinding):  RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCoursesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courses = listeCourses[position]

        with(holder){
            binding.cardView.tag = courses
            binding.cardView.setOnLongClickListener { view -> listener?.onLongClickCourseItem(courses = view.tag as Courses); return@setOnLongClickListener true }
            binding.cardView.setOnClickListener { view -> listener?.onClickCourseItem(courses = view.tag as Courses) }
            binding.iconTextView.text = courses.name.toIconText()
            binding.statutView.setBackgroundColor( if(courses.done) statutDoneColor else statutReadyColor )
            binding.nameTextView.text = courses.name
            binding.dateCreatedTextView.text = courses.createdTime
        }
    }

    override fun getItemCount(): Int = listeCourses.size


}
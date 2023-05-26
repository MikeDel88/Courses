package fr.projet.courses

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import fr.projet.courses.database.AppDatabase
import fr.projet.courses.database.courses.CoursesDatabase
import fr.projet.courses.database.ingredients.IngredientsDatabase
import timber.log.Timber

class App: Application() {

    companion object {
        lateinit var database: AppDatabase
        val repositoryListeCourses by lazy { CoursesDatabase(database.coursesDao()) }
        val repositoryListeIngredients by lazy { IngredientsDatabase(database.ingredientsDao()) }
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        database = Room
            .databaseBuilder(this, AppDatabase::class.java, "courses-database")
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()

    }
}
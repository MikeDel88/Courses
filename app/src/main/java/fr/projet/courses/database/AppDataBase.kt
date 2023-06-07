package fr.projet.courses.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import fr.projet.courses.data.courses.Courses
import fr.projet.courses.data.ingredients.Ingredients
import fr.projet.courses.database.courses.CoursesDao
import fr.projet.courses.database.ingredients.IngredientsDao


@Database(entities = [Courses::class, Ingredients::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [AutoMigration (from = 1, to = 2), AutoMigration (from = 2, to = 3)]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
    abstract fun ingredientsDao(): IngredientsDao
}


package fr.projet.courses.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import fr.projet.courses.data.courses.Courses
import fr.projet.courses.data.ingredients.Ingredients
import fr.projet.courses.database.courses.CoursesDao
import fr.projet.courses.database.ingredients.IngredientsDao

const val CURRENT_VERSION = 1
const val LASTEST_VERSION = CURRENT_VERSION + 1


@Database(entities = [Courses::class, Ingredients::class],
    version = LASTEST_VERSION,
    exportSchema = true,
    autoMigrations = [AutoMigration (from = CURRENT_VERSION, to = LASTEST_VERSION)]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
    abstract fun ingredientsDao(): IngredientsDao
}


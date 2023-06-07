package fr.projet.courses.database.ingredients

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fr.projet.courses.data.ingredients.Ingredients

@Dao
interface IngredientsDao {

    @Query("SELECT * FROM ingredients")
    fun getAll(): LiveData<List<Ingredients>>

    @Query("SELECT * FROM ingredients WHERE course_id = :courseId")
    fun getIngredientsByCourseId(courseId: Int): LiveData<List<Ingredients>>

    @Query("SELECT * FROM ingredients WHERE id = :ingredientId")
    fun getIngredientById(ingredientId: Int): Ingredients

    @Insert
    suspend fun insert(ingredient: Ingredients)

    @Delete
    fun delete(ingredient: Ingredients)

    @Update
    fun update(ingredient: Ingredients)
}
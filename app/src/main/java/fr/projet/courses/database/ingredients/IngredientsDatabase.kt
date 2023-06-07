package fr.projet.courses.database.ingredients

import androidx.lifecycle.LiveData
import fr.projet.courses.data.ingredients.Ingredients
import fr.projet.courses.data.ingredients.IngredientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IngredientsDatabase(private val ingredientsDao: IngredientsDao): IngredientsRepository {

    override fun getAll(): LiveData<List<Ingredients>> = ingredientsDao.getAll()
    override suspend fun getIngredientsByCourseId(courseId: Int): LiveData<List<Ingredients>> = withContext(Dispatchers.Default) {ingredientsDao.getIngredientsByCourseId(courseId)}
    override suspend fun getById(ingredientId: Int): Ingredients = withContext(Dispatchers.Default) {ingredientsDao.getIngredientById(ingredientId) }
    override suspend fun insert(ingredients: Ingredients) = withContext(Dispatchers.Default) {ingredientsDao.insert(ingredients) }
    override suspend fun update(ingredients: Ingredients) = withContext(Dispatchers.Default) {ingredientsDao.update(ingredients) }
    override suspend fun delete(ingredients: Ingredients) = withContext(Dispatchers.Default) {ingredientsDao.delete(ingredients) }
}
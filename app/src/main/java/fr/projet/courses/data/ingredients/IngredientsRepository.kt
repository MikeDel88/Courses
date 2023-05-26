package fr.projet.courses.data.ingredients

import androidx.lifecycle.LiveData

interface IngredientsRepository {
    fun getAll(): LiveData<List<Ingredients>>
    suspend fun getById(courseId: Int): Ingredients
    suspend fun insert(ingredients: Ingredients)
    suspend fun update(ingredients: Ingredients)
    suspend fun delete(ingredients: Ingredients)
}
package com.app.composedemoapp.ui.theme.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.composedemoapp.ui.theme.Model.ResipeResponse
import com.app.composedemoapp.ui.theme.ViewModel.UiState
import com.app.composedemoapp.ui.theme.retrofit.RecipeApiInterface
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
data class DetailedRecipeResponse(
    val title: String // ✅ Extract only title from the response
)
@Singleton
class RecipeRepository @Inject constructor(private val api: RecipeApiInterface) {

    suspend fun fetchRecipeTitle(): UiState<String> {
        return try {
            val response: Response<DetailedRecipeResponse> = api.getRecipeById()
            if (response.isSuccessful && response.body() != null) {
                UiState.Success(response.body()!!.title) // Only returning the title
            } else {
                UiState.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            UiState.Error("Exception: ${e.message}")
        }
    }
    suspend fun getRecipes(): Response<ResipeResponse> {
        return api.getAllRecipe()
    }


     fun getCountry() : List<String>{
        return listOf("All", "India", "China", "Japan", "United State", "United Kingdom")
    }
}
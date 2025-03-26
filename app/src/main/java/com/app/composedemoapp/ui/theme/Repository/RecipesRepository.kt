package com.app.composedemoapp.ui.theme.Repository

import com.app.composedemoapp.ui.theme.Model.ResipeResponse
import com.app.composedemoapp.ui.theme.retrofit.RecipeApiInterface
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

//class RecipesRepository() {
//    suspend fun getRecipes() : Response<ResipeResponse> {
//        return api.getAllRecipe()
//    }
//}
@Singleton
class RecipeRepository @Inject constructor(private val api: RecipeApiInterface) {

    suspend fun getRecipes(): Response<ResipeResponse> {
        return api.getAllRecipe()
    }
}
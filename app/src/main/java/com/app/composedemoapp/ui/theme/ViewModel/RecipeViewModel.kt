package com.app.composedemoapp.ui.theme.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.composedemoapp.ui.theme.Model.RecipeItem
import com.app.composedemoapp.ui.theme.Model.ResipeResponse
import com.app.composedemoapp.ui.theme.Repository.DetailedRecipeResponse
import com.app.composedemoapp.ui.theme.Repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repo: RecipeRepository) : ViewModel() {
    private val _recipeState = MutableLiveData<UiState<List<RecipeItem>>>()
    val recipeState: LiveData<UiState<List<RecipeItem>>> = _recipeState

    private val _countryList = MutableLiveData<UiState<List<String>>>()
    val countryList: LiveData<UiState<List<String>>> = _countryList

    private val _recipeTitle = MutableLiveData<UiState<String>>()
    val recipeTitle: LiveData<UiState<String>> = _recipeTitle

    fun getRecipeTitle() {
        _recipeTitle.value = UiState.Loading
        viewModelScope.launch {
            _recipeTitle.value = repo.fetchRecipeTitle()
        }
    }

    fun getRandomeRecipes(country : String ?= null) {
       //if( _recipeState.value == null) {
           _recipeState.value = UiState.Loading
           viewModelScope.launch {
               try {
                   val response: Response<ResipeResponse> = if(country.isNullOrEmpty()) {

                       repo.getRecipes()

                   } else {
                       repo.getRecipesByCountry(country)

                   }


                   if (response.isSuccessful) {
                       val body = response.body()
                       if (body != null) {
                           _recipeState.value = UiState.Success(body.results)
                       } else {
                           _recipeState.value = UiState.Error("Empty response body")
                       }
                   } else {
                       // Handle API error (like 404, 500)
                       val errorMessage = response.errorBody()?.string() ?: "Unknown API error"
                       _recipeState.value = UiState.Error("Error : $errorMessage")
                   }

               } catch (e: Exception) {
                   // Handle exceptions (like no internet, timeout)
                   _recipeState.value = UiState.Error(e.localizedMessage ?: "An error occurred")
               }
           }
     //  }


    }

    fun getCountry() {
        // delay(10000)
        val countries = repo.getCountry()
        _countryList.value = UiState.Success(countries)
    }
}

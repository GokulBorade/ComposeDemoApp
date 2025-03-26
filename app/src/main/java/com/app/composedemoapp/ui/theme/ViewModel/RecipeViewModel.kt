package com.app.composedemoapp.ui.theme.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.composedemoapp.ui.theme.Model.RecipeItem
import com.app.composedemoapp.ui.theme.Model.ResipeResponse
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

    fun getRandomeRecipes() {
            _recipeState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response: Response<ResipeResponse> = repo.getRecipes()

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


    }
}

package com.app.composedemoapp.ui.theme.Model

data class ResipeResponse(
    var results : List<RecipeItem>,
    var offset : String,
    var number : String,
    var totalResults : String
)
data class RecipeItem(
    var id : String,
    var title : String,
    var image : String,
    var imageType : String,
)
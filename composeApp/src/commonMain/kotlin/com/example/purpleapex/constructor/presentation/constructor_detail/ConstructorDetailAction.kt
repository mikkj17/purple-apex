package com.example.purpleapex.constructor.presentation.constructor_detail

sealed interface ConstructorDetailAction {
    data class OnSearchQueryChange(val query: String) : ConstructorDetailAction
    data object OnBackClick : ConstructorDetailAction
    data object OnRetryClick : ConstructorDetailAction
    data class OnGrandPrixClick(val season: Int, val round: Int) : ConstructorDetailAction
}

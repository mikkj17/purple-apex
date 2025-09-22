package com.example.purpleapex.constructor.presentation.constructor_detail

interface ConstructorDetailAction {
    data class OnSearchQueryChange(val query: String) : ConstructorDetailAction
    data object OnBackClick : ConstructorDetailAction
}

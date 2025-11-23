package com.example.purpleapex.qualifying.presentation.qualifying_detail

sealed interface QualifyingDetailAction {
    data object OnBackClick : QualifyingDetailAction
    data object OnRetryClick : QualifyingDetailAction
}

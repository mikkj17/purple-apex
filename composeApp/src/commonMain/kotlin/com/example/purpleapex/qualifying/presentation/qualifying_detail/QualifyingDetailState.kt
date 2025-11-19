package com.example.purpleapex.qualifying.presentation.qualifying_detail

import com.example.purpleapex.qualifying.domain.QualifyingDetail

data class QualifyingDetailState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val qualifying: QualifyingDetail? = null,
)

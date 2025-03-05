package com.example.purpleapex

import androidx.compose.runtime.Composable
import com.example.purpleapex.driver.presentation.driver_list.DriverListScreenRoot
import com.example.purpleapex.driver.presentation.driver_list.DriverListViewModel
import com.example.purpleapex.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<DriverListViewModel>()
    AppTheme {
        DriverListScreenRoot(
            viewModel = viewModel,
            onDriverClick = { }
        )
    }
}

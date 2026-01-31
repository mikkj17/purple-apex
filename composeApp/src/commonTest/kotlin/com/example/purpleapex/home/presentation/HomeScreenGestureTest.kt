package com.example.purpleapex.home.presentation

import androidx.compose.ui.test.*
import com.example.purpleapex.news.presentation.NewsListState
import com.example.purpleapex.search.presentation.SearchAction
import com.example.purpleapex.search.presentation.SearchState
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HomeScreenGestureTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun swipe_down_shows_search_overlay() = runComposeUiTest {
        var overlayShown = false
        val searchState = SearchState(showSearchOverlay = false)
        val newsState = NewsListState()

        setContent {
            HomeScreen(
                newsState = newsState,
                searchState = searchState,
                onNewsAction = {},
                onSearchAction = { action ->
                    if (action is SearchAction.OnToggleSearchOverlay) {
                        overlayShown = action.show
                    }
                }
            )
        }

        // Perform swipe down
        onRoot().performTouchInput {
            swipeDown(startY = 10f, endY = 400f)
        }

        assertTrue(overlayShown, "Overlay should be shown after swipe down")
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun swipe_up_hides_search_overlay() = runComposeUiTest {
        var overlayShown = true
        val searchState = SearchState(showSearchOverlay = true)
        val newsState = NewsListState()

        setContent {
            HomeScreen(
                newsState = newsState,
                searchState = searchState,
                onNewsAction = {},
                onSearchAction = { action ->
                    if (action is SearchAction.OnToggleSearchOverlay) {
                        overlayShown = action.show
                    }
                }
            )
        }

        // Perform swipe up
        onRoot().performTouchInput {
            swipeUp(startY = 400f, endY = 10f)
        }

        assertFalse(overlayShown, "Overlay should be hidden after swipe up")
    }
}

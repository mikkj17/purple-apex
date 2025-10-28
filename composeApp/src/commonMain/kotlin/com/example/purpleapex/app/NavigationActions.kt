package com.example.purpleapex.app

/**
 * Small abstraction to make bottom bar navigation behavior testable across KMP targets.
 */
interface TabNavigator {
    /**
     * Pops the back stack up to [route]. Returns true if something was popped, false otherwise.
     */
    fun popBackStack(route: Route, inclusive: Boolean = false): Boolean

    /**
     * Navigates to the provided tab [graph], restoring/creating its inner stack as needed.
     */
    fun navigateToGraph(graph: Route)
}

/**
 * Implements the bottom bar navigation policy used by the app.
 *
 * - When [reselected] is true: try to pop to the tab's [root]. If the root isn't present,
 *   navigate to the tab [graph] to restore/create its stack.
 * - When [reselected] is false (switching tabs): navigate to the tab [graph].
 */
fun handleBottomBarNavigation(
    navigator: TabNavigator,
    graph: Route,
    root: Route,
    reselected: Boolean,
) {
    if (reselected) {
        val popped = navigator.popBackStack(root, inclusive = false)
        if (!popped) {
            navigator.navigateToGraph(graph)
        }
    } else {
        navigator.navigateToGraph(graph)
    }
}

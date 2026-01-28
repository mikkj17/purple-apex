package com.example.purpleapex.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private class FakeNavigator : TabNavigator {
    data class PopCall(val route: Route, val inclusive: Boolean)

    val popCalls = mutableListOf<PopCall>()
    val navigateGraphCalls = mutableListOf<Route>()

    var popResultQueue: ArrayDeque<Boolean> = ArrayDeque()

    override fun popBackStack(route: Route, inclusive: Boolean): Boolean {
        popCalls += PopCall(route, inclusive)
        return if (popResultQueue.isEmpty()) false else popResultQueue.removeFirst()
    }

    override fun navigateToGraph(graph: Route) {
        navigateGraphCalls += graph
    }
}

class BottomBarNavigationTest {

    @Test
    fun switching_tabs_navigates_to_graph_only() {
        val fake = FakeNavigator()

        handleBottomBarNavigation(
            navigator = fake,
            graph = Route.RacingGraph,
            root = Route.Racing,
            reselected = false,
        )

        // Should navigate to graph once
        assertEquals(1, fake.navigateGraphCalls.size)
        assertEquals(Route.RacingGraph, fake.navigateGraphCalls.first())
        // Should not attempt to pop when not reselected
        assertTrue(fake.popCalls.isEmpty())
    }

    @Test
    fun reselection_pops_to_root_when_present_without_navigation() {
        val fake = FakeNavigator().apply {
            // Simulate that popBackStack will succeed
            popResultQueue.addLast(true)
        }

        handleBottomBarNavigation(
            navigator = fake,
            graph = Route.RacingGraph,
            root = Route.Racing,
            reselected = true,
        )

        // Verify one pop call with inclusive = false
        assertEquals(1, fake.popCalls.size)
        val call = fake.popCalls.single()
        assertEquals(Route.Racing, call.route)
        assertFalse(call.inclusive)

        // No navigation should occur when pop succeeded
        assertTrue(fake.navigateGraphCalls.isEmpty())
    }

    @Test
    fun reselection_navigates_to_graph_when_root_absent() {
        val fake = FakeNavigator().apply {
            // Simulate that popBackStack will fail (root not in stack)
            popResultQueue.addLast(false)
        }

        handleBottomBarNavigation(
            navigator = fake,
            graph = Route.StandingsGraph,
            root = Route.Standings,
            reselected = true,
        )

        // Verify pop was attempted
        assertEquals(1, fake.popCalls.size)
        val call = fake.popCalls.single()
        assertEquals(Route.Standings, call.route)
        assertFalse(call.inclusive)

        // Fallback navigation to graph should happen
        assertEquals(1, fake.navigateGraphCalls.size)
        assertEquals(Route.StandingsGraph, fake.navigateGraphCalls.first())
    }
}

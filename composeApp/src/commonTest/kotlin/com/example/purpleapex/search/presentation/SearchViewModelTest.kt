package com.example.purpleapex.search.presentation

import com.example.purpleapex.circuit.domain.Circuit
import com.example.purpleapex.circuit.domain.CircuitRepository
import com.example.purpleapex.circuit.domain.Location
import com.example.purpleapex.constructor.domain.Constructor
import com.example.purpleapex.constructor.domain.ConstructorRepository
import com.example.purpleapex.driver.domain.Driver
import com.example.purpleapex.driver.domain.DriverDetail
import com.example.purpleapex.driver.domain.DriverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.*

class FakeDriverRepository : DriverRepository {
    var drivers = emptyList<Driver>()
    var shouldThrow = false

    override suspend fun getDrivers(year: Int?, round: Int?, constructorId: String?): List<Driver> {
        if (shouldThrow) throw Exception("Driver Error")
        return drivers
    }

    override suspend fun getDriver(driverId: String): DriverDetail {
        throw NotImplementedError()
    }
}

class FakeConstructorRepository : ConstructorRepository {
    var constructors = emptyList<Constructor>()
    var shouldThrow = false

    override suspend fun getConstructors(year: Int?, round: Int?, driverId: String?): List<Constructor> {
        if (shouldThrow) throw Exception("Constructor Error")
        return constructors
    }

    override suspend fun getConstructor(constructorId: String): Constructor {
        throw NotImplementedError()
    }
}

class FakeCircuitRepository : CircuitRepository {
    var circuits = emptyList<Circuit>()
    var shouldThrow = false

    override suspend fun getCircuits(year: Int?, round: Int?): List<Circuit> {
        if (shouldThrow) throw Exception("Circuit Error")
        return circuits
    }

    override suspend fun getCircuit(circuitId: String): Circuit {
        throw NotImplementedError()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var driverRepo: FakeDriverRepository
    private lateinit var constructorRepo: FakeConstructorRepository
    private lateinit var circuitRepo: FakeCircuitRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        driverRepo = FakeDriverRepository()
        constructorRepo = FakeConstructorRepository()
        circuitRepo = FakeCircuitRepository()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() {
        viewModel = SearchViewModel(driverRepo, constructorRepo, circuitRepo)
    }

    @Test
    fun `initial state is correct and loads data`() = runTest {
        val drivers = listOf(Driver("1", "Max", "Verstappen", "Dutch", 1))
        val constructors = listOf(Constructor("1", "Red Bull", "Austrian"))
        val circuits = listOf(Circuit("1", Location("Netherlands", "Zandvoort"), "Zandvoort"))

        driverRepo.drivers = drivers
        constructorRepo.constructors = constructors
        circuitRepo.circuits = circuits

        createViewModel()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
        assertEquals(drivers, state.drivers)
        assertEquals(constructors, state.constructors)
        assertEquals(circuits, state.circuits)
    }

    @Test
    fun `search query change updates searched results`() = runTest {
        driverRepo.drivers = listOf(
            Driver("1", "Max", "Verstappen", "Dutch", 1),
            Driver("2", "Lewis", "Hamilton", "British", 44)
        )
        createViewModel()

        viewModel.onAction(SearchAction.OnSearchQueryChange("Max"))

        val state = viewModel.state.value
        assertEquals("Max", state.searchQuery)
        assertEquals(1, state.searchedDrivers.size)
        assertEquals("Max Verstappen", state.searchedDrivers[0].fullName)
    }

    @Test
    fun `empty search query returns no results`() = runTest {
        driverRepo.drivers = listOf(Driver("1", "Max", "Verstappen", "Dutch", 1))
        createViewModel()

        viewModel.onAction(SearchAction.OnSearchQueryChange("Max"))
        assertEquals(1, viewModel.state.value.searchedDrivers.size)

        viewModel.onAction(SearchAction.OnSearchQueryChange(""))
        assertEquals(0, viewModel.state.value.searchedDrivers.size)
    }

    @Test
    fun `search by driver number works`() = runTest {
        driverRepo.drivers = listOf(
            Driver("1", "Max", "Verstappen", "Dutch", 1),
            Driver("2", "Lewis", "Hamilton", "British", 44)
        )
        createViewModel()

        viewModel.onAction(SearchAction.OnSearchQueryChange("44"))

        val state = viewModel.state.value
        assertEquals(1, state.searchedDrivers.size)
        assertEquals("Lewis Hamilton", state.searchedDrivers[0].fullName)
    }

    @Test
    fun `toggle search overlay updates state`() = runTest {
        createViewModel()
        assertFalse(viewModel.state.value.showSearchOverlay)

        viewModel.onAction(SearchAction.OnToggleSearchOverlay(true))
        assertTrue(viewModel.state.value.showSearchOverlay)

        viewModel.onAction(SearchAction.OnToggleSearchOverlay(false))
        assertFalse(viewModel.state.value.showSearchOverlay)
    }

    @Test
    fun `loading error updates state with error message`() = runTest {
        driverRepo.shouldThrow = true
        createViewModel()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals("Driver Error", state.errorMessage)
    }

    @Test
    fun `retry loading data works`() = runTest {
        driverRepo.shouldThrow = true
        createViewModel()
        assertEquals("Driver Error", viewModel.state.value.errorMessage)

        driverRepo.shouldThrow = false
        driverRepo.drivers = listOf(Driver("1", "Max", "Verstappen", "Dutch", 1))

        viewModel.onAction(SearchAction.OnRetryClick)

        val state = viewModel.state.value
        assertNull(state.errorMessage)
        assertEquals(1, state.drivers.size)
    }

    @Test
    fun `search constructor by name`() = runTest {
        constructorRepo.constructors = listOf(
            Constructor("1", "Red Bull Racing", "Austrian"),
            Constructor("2", "Mercedes", "German")
        )
        createViewModel()

        viewModel.onAction(SearchAction.OnSearchQueryChange("Red Bull"))

        val state = viewModel.state.value
        assertEquals(1, state.searchedConstructors.size)
        assertEquals("Red Bull Racing", state.searchedConstructors[0].name)
    }

    @Test
    fun `search circuit by country`() = runTest {
        circuitRepo.circuits = listOf(
            Circuit("1", Location("Netherlands", "Zandvoort"), "Zandvoort"),
            Circuit("2", Location("Monaco", "Monte Carlo"), "Circuit de Monaco")
        )
        createViewModel()

        viewModel.onAction(SearchAction.OnSearchQueryChange("Monaco"))

        val state = viewModel.state.value
        assertEquals(1, state.searchedCircuits.size)
        assertEquals("Circuit de Monaco", state.searchedCircuits[0].name)
    }
}

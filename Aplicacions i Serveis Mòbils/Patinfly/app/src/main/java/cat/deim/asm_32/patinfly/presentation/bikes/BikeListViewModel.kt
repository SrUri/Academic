package cat.deim.asm_32.patinfly.presentation.bikes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.usecase.BikeListUseCase
import kotlinx.coroutines.launch

class BikeListViewModel(
    private val useCase: BikeListUseCase
) : ViewModel() {

    private val bicis = MutableStateFlow<List<Bike>>(emptyList())
    val bikes: StateFlow<List<Bike>> = bicis

    init {
        loadBikes()
    }

    private fun loadBikes() {
        viewModelScope.launch {
            bicis.value = useCase.execute()
        }
    }
}

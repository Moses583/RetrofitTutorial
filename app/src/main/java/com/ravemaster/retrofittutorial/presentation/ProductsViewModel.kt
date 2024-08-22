package com.ravemaster.retrofittutorial.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravemaster.retrofittutorial.data.ProductsApiResponseRepository
import com.ravemaster.retrofittutorial.data.Result
import com.ravemaster.retrofittutorial.data.models.Product
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productsApiResponseRepository: ProductsApiResponseRepository
): ViewModel() {
    private val _response = MutableStateFlow<List<Product>>(emptyList())
    val response = _response.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            productsApiResponseRepository.getProductsApiResponse().collectLatest { result->
                when(result){
                    is Result.Failure -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.response?.let { response ->
                            _response.update {
                                response
                            }
                        }
                    }
                }

            }
        }
    }
}
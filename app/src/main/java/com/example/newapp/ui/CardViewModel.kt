package com.example.newapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newapp.data.CardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<List<CardItem>>(emptyList())
    val uiState: StateFlow<List<CardItem>> = _uiState.asStateFlow()

    fun add(cardItem: CardItem){
        viewModelScope.launch {
            _uiState.value = _uiState.value + cardItem
        }
    }

    fun delete(cardItem: CardItem){
        viewModelScope.launch {
            _uiState.value = _uiState.value - cardItem
        }
    }

    fun edit(edited: CardItem) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.map { card ->
                if (card.id == edited.id) {
                    card.copy(title = edited.title, info = edited.info)
                } else {
                    card
                }
            }
        }
    }

}



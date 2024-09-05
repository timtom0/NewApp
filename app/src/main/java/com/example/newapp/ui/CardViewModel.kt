package com.example.newapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newapp.data.CardRepository
import com.example.newapp.model.CardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(private val cardsRepository: CardRepository) : ViewModel() {

    var cardItem:LiveData<List<CardItem>> = cardsRepository.allcards.asLiveData()

    private val _uiState = MutableStateFlow<List<CardItem>>(emptyList())
    val uiState: StateFlow<List<CardItem>> = _uiState.asStateFlow()

    fun add(cardItem: CardItem) {
        viewModelScope.launch {
            cardsRepository.insert(cardItem)
        }
    }

    fun delete(cardItem: CardItem) {
        viewModelScope.launch {
            cardsRepository.delete(cardItem)
        }
    }

    fun edit(edited: CardItem) {
        viewModelScope.launch {
            cardsRepository.update(edited)
        }
    }
}

class CardViewModelFactory(private val repository: CardRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(CardViewModel::class.java))
            return CardViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


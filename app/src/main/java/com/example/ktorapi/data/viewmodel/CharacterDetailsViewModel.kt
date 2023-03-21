package com.example.ktorapi.data.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktorapi.data.dtos.CharacterDetails
import com.example.ktorapi.data.repo.CharacterRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val repository = CharacterRepositoryImpl()
    private val id = savedStateHandle.get<String>("id")

    private val mCharacter = MutableStateFlow<CharacterDetails?>(null)
    val _character get() = mCharacter

    private val _error = MutableStateFlow<String?>(null)

    init {
        Log.i("Details", "DETAILS : ${id} ")
        if (id != null) {
            getCharacterDetails(id = id.toInt())
        }
    }

    private fun getCharacterDetails(id:Int) {
        viewModelScope.launch {
            try {
                val character = repository.getCharacterDetails( id )
                mCharacter.value = character
               // Log.i("Details", "DETAILS : ${character} ")
            } catch (e: Exception) {
                _error.value = e.message.toString()
                //Log.i("Details", "ERROR : ${e.localizedMessage} ")
            }
        }
    }
}
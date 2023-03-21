package com.example.ktorapi.data.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktorapi.data.repo.CharacterRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.ktorapi.data.dtos.Character
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel() : ViewModel(){
    private val repository = CharacterRepositoryImpl()

    private val mList = MutableStateFlow<List<Character>>(listOf())
    val list : StateFlow<List<Character>> get() = mList

    private val _error = MutableStateFlow<String?>(null)

    init {
        getCharacter()
    }

    private fun getCharacter(){
        viewModelScope.launch {
            Log.i("Characters","Loading List")
            try {
                Log.i("Characters","Starting")
                val character = repository.getCharacter()
                mList.value = character
                Log.i("Characters","${character.size}")
                Log.i("Characters","Finished")
            }
            catch (e: Exception){
                _error.value = e.message.toString()

            }
        }
    }
}


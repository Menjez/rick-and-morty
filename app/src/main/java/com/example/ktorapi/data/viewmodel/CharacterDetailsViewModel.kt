package com.example.ktorapi.data.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.ktorapi.data.dtos.CharacterDetails
import com.example.ktorapi.data.repo.CharacterRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URL


class CharacterDetailsViewModel(app: Application, savedStateHandle: SavedStateHandle) : AndroidViewModel(app) {

    private val repository = CharacterRepositoryImpl()
    private val id = savedStateHandle.get<String>("id")

    private val _character = MutableStateFlow<CharacterDetails?>(null)
    val character get() = _character.asStateFlow()

    private val _palette = MutableStateFlow<Palette?>(null)
    val palette get() = _palette.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)

    init {
       // Log.i("Details", "DETAILS : ${id} ")
        if (id != null) {
            getCharacterDetails(id = id.toInt())
        }
    }

    private fun getCharacterDetails(id:Int) {
        viewModelScope.launch {
            try {
                val character = repository.getCharacterDetails( id )
                _character.value = character
                setImageUri(character.image)
               // Log.i("Details", "DETAILS : ${character} ")
            } catch (e: Exception) {
                _error.value = e.message.toString()
                //Log.i("Details", "ERROR : ${e.localizedMessage} ")
            }
        }
    }

    //private fun getContext(): Context = getApplication<Application>().applicationContext

    private fun setImageUri(url: String) {
        Log.i("GET URL", "URL -> $url")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i("GET URL", "Starting")
                val mUrl = URL(url)
                val bitmap = BitmapFactory.decodeStream(mUrl.openConnection().getInputStream())
                _palette.value = Palette.from(bitmap).generate()
                Log.i("GET URL", "Done")
            } catch (e: Exception) {
                Log.i("GET URL", "Error -> ${e.localizedMessage}")
            }

        }
    }

}
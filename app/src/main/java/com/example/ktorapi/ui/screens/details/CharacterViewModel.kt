package com.example.ktorapi.ui.screens.details

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.ktorapi.domain.models.CharacterDetails
import com.example.ktorapi.domain.usecase.GetCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URL

class CharacterViewModel(
    app: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    private val id = savedStateHandle.get<String>("id")
    private val useCase = GetCharacterUseCase()

    private val _character = MutableStateFlow<CharacterDetails?>(null)
    val character get() = _character.asStateFlow()

    private val _palette = MutableStateFlow<Palette?>(null)
    val palette get() = _palette.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)

    init {
        if (id != null) {
            getCharacterDetails(id = id.toInt())
        }
    }

    private fun getCharacterDetails(id: Int) {
        viewModelScope.launch {
            try {
                val character = useCase.getCharacter(id = id)
                _character.value = character
                setImageUri(character.image)
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }

    private fun setImageUri(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val mUrl = URL(url)
                val bitmap = BitmapFactory.decodeStream(mUrl.openConnection().getInputStream())
                _palette.value = Palette.from(bitmap).generate()
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }

}
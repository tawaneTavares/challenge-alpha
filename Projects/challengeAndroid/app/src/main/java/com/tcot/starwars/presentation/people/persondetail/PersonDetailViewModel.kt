package com.tcot.starwars.presentation.people.persondetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcot.starwars.common.Constants.TYPE_PEOPLE
import com.tcot.starwars.common.Resource
import com.tcot.starwars.domain.model.LastView
import com.tcot.starwars.domain.model.getEmptyPerson
import com.tcot.starwars.domain.model.getUrlImage
import com.tcot.starwars.domain.usecase.GetPersonFromDbUseCase
import com.tcot.starwars.domain.usecase.UpdateLastViewUseCase
import com.tcot.starwars.domain.usecase.UpdatePersonFavoredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val getPersonFromDbUseCase: GetPersonFromDbUseCase,
    private val updatePersonFavoredUseCase: UpdatePersonFavoredUseCase,
    private val updateLastViewUseCase: UpdateLastViewUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(PersonDetailState())
    val state: State<PersonDetailState> = _state

    fun getPersonDetail(personId: Int) {
        getPersonFromDbUseCase(personId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val person = result.data ?: getEmptyPerson()
                    updateLastViewUseCase(LastView(person.name, TYPE_PEOPLE, personId, person.getUrlImage() ?: ""))
                    _state.value =
                        PersonDetailState(
                            person = person,
                        )
                }

                is Resource.Error -> {
                    _state.value =
                        PersonDetailState(
                            error = result.message ?: "An unexpected error occurred",
                        )
                }

                is Resource.Loading -> {
                    _state.value = PersonDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun favoritePerson(personId: Int) = viewModelScope.launch {
        updatePersonFavoredUseCase(personId)
    }
}
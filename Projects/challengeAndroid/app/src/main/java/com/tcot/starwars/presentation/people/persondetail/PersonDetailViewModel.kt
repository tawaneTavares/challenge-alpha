package com.tcot.starwars.presentation.people.persondetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcot.starwars.common.Constants.TYPE_PEOPLE
import com.tcot.starwars.common.Resource
import com.tcot.starwars.domain.model.Favorite
import com.tcot.starwars.domain.model.LastView
import com.tcot.starwars.domain.model.getEmptyPerson
import com.tcot.starwars.domain.model.getUrlImage
import com.tcot.starwars.domain.usecase.AddFavoriteUseCase
import com.tcot.starwars.domain.usecase.CheckFavoriteUseCase
import com.tcot.starwars.domain.usecase.DeleteFavoriteUseCase
import com.tcot.starwars.domain.usecase.GetPersonFromDbUseCase
import com.tcot.starwars.domain.usecase.UpdateLastViewUseCase
import com.tcot.starwars.presentation.favorites.CheckFavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val getPersonFromDbUseCase: GetPersonFromDbUseCase,
    private val updateLastViewUseCase: UpdateLastViewUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(PersonDetailState())
    val state: State<PersonDetailState> = _state

    private val _checkFavorite = mutableStateOf(CheckFavoriteState())
    val checkFavorite: MutableState<CheckFavoriteState> = _checkFavorite

    fun getPersonDetail(personId: Int) {
        getPersonFromDbUseCase(personId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val person = result.data ?: getEmptyPerson()
                    updateLastViewUseCase(
                        LastView(
                            person.name,
                            TYPE_PEOPLE,
                            personId,
                            person.getUrlImage() ?: "",
                        ),
                    )
                    checkIsFavorite(person.name)
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

    private fun checkIsFavorite(name: String) {
        checkFavoriteUseCase(name).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val isFavorite = result.data ?: false
                    _checkFavorite.value = CheckFavoriteState(
                        hasResponded = true,
                        isFavorite = isFavorite,
                    )
                }

                is Resource.Error -> {
                    _checkFavorite.value = CheckFavoriteState(
                        hasResponded = true,
                        isFavorite = false,
                    )
                }

                is Resource.Loading -> {
                    _checkFavorite.value = CheckFavoriteState(
                        hasResponded = false,
                        isFavorite = false,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun favoritePerson() = viewModelScope.launch {
        val person = state.value.person
        if (checkFavorite.value.isFavorite) {
            deleteFavoriteUseCase(person.name)
        } else {
            addFavoriteUseCase(
                Favorite(
                    name = person.name,
                    type = TYPE_PEOPLE,
                    id = person.id,
                    imageUrl = person.getUrlImage() ?: "",
                ),
            )
        }
    }
}
package com.tcot.starwars.presentation.people.peoplelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.tcot.starwars.data.local.PersonEntity
import com.tcot.starwars.data.local.toPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    pager: Pager<Int, PersonEntity>,
) : ViewModel() {

    val peoplePagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toPerson() }
        }
        .cachedIn(viewModelScope)
}
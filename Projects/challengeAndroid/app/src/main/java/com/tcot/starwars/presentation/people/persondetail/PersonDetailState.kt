package com.tcot.starwars.presentation.people.persondetail

import com.tcot.starwars.domain.model.Person
import com.tcot.starwars.domain.model.getEmptyPerson

data class PersonDetailState(
    val isLoading: Boolean = false,
    val person: Person = getEmptyPerson(),
    val error: String = "",
)
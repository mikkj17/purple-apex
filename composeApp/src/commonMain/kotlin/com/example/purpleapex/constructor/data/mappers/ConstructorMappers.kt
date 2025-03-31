package com.example.purpleapex.constructor.data.mappers

import com.example.ConstructorsQuery
import com.example.purpleapex.constructor.domain.Constructor

fun ConstructorsQuery.Constructor.toConstructor() = Constructor(
    id = id,
    name = name,
    nationality = nationality,
)

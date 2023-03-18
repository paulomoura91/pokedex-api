package com.pokedex.api.data.model

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Pokemon(
    val number: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val types: List<String>,
    val evolutions: List<Int>
)
package com.pokedex.api.data.repository

import com.pokedex.api.data.model.Pokemon
import org.springframework.data.mongodb.repository.MongoRepository

interface PokemonRepository : MongoRepository<Pokemon, String> {
    fun findByNumber(number: Int): Pokemon?
}
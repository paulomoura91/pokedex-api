package com.pokedex.api

import com.pokedex.api.data.model.Pokemon
import com.pokedex.api.data.repository.PokemonRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController(private val pokemonRepository: PokemonRepository) {

    @GetMapping
    fun getAllPokemons(): ResponseEntity<List<Pokemon>> {
        val pokemons = pokemonRepository.findAll()
        return ResponseEntity.ok(pokemons)
    }

    @GetMapping("/{number}")
    fun getPokemon(@PathVariable("number") number: Int): ResponseEntity<Pokemon> {
        val pokemon = pokemonRepository.findByNumber(number)
        return pokemon?.let { ResponseEntity.ok(pokemon) } ?: ResponseEntity.notFound().build()
    }
}
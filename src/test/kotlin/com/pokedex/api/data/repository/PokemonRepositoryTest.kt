package com.pokedex.api.data.repository

import com.pokedex.api.data.model.Pokemon
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
class PokemonRepositoryTest {

    @Autowired
    private lateinit var pokemonRepository: PokemonRepository

    @BeforeEach
    fun setup() {
        pokemonRepository.insert(
            Pokemon(
                number = 1,
                name = "Bulbasaur",
                description = "There is a plant seed on its back right from the day this Pokémon is born. The seed slowly grows larger.",
                imageUrl = "http://baseurl/001.png",
                types = listOf("grass", "poison"),
                evolutions = listOf(1, 2, 3)
            )
        )
        pokemonRepository.insert(
            Pokemon(
                number = 2,
                name = "Ivysaur",
                description = "When the bulb on its back grows large, it appears to lose the ability to stand on its hind legs.",
                imageUrl = "http://baseurl/002.png",
                types = listOf("grass", "poison"),
                evolutions = listOf(1, 2, 3)
            )
        )
    }

    @Test
    fun `should not be empty`() {
        val pokemonBulbasaur = Pokemon(
            number = 1,
            name = "Bulbasaur",
            description = "There is a plant seed on its back right from the day this Pokémon is born. The seed slowly grows larger.",
            imageUrl = "http://baseurl/001.png",
            types = listOf("grass", "poison"),
            evolutions = listOf(1, 2, 3)
        )
        val pokemonIvysaur = Pokemon(
            number = 2,
            name = "Ivysaur",
            description = "When the bulb on its back grows large, it appears to lose the ability to stand on its hind legs.",
            imageUrl = "http://baseurl/002.png",
            types = listOf("grass", "poison"),
            evolutions = listOf(1, 2, 3)
        )
        assert(pokemonRepository.findByNumber(1) == pokemonBulbasaur)
        assert(pokemonRepository.findByNumber(2) == pokemonIvysaur)
    }

    @AfterEach
    fun clearMongo() = pokemonRepository.deleteAll()
}
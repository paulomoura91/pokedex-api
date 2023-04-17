package com.pokedex.api

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.pokedex.api.data.model.Pokemon
import com.pokedex.api.data.repository.PokemonRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get


@WebMvcTest(PokemonController::class)
class PokemonControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var pokemonRepository: PokemonRepository

    @Test
    fun `Test 'getAllPokemons' endpoint`() {
        val mockedPokemons = listOf(
            Pokemon(
                number = 1,
                name = "Bulbasaur",
                description = "There is a plant seed on its back right from the day this Pokémon is born. The seed slowly grows larger.",
                imageUrl = "http://baseurl/001.png",
                types = listOf("grass", "poison"),
                evolutions = listOf(1, 2, 3)
            ),
            Pokemon(
                number = 2,
                name = "Ivysaur",
                description = "When the bulb on its back grows large, it appears to lose the ability to stand on its hind legs.",
                imageUrl = "http://baseurl/002.png",
                types = listOf("grass", "poison"),
                evolutions = listOf(1, 2, 3)
            )
        )

        `when`(pokemonRepository.findAll()).thenReturn(mockedPokemons)

        val mvcResult = mockMvc.perform(get("/pokemon")).andReturn()
        val body = mvcResult.response.contentAsString
        val pokemons: List<Pokemon> = jacksonObjectMapper().readValue(body)
        assert(mvcResult.response.status == HttpStatus.OK.value())
        assert(pokemons.firstOrNull { it.number == 1 && it.name == "Bulbasaur" } != null)
        assert(pokemons.firstOrNull { it.number == 2 && it.name == "Ivysaur" } != null)
    }

    @Test
    fun `Test 'getPokemon' endpoint with success`() {
        val mockedPokemon = Pokemon(
            number = 1,
            name = "Bulbasaur",
            description = "There is a plant seed on its back right from the day this Pokémon is born. The seed slowly grows larger.",
            imageUrl = "http://baseurl/001.png",
            types = listOf("grass", "poison"),
            evolutions = listOf(1, 2, 3)
        )

        `when`(pokemonRepository.findByNumber(1)).thenReturn(mockedPokemon)

        val mvcResult = mockMvc.perform(get("/pokemon/1")).andReturn()
        val body = mvcResult.response.contentAsString
        val pokemon: Pokemon = jacksonObjectMapper().readValue(body)
        assert(mvcResult.response.status == HttpStatus.OK.value())
        assert(pokemon.number == 1 && pokemon.name == "Bulbasaur")
    }

    @Test
    fun `Test 'getPokemon' endpoint with Error`() {
        val mockedPokemon = Pokemon(
            number = 1,
            name = "Bulbasaur",
            description = "There is a plant seed on its back right from the day this Pokémon is born. The seed slowly grows larger.",
            imageUrl = "http://baseurl/001.png",
            types = listOf("grass", "poison"),
            evolutions = listOf(1, 2, 3)
        )

        `when`(pokemonRepository.findByNumber(1)).thenReturn(mockedPokemon)

        val mvcResult = mockMvc.perform(get("/pokemon/152")).andReturn()
        val body = mvcResult.response.contentAsString
        assert(mvcResult.response.status == HttpStatus.NOT_FOUND.value())
        assertThrows<MismatchedInputException> { jacksonObjectMapper().readValue(body) }
    }
}

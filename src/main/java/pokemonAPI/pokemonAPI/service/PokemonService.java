package pokemonAPI.pokemonAPI.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import pokemonAPI.pokemonAPI.api.model.Pokemon;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PokemonService {

    private List<Pokemon> pokemonList;
    private final String DATA_FILE_PATH = "src/main/resources/Pokemons.json"; 

    public PokemonService() {
        pokemonList = loadPokemonsFromFile();
       
    }

    public Pokemon getPokemon(int id) {
        return pokemonList.stream()
                .filter(pokemon -> id == (pokemon.getId()))
                .findFirst()
                .orElse(null);
    }

    public List<Pokemon> getPokemons() {
        return pokemonList;
    }

    public Pokemon createPokemon(Pokemon pokemon) {

        pokemonList.add(pokemon);
        savePokemonsToFile();
        return pokemon;
    }

    public boolean deletePokemon(int id) {
        Pokemon deletedPokemon = getPokemon(id);
        if (deletedPokemon != null) {
            pokemonList.remove(deletedPokemon);
            savePokemonsToFile();
            return true;
        }
        return false;
    }

    public void markPokemonAsCaught(int id, boolean caught) {
        Pokemon pokemon = getPokemon(id);
        if (pokemon != null) {
            pokemon.setCaught(caught);
            savePokemonsToFile();
        }
    }

    private List<Pokemon> loadPokemonsFromFile() {
    ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = getClass().getResourceAsStream("/Pokemons.json")) {
            if (inputStream != null) {
                return objectMapper.readValue(inputStream, new TypeReference<List<Pokemon>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading from classpath resource: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private void savePokemonsToFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(DATA_FILE_PATH);

        try {
            objectMapper.writeValue(file, pokemonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public List<Integer> getCaughtPokemonIds() {
        return pokemonList.stream()
                .filter(Pokemon::isCaught)
                .map(Pokemon::getId)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> updatePokemonDescription(@PathVariable int id, @RequestParam String description) {
        Pokemon pokemon = getPokemon(id);

        if (pokemon != null) {
            pokemon.setDescription(description);
            savePokemonsToFile();
            return ResponseEntity.ok("Pokemon description updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon with ID " + id + " not found");
        }
    }

}


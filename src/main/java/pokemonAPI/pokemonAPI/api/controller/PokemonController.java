package pokemonAPI.pokemonAPI.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pokemonAPI.pokemonAPI.api.model.Pokemon;
import pokemonAPI.pokemonAPI.service.PokemonService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PokemonController {
    private PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/pokemons")
    public List<Pokemon> getPokemons() {
        return pokemonService.getPokemons();
    }

    @GetMapping("/pokemon")
    public Pokemon getPokemon(@RequestParam int id) {

        return pokemonService.getPokemon(id);
    }

    @PostMapping("/pokemon")
    public Pokemon createPokemon(@RequestBody Pokemon pokemon) {
        System.out.println("Pokemon body" + pokemon);
        return pokemonService.createPokemon(pokemon);
    }

    @DeleteMapping("/pokemon")
    public ResponseEntity<String> deletePokemon(@RequestParam int id) {
        boolean deleted = pokemonService.deletePokemon(id);
    
        if (deleted) {
            return ResponseEntity.ok("Pokemon släpptes fri");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon with ID " + id + " not found");
        }
    }

    @GetMapping("/caught-pokemon-ids")
        public List<Integer> getCaughtPokemonIds() {
            return pokemonService.getCaughtPokemonIds();
        }
    

    @PatchMapping("/pokemon/{id}")
    public ResponseEntity<String> updatePokemonDescription(@PathVariable int id, @RequestParam String description) {
        ResponseEntity<String> response = pokemonService.updatePokemonDescription(id, description);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.ok("Pokemon description updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon with ID " + id + " not found");
        }
    }
}
    
    
    


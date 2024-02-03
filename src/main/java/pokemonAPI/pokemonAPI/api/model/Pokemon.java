package pokemonAPI.pokemonAPI.api.model;

import java.util.List;


public class Pokemon {
    private int id;
    private String name;
    private String description;
    private List<String> type;
    private boolean caught;
    
    public Pokemon(){
        
    }

    public Pokemon(int id, String name, String description, List<String> type, boolean caught) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.caught = caught;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public boolean isCaught() {
        return caught;
    }

    public void setCaught(boolean caught) {
        this.caught = caught;
    }
}

package database.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodDTO {

    private long id;
    private String name;
    private String description;
    private int priceCents;
    private String category;

    @JsonCreator
    public FoodDTO(
    		@JsonProperty("id") long id,
    		@JsonProperty("name" )String name,
    		@JsonProperty("description") String description,
    		@JsonProperty("priceCents") int priceCents,
    		@JsonProperty("category") String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priceCents = priceCents;
        this.category = category;
    }
    


    public long getId()           { return id; }
    public String getName()       { return name; }
    public String getDescription(){ return description; }
    public int getPriceCents()    { return priceCents; }
    public String getCategory()   { return category; }

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + (priceCents / 100.0);
    }
}
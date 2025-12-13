package database.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodDTO {

    private long id;
    private String name;
    private String description;
    private int priceCents;
    private String category;
    private int quantity = 1;

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
    

    public FoodDTO(
    		long id,
    		String name,
    		String description,
    		int priceCents,
    		String category,
    		int quantity){
        this.id = id;
        this.name = name;
        this.description = description;
        this.priceCents = priceCents;
        this.category = category;
        this.quantity = quantity;
    }
    


    public long getId()           { return id; }
    public String getName()       { return name; }
    public String getDescription(){ return description; }
    public int getPriceCents()    { return priceCents; }
    public String getCategory()   { return category; }
    public int getQuantity() {return quantity;}
    
    public void updateQuantity(int update) {quantity+=update;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + (priceCents / 100.0);
    }
}
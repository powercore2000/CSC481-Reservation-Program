package backend.controllers;

import database.dto.*;
import database.queries.ReservationQueries;
import database.queries.RestaurantQueries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.models.FoodModel;
import backend.models.ReservationModel;
import backend.models.RestaurantModel;
import backend.services.FoodMapper;
import backend.services.ReservationMapper;
import backend.services.RestaurantMapper;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	
    // Handles which restaurant the browsing user has selected to make a reservation at
	private static long selectedRestaurantId = 1;
	
	public static long getSelectedRestaurantID() { return selectedRestaurantId;}


	public static void setSelectedRestaurantID(long id) {selectedRestaurantId = id;}
	
	private static FoodDTO selectedFood;

	public static FoodDTO getSelectedFood() {
	    return selectedFood;
	}

	public static void setSelectedFoodID(FoodDTO newFood) {
	    selectedFood = newFood;
	}

	//--------------update----------------
	
	
	@GetMapping("/byTag/{tag}")
	public static List<RestaurantDTO> getRestaurantsByTag(@PathVariable String tag) {

	    List<RestaurantModel> models = RestaurantQueries.findByTag(tag);
	    List<RestaurantDTO> dtos = new ArrayList<>();


	    for (RestaurantModel r : models) {
	        dtos.add(RestaurantMapper.toDTO(r));
	       
	    }

	    return dtos;
	}
	
	private static String currentTagFilter = null;

	public static void setCurrentTagFilter(String tag) {
	    currentTagFilter = tag;
	}

	public static String getCurrentTagFilter() {
	    return currentTagFilter;
	}


	// -----------------------------------------
	
	
    @GetMapping("/listAll")
    public static List<RestaurantDTO> getAllRestaurants() {
    	List<RestaurantDTO> allRestaurants = new ArrayList<RestaurantDTO>();
       
       List<RestaurantModel> allModels = RestaurantQueries.findAll();
       
       for (RestaurantModel r : allModels) {
    	   
    	   allRestaurants.add(RestaurantMapper.toDTO(r));
       }
       
       return allRestaurants;
    }
    
    /**
	 * REST endpoint to select the current restaurant by its ID.
	 * This mirrors the existing selectRestaurant(RestaurantDTO) behavior
	 * but is easier to call from thin HTTP clients.
	 */
	@GetMapping("/selectById/{id}")
	public static ResponseEntity<Boolean> selectRestaurantById(@PathVariable("id") long id) {
        System.out.println("[RestaurantController] Selected restaurant by id = " + id);
        setSelectedRestaurantID(id);
		return ResponseEntity.ok(true);
	}

    @GetMapping("/getCurrentRestaurant")
    public static RestaurantDTO getCurrentRestaurant(){
    	
    	Optional<RestaurantModel> response = RestaurantQueries.findRestaurantById(selectedRestaurantId);
		
    	if(response.isEmpty())
    		return null;
		
        return RestaurantMapper.toDTO( response.get());
    }
    
    public static long setCurrentRestaurantByReservation(ReservationDTO reservation){
    	
    	try {
    	long id = ReservationMapper.toModel(reservation).getRestaurantId();
    	setSelectedRestaurantID(id);
    	return id;
    	} catch (Exception e) {
    		System.out.println(e);
    	}
		
        return -1;
    }

    
    @PostMapping("/selectRestaurant")
    public static ResponseEntity<Boolean> selectRestaurant(@RequestBody RestaurantDTO restaurantDto) {

    		selectedRestaurantId = restaurantDto.getId();
    		
    		System.out.println("Set retaurant id to " + getSelectedRestaurantID());
    		
            return ResponseEntity.ok(true);

    }

    @GetMapping("/allRestaurantFood")
    public static List<FoodDTO> allRestaurantFood(){
    	
    	List<FoodModel> foodModels = RestaurantQueries.FoodForRestaurant(selectedRestaurantId);
    	 List<FoodDTO> displayFood  = new ArrayList<FoodDTO>(); 
    	 
    	for (FoodModel f : foodModels) {
     	   
    		displayFood.add(FoodMapper.toDTO(f));
        }
        
        return displayFood;
    }
    

 }

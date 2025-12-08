package backend.controllers;

import database.dto.*;


import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.models.ReservationModel;
import backend.services.ReservationMapper;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	
	
    @GetMapping("/listAll")
    public static ArrayList<RestaurantDTO> getAllReservations() {
        return new ArrayList<RestaurantDTO>();
    }
    

    @PostMapping("/updateSchedule")
    public ResponseEntity<Boolean> createReservation(@RequestBody RestaurantDTO restaurantDto) {

            return ResponseEntity.ok(true);

    }
    
	
}

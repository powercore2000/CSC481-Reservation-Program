package com.csc481.reservationSys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
	
	@GetMapping("/welcome")
	public String welcome() {
		
		return "Welcome to hell";
	}

}

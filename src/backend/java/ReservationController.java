package backend.java;

import backend.java.models.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
	
	@GetMapping("/welcome")
	public String welcome() {
		
		return "Welcome to hell";
	}
	
	@RestController
	@RequestMapping("/api/users")
	public class UserController {
	    @PostMapping
	    public String createUser(@RequestBody User user) {
	        return "User created: " + user.getName();
	    }
	}

}

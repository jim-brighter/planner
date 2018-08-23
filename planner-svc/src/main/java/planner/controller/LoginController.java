package planner.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
	public Principal user(Principal user) {
		return user;
	}
}

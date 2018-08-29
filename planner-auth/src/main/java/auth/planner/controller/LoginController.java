package auth.planner.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public Map<String, String> token(HttpSession session) {
		return Collections.singletonMap("token", session.getId());
	}
}

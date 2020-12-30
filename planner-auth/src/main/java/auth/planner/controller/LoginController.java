package auth.planner.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public Map<String, String> token(HttpSession session) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("token", session.getId());
        response.put("token_expiration", "" + (session.getMaxInactiveInterval() * 1000 + System.currentTimeMillis()));

        return response;
    }
}

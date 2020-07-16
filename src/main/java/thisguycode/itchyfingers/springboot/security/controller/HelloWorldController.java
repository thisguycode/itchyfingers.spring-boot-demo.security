package thisguycode.itchyfingers.springboot.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

  @GetMapping("/hey")
  public String greeting(Authentication authentication) {
    var userDetails = (UserDetails) authentication.getPrincipal();
    return String.format("Hello %s! How is it going?", userDetails.getUsername());
  }
}

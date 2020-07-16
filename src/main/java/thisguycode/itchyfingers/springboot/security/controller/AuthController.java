package thisguycode.itchyfingers.springboot.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thisguycode.itchyfingers.springboot.security.model.AuthTokenResponse;
import thisguycode.itchyfingers.springboot.security.model.LoginRequest;
import thisguycode.itchyfingers.springboot.security.service.AuthService;

@RestController
@RequestMapping("/api")
public class AuthController {

  private AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public AuthTokenResponse login(@RequestBody LoginRequest loginRequest) {
    return authService.authenticate(loginRequest);
  }
}

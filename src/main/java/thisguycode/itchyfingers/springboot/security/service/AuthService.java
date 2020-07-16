package thisguycode.itchyfingers.springboot.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import thisguycode.itchyfingers.springboot.security.model.AuthTokenResponse;
import thisguycode.itchyfingers.springboot.security.model.LoginRequest;
import thisguycode.itchyfingers.springboot.security.utils.JwtUtils;

@Service
public class AuthService {

  private AuthenticationManager authenticationManager;
  private CustomUserDetailsService userDetailsService;


  @Autowired
  public AuthService(AuthenticationManager authenticationManager,
      CustomUserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  public AuthTokenResponse authenticate(LoginRequest loginRequest) {
    var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwtToken = JwtUtils.generateToken(loginRequest.getUsername());
    return new AuthTokenResponse(jwtToken, AuthTokenResponse.BEARER_TOKEN_TYPE);
  }

  public void authorizeJwt(String token) {
    var claims = JwtUtils.getClaims(token);
    if (claims.isPresent()) {
      var userDetails = userDetailsService.loadUserByUsername(claims.get().getSubject());
      var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
          userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // UserDetails userDetails = customUserDetailsService.loadUserById(userId);
      // UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      // authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    }
  }
}

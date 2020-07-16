package thisguycode.itchyfingers.springboot.security.filter;

import static org.springframework.util.StringUtils.hasText;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import thisguycode.itchyfingers.springboot.security.service.AuthService;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  @Autowired
  private AuthService authService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    var token = getJwtFromRequest(request);
    if (token.isPresent()) {
      authService.authorizeJwt(token.get());
    }

    filterChain.doFilter(request, response);
  }

  private Optional<String> getJwtFromRequest(HttpServletRequest request) {
    String jwt = null;
    String bearerToken = request.getHeader("Authorization");
    if (hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      jwt = bearerToken.substring(7, bearerToken.length());
    }
    return Optional.ofNullable(jwt);
  }
}

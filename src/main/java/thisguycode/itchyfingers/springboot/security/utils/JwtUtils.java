package thisguycode.itchyfingers.springboot.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {

  public static Key getKey() {
    // ##### Generate Secret Key #####
    // Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    // String secretString = Encoders.BASE64.encode(key.getEncoded());
    // log.info("Secret String - {}", secretString);

    var secretString = "KgTTKuXSQWm/iCkX/uxr0OhBPI3YpvhnLIDL33BjAqzBpQmoPbfO34H0bqGpp6NHFl8/qQSRkeEQhs+mqIe1XQ==";
    return Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
  }

  public static String generateToken(String subject) {
    return generateToken(subject, null);
  }

  public static String generateToken(String subject, Map<String, Object> customClaims) {
    log.info("generateToken - subject:{}", subject);

    Claims claims = Jwts.claims();
    claims.setSubject(subject);
    claims.setAudience("WEB");
    claims.setIssuer("ItchyFingers");
    claims.setIssuedAt(Date.from(Instant.now()));
    claims.setExpiration(Date.from(Instant.now().plusSeconds(Duration.ofMinutes(10).toSeconds())));
    claims.setId(UUID.randomUUID().toString());
    if (!Objects.isNull(customClaims)) {
      claims.putAll(customClaims);
    }

    return Jwts.builder().setClaims(claims).signWith(getKey()).compact();
  }

  public static boolean isValidToken(String jwt) {
    return getClaims(jwt).isPresent();
  }

  public static Optional<Claims> getClaims(String jwt) {
    log.info("getClaims - jwt:{}", jwt);

    try {
      var jwts = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt);
      //OK, we can trust this JWT

      log.info("getClaims - JWT Validation Successful.");
      return Optional.of(jwts.getBody());
    } catch (JwtException e) {

      log.error("getClaims - Invalid Jwt Token", e);
      return Optional.empty();
    }
  }
}


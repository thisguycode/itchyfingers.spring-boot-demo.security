package thisguycode.itchyfingers.springboot.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenResponse {

  public static final String BEARER_TOKEN_TYPE = "Bearer";

  private String token;
  private String tokenType;
}

package com.github.simondan.svl.server.security;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.simondan.svl.server.auth.User;

import java.time.*;
import java.util.Date;

/**
 * @author Simon Danner, 27.09.2019
 */
public final class JWTUtil
{
  static final String USER_NAME_CLAIM = "claim_user_name";

  private static final Algorithm ALGORITHM = Algorithm.HMAC256("TODO");
  private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM).build();

  private JWTUtil()
  {
  }

  public static String createJwtForUser(User pUser)
  {
    final Date expirationDate = Date.from(Instant.now().plus(Duration.ofDays(1)));

    return JWT.create()
        .withExpiresAt(expirationDate)
        .withClaim(USER_NAME_CLAIM, pUser.getValue(User.NAME).toString())
        .sign(ALGORITHM);
  }

  public static DecodedJWT decodeJwt(String pJwt)
  {
    return VERIFIER.verify(pJwt);
  }
}

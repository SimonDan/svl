package com.github.simondan.svl.server.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.simondan.svl.server.auth.UserName;
import com.github.simondan.svl.server.auth.exceptions.BadUserNameException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;

/**
 * @author Simon Danner, 20.09.2019
 */
@SecureBoundary
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecureRequestBoundary implements ContainerRequestFilter
{
  private static final int TOKEN_PREFIX_LENGTH = "Bearer".length();

  @Inject
  private RequestSecurityContext securityContext;

  @Override
  public void filter(ContainerRequestContext pRequestContext)
  {
    try
    {
      final String token = _retrieveTokenFromHeader(pRequestContext);
      final DecodedJWT decoded = JWTUtil.decodeJwt(token);
      final String userName = decoded.getClaim(JWTUtil.USER_NAME_CLAIM).asString();

      securityContext.setAuthenticatedUserName(UserName.of(userName));
    }
    catch (JWTVerificationException | BadUserNameException pE)
    {
      pRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }

  private static String _retrieveTokenFromHeader(ContainerRequestContext pRequestContext) throws JWTVerificationException
  {
    final String authHeader = pRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

    if (authHeader == null)
      throw new JWTVerificationException("No authorization token in header!");

    return authHeader.substring(TOKEN_PREFIX_LENGTH).trim();
  }
}

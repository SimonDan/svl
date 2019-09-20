package com.github.simondan.svl.server.security;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.jersey.core.util.Priority;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.Objects;

/**
 * @author Simon Danner, 20.09.2019
 */
@SecureBoundary
@Provider
@Priority(Priorities.AUTHENTICATION)
public final class SecureRequestBoundary implements ContainerRequestFilter
{
  private static final int TOKEN_PREFIX_LENGTH = "Bearer".length();
  private static final Algorithm ALGORITHM = Algorithm.HMAC256("TODO");
  private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM)
      .withIssuer("auth0")
      .build();

  @Override
  public void filter(ContainerRequestContext pRequestContext)
  {
    final String token = _retrieveTokenFromHeader(pRequestContext);

    try
    {
      final DecodedJWT decoded = VERIFIER.verify(token);
      final String user = decoded.getSubject();
      final String role = decoded.getId(); //TODO

      pRequestContext.setSecurityContext(new _UserContext(user, role));
    }
    catch (JWTVerificationException pE)
    {
      pRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }

  private static String _retrieveTokenFromHeader(ContainerRequestContext pRequestContext)
  {
    final String authHeader = pRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    return authHeader.substring(TOKEN_PREFIX_LENGTH).trim();
  }

  private static class _UserContext implements SecurityContext
  {
    private final String authenticatedUser;
    private final ERole userRole;

    _UserContext(String pAuthenticatedUser, String pUserRole)
    {
      authenticatedUser = Objects.requireNonNull(pAuthenticatedUser);
      userRole = _userRoleFromString(pUserRole);
    }

    @Override
    public Principal getUserPrincipal()
    {
      return () -> authenticatedUser;
    }

    @Override
    public boolean isUserInRole(String pRole)
    {
      return userRole == _userRoleFromString(pRole);
    }

    @Override
    public boolean isSecure()
    {
      return true;
    }

    @Override
    public String getAuthenticationScheme()
    {
      return SecurityContext.DIGEST_AUTH;
    }

    private static ERole _userRoleFromString(String pRole)
    {
      try
      {
        return ERole.valueOf(pRole);
      }
      catch (IllegalArgumentException pE)
      {
        throw new RuntimeException("Role " + pRole + " is no valid user role!");
      }
    }
  }
}

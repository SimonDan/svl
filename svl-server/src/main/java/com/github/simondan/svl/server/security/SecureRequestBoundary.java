package com.github.simondan.svl.server.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.security.Principal;

/**
 * @author Simon Danner, 20.09.2019
 */
@SecureBoundary
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecureRequestBoundary implements ContainerRequestFilter
{
  private static final int TOKEN_PREFIX_LENGTH = "Bearer".length();

  @Override
  public void filter(ContainerRequestContext pRequestContext)
  {
    try
    {
      final String token = _retrieveTokenFromHeader(pRequestContext);
      final DecodedJWT decoded = JWTUtil.decodeJwt(token);
      final int userId = decoded.getClaim(JWTUtil.USER_ID_CLAIM).asInt();
      final ERole role = _userRoleFromString(decoded.getClaim(JWTUtil.USER_ROLE_CLAIM).asString());

      pRequestContext.setSecurityContext(new _UserContext(userId, role));
    }
    catch (JWTVerificationException pE)
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

  private static class _UserContext implements SecurityContext
  {
    private final int authenticatedUserId;
    private final ERole userRole;

    _UserContext(int pAuthenticatedUserId, ERole pUserRole)
    {
      authenticatedUserId = pAuthenticatedUserId;
      userRole = pUserRole;
    }

    @Override
    public Principal getUserPrincipal()
    {
      return () -> String.valueOf(authenticatedUserId);
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
  }
}

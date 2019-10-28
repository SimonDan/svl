package com.github.simondan.svl.server.security;

import com.github.simondan.svl.server.auth.UserName;
import org.glassfish.jersey.process.internal.RequestScoped;

import java.util.*;

/**
 * @author Simon Danner, 13.10.2019
 */
@RequestScoped
public class RequestSecurityContext
{
  private UserName authenticatedUserName;

  public Optional<UserName> getAuthenticatedUserName()
  {
    return Optional.ofNullable(authenticatedUserName);
  }

  void setAuthenticatedUserName(UserName pAuthenticatedUserName)
  {
    authenticatedUserName = Objects.requireNonNull(pAuthenticatedUserName, "The authenticated user name must not be null!");
  }
}

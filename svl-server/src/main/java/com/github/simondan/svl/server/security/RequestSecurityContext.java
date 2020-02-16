package com.github.simondan.svl.server.security;

import com.github.simondan.svl.communication.auth.UserName;

import javax.enterprise.context.RequestScoped;
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

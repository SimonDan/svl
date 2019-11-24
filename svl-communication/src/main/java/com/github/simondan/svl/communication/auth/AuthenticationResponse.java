package com.github.simondan.svl.communication.auth;

/**
 * @author Simon Danner, 25.10.2019
 */
public final class AuthenticationResponse
{
  private final String token;
  private final String nextPassword;
  private final EUserRole userRole;

  public AuthenticationResponse(String pToken, String pNextPassword, EUserRole pUserRole)
  {
    token = pToken;
    nextPassword = pNextPassword;
    userRole = pUserRole;
  }

  public String getToken()
  {
    return token;
  }

  public String getNextPassword()
  {
    return nextPassword;
  }

  public EUserRole getUserRole()
  {
    return userRole;
  }
}

package com.github.simondan.svl.communication.auth;

/**
 * @author Simon Danner, 25.10.2019
 */
public final class AuthenticationResponse
{
  private final String token;
  private final String nextPassword;

  public AuthenticationResponse(String pToken, String pNextPassword)
  {
    token = pToken;
    nextPassword = pNextPassword;
  }

  public String getToken()
  {
    return token;
  }

  public String getNextPassword()
  {
    return nextPassword;
  }
}

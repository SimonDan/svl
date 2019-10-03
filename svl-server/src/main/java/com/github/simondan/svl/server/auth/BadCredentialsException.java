package com.github.simondan.svl.server.auth;

/**
 * @author Simon Danner, 27.09.2019
 */
public class BadCredentialsException extends Exception
{
  public BadCredentialsException(String pUserName)
  {
    super("Authentication failed for user " + pUserName + " due to bad credentials!");
  }
}

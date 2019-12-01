package com.github.simondan.svl.server.auth.exceptions;

import com.github.simondan.svl.communication.auth.UserName;

/**
 * @author Simon Danner, 27.09.2019
 */
public class BadCredentialsException extends Exception
{
  public BadCredentialsException(UserName pUserName)
  {
    super("Authentication failed for user " + pUserName + " due to bad credentials!");
  }
}

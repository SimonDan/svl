package com.github.simondan.svl.server.auth.exceptions;

import com.github.simondan.svl.communication.auth.UserName;

/**
 * @author Simon Danner, 30.11.2019
 */
public class UserNotFoundException extends Exception
{
  public UserNotFoundException(UserName pUserName)
  {
    super("User with name " + pUserName + " does not exist!");
  }
}

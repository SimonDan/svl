package com.github.simondan.svl.server.auth.exceptions;

import com.github.simondan.svl.communication.auth.UserName;

/**
 * @author Simon Danner, 13.10.2019
 */
public class UserAlreadyExistsException extends Exception
{
  public UserAlreadyExistsException(UserName pUserName)
  {
    super("The user " + pUserName + " already exists!");
  }
}

package com.github.simondan.svl.server.auth.exceptions;

/**
 * @author Simon Danner, 11.10.2019
 */
public class BadUserNameException extends Exception
{
  public BadUserNameException(Throwable pCause)
  {
    super(pCause);
  }
}

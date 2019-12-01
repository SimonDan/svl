package com.github.simondan.svl.communication.auth;

/**
 * @author Simon Danner, 11.10.2019
 */
public class BadUserNameException extends Exception
{
  public BadUserNameException(Throwable pCause)
  {
    super(pCause);
  }

  public BadUserNameException(int pMinLength, int pMaxLength)
  {
    super("A first or last name must contain between " + pMinLength + " and " + pMaxLength + " characters!");
  }
}

package com.github.simondan.svl.server.auth.exceptions;

/**
 * @author Simon Danner, 30.11.2019
 */
public class BadMailAddressException extends Exception
{
  public BadMailAddressException(String pMail)
  {
    super(pMail + " is not a vail email address!");
  }
}

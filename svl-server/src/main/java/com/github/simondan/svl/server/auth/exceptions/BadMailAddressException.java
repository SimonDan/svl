package com.github.simondan.svl.server.auth.exceptions;

/**
 * @author Simon Danner, 30.11.2019
 */
public class BadMailAddressException extends Exception
{
  public BadMailAddressException(String pMessage)
  {
    super(pMessage);
  }
}

package com.github.simondan.svl.server.auth.exceptions;

import java.time.Duration;

/**
 * @author Simon Danner, 23.11.2019
 */
public class BadRestoreCodeException extends Exception
{
  public BadRestoreCodeException(String pRestoreCode)
  {
    super("Bad restore code: " + pRestoreCode);
  }

  public BadRestoreCodeException(Duration pDuration)
  {
    super("Restore code expired after " + pDuration.toMinutes() + " minutes!");
  }
}

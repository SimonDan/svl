package com.github.simondan.svl.server.penalty.exceptions;

/**
 * @author Simon Danner, 10.05.2020
 */
public class PenaltyNotForMeException extends Exception
{
  public PenaltyNotForMeException()
  {
    super("Penalty is not for the requesting user!");
  }
}

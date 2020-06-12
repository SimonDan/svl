package com.github.simondan.svl.server.penalty.exceptions;

import com.github.simondan.svl.communication.penalty.AbstractPenalty;

/**
 * @author Simon Danner, 28.03.2020
 */
public class PenaltyNotFoundException extends Exception
{
  public PenaltyNotFoundException(AbstractPenalty<?> pPenalty)
  {
    super("Unable to find penalty " + pPenalty);
  }
}

package com.github.simondan.svl.server.penalty.exceptions;

import com.github.simondan.svl.communication.penalty.type.AbstractPenaltyType;

import static com.github.simondan.svl.communication.penalty.type.AbstractPenaltyType.PENALTY_NAME;

/**
 * @author Simon Danner, 26.03.2020
 */
public class PenaltyTypeNotFound extends Exception
{
  public PenaltyTypeNotFound(AbstractPenaltyType pPenaltyType)
  {
    super("Penalty type with name " + pPenaltyType.getValue(PENALTY_NAME) + " not found!");
  }
}

package com.github.simondan.svl.server.penalty.exceptions;

import com.github.simondan.svl.communication.penalty.type.AbstractPenaltyType;

import static com.github.simondan.svl.communication.penalty.type.AbstractPenaltyType.PENALTY_NAME;

/**
 * @author Simon Danner, 21.03.2020
 */
public class PenaltyTypeAlreadyExistsException extends Exception
{
  public PenaltyTypeAlreadyExistsException(AbstractPenaltyType pPenaltyType)
  {
    super("A penalty type called " + pPenaltyType.getValue(PENALTY_NAME) + " is already existing!");
  }
}

package com.github.simondan.svl.server.penalty;

import com.github.simondan.svl.communication.penalty.AbstractPenalty;
import com.github.simondan.svl.communication.penalty.type.AbstractPenaltyType;
import com.github.simondan.svl.server.penalty.exceptions.*;
import de.adito.ojcms.rest.security.user.exceptions.UserNotFoundException;

import java.util.*;

/**
 * @author Simon Danner, 21.03.2020
 */
public interface IPenaltyService
{
  void registerPenaltyType(AbstractPenaltyType pNewPenaltyType) throws PenaltyTypeAlreadyExistsException;

  void deletePenaltyType(AbstractPenaltyType pPenaltyType) throws PenaltyTypeNotFound;

  void newPenaltyForMe(AbstractPenalty<?> pPenalty) throws PenaltyTypeNotFound, UserNotFoundException, PenaltyNotForMeException;

  void newPenalty(AbstractPenalty<?> pPenalty) throws PenaltyTypeNotFound, UserNotFoundException;

  void deletePenalty(AbstractPenalty<?> pPenalty) throws PenaltyNotFoundException;

  void deleteAllPenaltiesOfUser(String pUserMail) throws UserNotFoundException;

  Set<AbstractPenaltyType> getAllPenaltyTypes();

  List<AbstractPenalty<?>> getPenaltiesForUser(String pUserMail) throws UserNotFoundException;
}

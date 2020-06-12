package com.github.simondan.svl.server.behindtheapi;

import com.github.simondan.svl.communication.penalty.AbstractPenalty;
import com.github.simondan.svl.communication.penalty.type.AbstractPenaltyType;
import com.github.simondan.svl.server.penalty.IPenaltyService;
import com.github.simondan.svl.server.penalty.exceptions.*;
import com.github.simondan.svl.server.rest.SVLUser;
import de.adito.ojcms.beans.IBeanContainer;
import de.adito.ojcms.rest.security.UserRequestContext;
import de.adito.ojcms.rest.security.user.exceptions.UserNotFoundException;
import de.adito.ojcms.transactions.annotations.Transactional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.simondan.svl.communication.penalty.AbstractPenalty.*;
import static de.adito.ojcms.rest.security.user.OJUser.MAIL;

/**
 * @author Simon Danner, 21.03.2020
 */
@ApplicationScoped
public class PenaltyService implements IPenaltyService
{
  @Inject
  private IBeanContainer<SVLUser> users;
  @Inject
  private IBeanContainer<AbstractPenaltyType> typeContainer;
  @Inject
  private IBeanContainer<AbstractPenalty> penaltyContainer;
  @Inject
  private UserRequestContext requestContext;

  @Override
  @Transactional
  public void registerPenaltyType(AbstractPenaltyType pNewPenaltyType) throws PenaltyTypeAlreadyExistsException
  {
    if (typeContainer.contains(pNewPenaltyType))
      throw new PenaltyTypeAlreadyExistsException(pNewPenaltyType);

    typeContainer.addBean(pNewPenaltyType);
  }

  @Override
  @Transactional
  public void deletePenaltyType(AbstractPenaltyType pPenaltyType) throws PenaltyTypeNotFound
  {
    final boolean removed = typeContainer.removeBean(pPenaltyType);

    if (!removed)
      throw new PenaltyTypeNotFound(pPenaltyType);
  }

  @Override
  @Transactional
  public void newPenaltyForMe(AbstractPenalty<?> pPenalty) throws PenaltyTypeNotFound, UserNotFoundException, PenaltyNotForMeException
  {
    if (!requestContext.getRequestingUser().getValue(MAIL).equals(pPenalty.getValue(RECEIVING_USER_MAIL)))
      throw new PenaltyNotForMeException();

    _newPenalty(pPenalty);
  }

  @Override
  @Transactional
  public void newPenalty(AbstractPenalty<?> pPenalty) throws PenaltyTypeNotFound, UserNotFoundException
  {
    _newPenalty(pPenalty);
  }

  @Override
  @Transactional
  public void deletePenalty(AbstractPenalty<?> pPenalty) throws PenaltyNotFoundException
  {
    final boolean removed = penaltyContainer.removeBean(pPenalty);

    if (!removed)
      throw new PenaltyNotFoundException(pPenalty);
  }

  @Override
  @Transactional
  public void deleteAllPenaltiesOfUser(String pUserMail) throws UserNotFoundException
  {
    _checkUser(pUserMail);
    penaltyContainer.removeBeanIf(pPenalty -> pPenalty.getValue(RECEIVING_USER_MAIL).equals(pUserMail));
  }

  @Override
  @Transactional
  public Set<AbstractPenaltyType> getAllPenaltyTypes()
  {
    return typeContainer.stream().collect(Collectors.toSet());
  }

  @Override
  @Transactional
  public List<AbstractPenalty<?>> getPenaltiesForUser(String pUserMail) throws UserNotFoundException
  {
    _checkUser(pUserMail);

    return penaltyContainer.findByFieldValue(RECEIVING_USER_MAIL, pUserMail).stream()
        .map(pPenalty -> (AbstractPenalty<?>) pPenalty)
        .collect(Collectors.toList());
  }

  private void _newPenalty(AbstractPenalty<?> pPenalty) throws UserNotFoundException, PenaltyTypeNotFound
  {
    _checkUser(pPenalty.getValue(RECEIVING_USER_MAIL));

    if (!typeContainer.contains(pPenalty.getValue(PENALTY_TYPE)))
      throw new PenaltyTypeNotFound(pPenalty.getValue(PENALTY_TYPE));

    penaltyContainer.addBean(pPenalty);
  }

  private void _checkUser(String pUserMail) throws UserNotFoundException
  {
    if (users.findOneByFieldValue(MAIL, pUserMail).isEmpty())
      throw new UserNotFoundException(pUserMail);
  }
}

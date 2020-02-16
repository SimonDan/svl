package com.github.simondan.svl.server.behindtheapi;

import com.github.simondan.svl.communication.auth.*;
import com.github.simondan.svl.communication.utils.SharedUtils;
import com.github.simondan.svl.server.auth.*;
import com.github.simondan.svl.server.auth.exceptions.*;
import com.github.simondan.svl.server.security.RequestSecurityContext;
import de.adito.ojcms.beans.IBeanContainer;
import de.adito.ojcms.beans.literals.fields.util.FieldValueTuple;
import de.adito.ojcms.transactions.annotations.Transactional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import static com.github.simondan.svl.communication.utils.SharedUtils.VALID_EMAIL_ADDRESS_REGEX;

/**
 * @author Simon Danner, 30.09.2019
 */
@RequestScoped
public class UserService implements IUserService
{
  @Inject
  private IBeanContainer<User> users;
  @Inject
  private RequestSecurityContext securityContext;
  @Inject
  private MailSender mailSender;

  private User authenticatedUser;

  @Override
  @Transactional
  public User authenticateUser(IAuthenticationRequest pRequest) throws BadCredentialsException
  {
    final UserName userName = pRequest.getUserName();

    final User authenticatedUser = users
        .findOneByFieldValues(new FieldValueTuple<>(User.NAME, userName), new FieldValueTuple<>(User.PASSWORD, pRequest.getPassword()))
        .orElseThrow(() -> new BadCredentialsException(userName));

    authenticatedUser.generateNewPassword();
    return authenticatedUser;
  }

  @Override
  @Transactional
  public User registerNewUser(IRegistrationRequest pRequest) throws UserAlreadyExistsException, BadMailAddressException
  {
    final UserName userName = pRequest.getUserName();
    final String mail = pRequest.getMailAddress();

    if (!SharedUtils.validatePattern(VALID_EMAIL_ADDRESS_REGEX, mail))
      throw new BadMailAddressException(mail + " is not a vail email address!");

    if (users.findOneByFieldValue(User.NAME, userName).isPresent())
      throw new UserAlreadyExistsException(userName);

    if (users.findOneByFieldValue(User.EMAIL, mail).isPresent())
      throw new BadMailAddressException("Mail address " + mail + " has already been used by another user!");

    final User newUser = new User(userName, mail);
    users.addBean(newUser);

    return newUser;
  }

  @Override
  @Transactional
  public void requestPasswordRestoreCodeByMail(IRegistrationRequest pRegistrationData) throws MailNotMatchingException
  {
    final UserName userName = pRegistrationData.getUserName();
    final String mail = pRegistrationData.getMailAddress();

    final User user = users
        .findOneByFieldValues(new FieldValueTuple<>(User.NAME, userName), new FieldValueTuple<>(User.EMAIL, mail))
        .orElseThrow(() -> new MailNotMatchingException(userName, mail));

    user.generateRestoreCode();
    mailSender.sendRestoreCodeMail(user);
  }

  @Override
  @Transactional
  public User restorePassword(IRestoreAuthRequest pRestoreAuthRequest) throws UserNotFoundException, BadRestoreCodeException
  {
    final User user = users.findOneByFieldValue(User.NAME, pRestoreAuthRequest.getUserName())
        .orElseThrow(() -> new UserNotFoundException(pRestoreAuthRequest.getUserName()));

    user.validateAndResetRestoreCode(pRestoreAuthRequest.getRestoreCode());
    return user;
  }

  @Override
  public User getAuthenticatedUser()
  {
    if (authenticatedUser == null)
      authenticatedUser = securityContext.getAuthenticatedUserName()
          .flatMap(authUserName -> users.findOneByFieldValue(User.NAME, authUserName))
          .orElseThrow(NoAuthenticatedUserException::new);

    return authenticatedUser;
  }
}

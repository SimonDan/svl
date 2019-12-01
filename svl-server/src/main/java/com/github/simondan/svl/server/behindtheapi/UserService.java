package com.github.simondan.svl.server.behindtheapi;

import com.github.simondan.svl.communication.auth.UserName;
import com.github.simondan.svl.communication.utils.SharedUtils;
import com.github.simondan.svl.server.auth.*;
import com.github.simondan.svl.server.auth.exceptions.*;
import com.github.simondan.svl.server.security.RequestSecurityContext;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.inject.Inject;
import java.util.Objects;

import static com.github.simondan.svl.communication.utils.SharedUtils.VALID_EMAIL_ADDRESS_REGEX;
import static de.adito.ojcms.persistence.OJContainers.SVL_USERS;

/**
 * @author Simon Danner, 30.09.2019
 */
@RequestScoped
public class UserService implements IUserService
{
  @Inject
  private RequestSecurityContext securityContext;
  @Inject
  private MailSender mailSender;

  private User authenticatedUser;

  @Override
  public User authenticateUser(UserName pUserName, String pPassword) throws BadCredentialsException
  {
    final User authenticatedUser = SVL_USERS.stream()
        .filter(pUser -> Objects.equals(pUserName, pUser.getValue(User.NAME)) && Objects.equals(pPassword, pUser.getValue(User.PASSWORD)))
        .findAny()
        .orElseThrow(() -> new BadCredentialsException(pUserName));

    authenticatedUser.generateNewPassword();

    return authenticatedUser;
  }

  @Override
  public User registerNewUser(UserName pUserName, String pMail) throws UserAlreadyExistsException, BadMailAddressException
  {
    if (!SharedUtils.validatePattern(VALID_EMAIL_ADDRESS_REGEX, pMail))
      throw new BadMailAddressException(pMail + " is not a vail email address!");

    if (SVL_USERS.findOneByFieldValue(User.NAME, pUserName).isPresent())
      throw new UserAlreadyExistsException(pUserName);

    if (SVL_USERS.findOneByFieldValue(User.EMAIL, pMail).isPresent())
      throw new BadMailAddressException("Mail address " + pMail + " has already been user by another user!");

    return new User(pUserName, pMail);
  }

  @Override
  public void requestPasswordRestoreCodeByMail(UserName pUserName, String pMail) throws MailNotMatchingException
  {
    final User user = SVL_USERS.stream()
        .filter(pUser -> Objects.equals(pUserName, pUser.getValue(User.NAME)) && Objects.equals(pMail, pUser.getValue(User.EMAIL)))
        .findAny()
        .orElseThrow(() -> new MailNotMatchingException(pUserName, pMail));

    user.generateRestoreCode();
    mailSender.sendRestoreCodeMail(user);
  }

  @Override
  public User restorePassword(UserName pUserName, String pRestoreCode) throws UserNotFoundException, BadRestoreCodeException
  {
    final User user = SVL_USERS.findOneByFieldValue(User.NAME, pUserName)
        .orElseThrow(() -> new UserNotFoundException(pUserName));

    user.validateAndResetRestoreCode(pRestoreCode);

    return user;
  }

  @Override
  public User getAuthenticatedUser()
  {
    if (authenticatedUser == null)
      authenticatedUser = securityContext.getAuthenticatedUserName()
          .flatMap(authUserName -> SVL_USERS.findOneByFieldValue(User.NAME, authUserName))
          .orElseThrow(NoAuthenticatedUserException::new);

    return authenticatedUser;
  }
}

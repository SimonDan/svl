package com.github.simondan.svl.server.behindtheapi;

import com.github.simondan.svl.server.auth.*;
import com.github.simondan.svl.server.auth.exceptions.*;
import com.github.simondan.svl.server.security.RequestSecurityContext;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.inject.Inject;
import java.util.Objects;

import static de.adito.ojcms.persistence.OJContainers.SVL_USERS;

/**
 * @author Simon Danner, 30.09.2019
 */
@RequestScoped
public class UserService implements IUserService
{
  @Inject
  private RequestSecurityContext securityContext;

  private User authenticatedUser;

  @Override
  public User authenticateUser(UserName pUserName, String pPassword) throws BadCredentialsException
  {
    return SVL_USERS.stream()
        .filter(pUser -> Objects.equals(pUserName, pUser.getValue(User.NAME)) && Objects.equals(pPassword, pUser.getValue(User.PASSWORD)))
        .findAny()
        .orElseThrow(() -> new BadCredentialsException(pUserName));
  }

  @Override
  public User registerNewUser(UserName pUserName, String pEmail) throws UserAlreadyExistsException
  {
    if (SVL_USERS.findOneByFieldValue(User.NAME, pUserName).isPresent())
      throw new UserAlreadyExistsException(pUserName);

    return new User(pUserName, _generateNewPassword(), pEmail);
  }

  @Override
  public void requestPasswordRestoreCodeByMail(UserName pUserName, String pMail) throws MailNotMatchingException
  {
    final User user = SVL_USERS.stream()
        .filter(pUser -> Objects.equals(pUserName, pUser.getValue(User.NAME)) && Objects.equals(pMail, pUser.getValue(User.EMAIL)))
        .findAny()
        .orElseThrow(() -> new MailNotMatchingException(pUserName, pMail));

    final String restoreCode = user.generateAndSetRestoreCode();

    MailSender.sendRestoreCodeMail(user, restoreCode);
  }

  @Override
  public User restorePassword(UserName pUserName, String pRestoreCode) throws BadRestoreCodeException
  {
    return null;
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

  private String _generateNewPassword()
  {
    return "test"; //TODO
  }
}

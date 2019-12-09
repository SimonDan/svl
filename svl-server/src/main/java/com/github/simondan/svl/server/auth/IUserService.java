package com.github.simondan.svl.server.auth;

import com.github.simondan.svl.communication.auth.*;
import com.github.simondan.svl.server.auth.exceptions.*;

/**
 * @author Simon Danner, 20.09.2019
 */
public interface IUserService
{
  User authenticateUser(IAuthenticationRequest pAuthenticationRequest) throws BadCredentialsException;

  User registerNewUser(IRegistrationRequest pRegistrationRequest) throws UserAlreadyExistsException, BadMailAddressException;

  void requestPasswordRestoreCodeByMail(IRegistrationRequest pRegistrationData) throws MailNotMatchingException;

  User restorePassword(IRestoreAuthRequest pRestoreAuthRequest) throws UserNotFoundException, BadRestoreCodeException;

  User getAuthenticatedUser();
}

package com.github.simondan.svl.server.auth;

import com.github.simondan.svl.communication.auth.UserName;
import com.github.simondan.svl.server.auth.exceptions.*;

/**
 * @author Simon Danner, 20.09.2019
 */
public interface IUserService
{
  User authenticateUser(UserName pUserName, String pPassword) throws BadCredentialsException;

  User registerNewUser(UserName pUserName, String pMail) throws UserAlreadyExistsException, BadMailAddressException;

  void requestPasswordRestoreCodeByMail(UserName pUserName, String pMail) throws MailNotMatchingException;

  User restorePassword(UserName pUserName, String pRestoreCode) throws UserNotFoundException, BadRestoreCodeException;

  User getAuthenticatedUser();
}

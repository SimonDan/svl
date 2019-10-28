package com.github.simondan.svl.server.auth;

import com.github.simondan.svl.server.auth.exceptions.*;

/**
 * @author Simon Danner, 20.09.2019
 */
public interface IUserService
{
  User authenticateUser(UserName pUserName, String pPassword) throws BadCredentialsException;

  User registerNewUser(UserName pUserName, String pEmail) throws UserAlreadyExistsException;

  void requestNewPasswordByMail(UserName pUserName);

  User getAuthenticatedUser();
}

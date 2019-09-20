package com.github.simondan.svl.server.auth;

/**
 * @author Simon Danner, 20.09.2019
 */
public interface IUserAuthService
{
  User authenticateUser(String pUsername, String pPassword);

  void registerNewUser(User pUser);
}

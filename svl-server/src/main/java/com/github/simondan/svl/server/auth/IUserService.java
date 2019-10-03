package com.github.simondan.svl.server.auth;

/**
 * @author Simon Danner, 20.09.2019
 */
public interface IUserService
{
  User authenticateUser(String pFirstName, String pLastName, String pPassword) throws BadCredentialsException;

  void registerNewUser(User pUser);
}

package com.github.simondan.svl.server.behindtheapi;

import com.github.simondan.svl.server.auth.*;
import com.github.simondan.svl.server.security.ERole;

/**
 * @author Simon Danner, 30.09.2019
 */
public class UserService implements IUserService
{
  @Override
  public User authenticateUser(String pFirstName, String pLastName, String pPassword)
  {
    return new User(pFirstName, pLastName, pPassword, ERole.DEFAULT);
  }

  @Override
  public void registerNewUser(User pUser)
  {

  }
}

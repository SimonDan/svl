package com.github.simondan.svl.communication.auth;

import java.io.Serializable;

/**
 * @author Simon Danner, 07.12.2019
 */
public interface IAuthenticationRequest extends Serializable
{
  UserName getUserName();

  String getPassword();
}

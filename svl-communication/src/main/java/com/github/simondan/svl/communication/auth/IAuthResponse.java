package com.github.simondan.svl.communication.auth;

import java.io.Serializable;

/**
 * @author Simon Danner, 25.10.2019
 */
public interface IAuthResponse extends Serializable
{
  String getToken();

  String getNextPassword();
}

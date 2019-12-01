package com.github.simondan.svl.server.auth.exceptions;

import com.github.simondan.svl.server.auth.UserName;

/**
 * @author Simon Danner, 23.11.2019
 */
public class MailNotMatchingException extends Exception
{
  public MailNotMatchingException(UserName pUserName, String pMail)
  {
    super("No user " + pUserName + " with mail " + pMail + " found!");
  }
}

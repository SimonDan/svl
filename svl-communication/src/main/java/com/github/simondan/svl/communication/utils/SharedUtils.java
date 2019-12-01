package com.github.simondan.svl.communication.utils;

import java.time.Duration;
import java.util.regex.*;

/**
 * @author Simon Danner, 30.11.2019
 */
public final class SharedUtils
{
  public static final int MIN_NAME_LENGTH = 2;
  public static final int MAX_NAME_LENGTH = 25;
  public static final int CODE_LENGTH = 8;
  public static final Duration RESTORE_CODE_EXPIRATION_THRESHOLD = Duration.ofMinutes(10);

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  private SharedUtils()
  {
  }

  public static boolean validatePattern(Pattern pPattern, String pToVerify)
  {
    final Matcher matcher = pPattern.matcher(pToVerify);
    return matcher.find();
  }
}

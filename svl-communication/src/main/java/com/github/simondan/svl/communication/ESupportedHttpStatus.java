package com.github.simondan.svl.communication;

import java.util.stream.Stream;

/**
 * @author Simon Danner, 23.11.2019
 */
public enum ESupportedHttpStatus
{
  OK(200), BAD_REQUEST(400), NOT_AUTHORIZED(401), INTERNAL_SERVER_ERROR(500);

  private final int code;

  ESupportedHttpStatus(int pCode)
  {
    code = pCode;
  }

  public static ESupportedHttpStatus byCode(int pCode)
  {
    return Stream.of(ESupportedHttpStatus.values())
        .filter(pStatus -> pStatus.code == pCode)
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("Code " + pCode + " is not a supported HTTP response of the server!"));
  }
}

package com.github.simondan.svl.communication.auth;

import de.adito.ojcms.beans.OJFields;
import de.adito.ojcms.beans.annotations.*;
import de.adito.ojcms.beans.literals.fields.types.EnumField;
import de.adito.ojcms.rest.auth.api.AuthenticationResponse;

/**
 * @author Simon Danner, 13.04.2020
 */
public class SVLAuthenticationResponse extends AuthenticationResponse
{
  @FinalNeverNull
  @FieldOrder(2)
  public static final EnumField<EUserRole> USER_ROLE = OJFields.create(SVLAuthenticationResponse.class);

  public SVLAuthenticationResponse(String pToken, String pNextPassword, EUserRole pUserRole)
  {
    super(pToken, pNextPassword);
    setValue(USER_ROLE, pUserRole);
  }

  /**
   * Required for serialization.
   */
  @SuppressWarnings("unused")
  private SVLAuthenticationResponse()
  {
  }
}


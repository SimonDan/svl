package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.communication.auth.EUserRole;
import de.adito.ojcms.beans.OJFields;
import de.adito.ojcms.beans.annotations.NeverNull;
import de.adito.ojcms.beans.literals.fields.types.EnumField;
import de.adito.ojcms.persistence.Persist;
import de.adito.ojcms.rest.security.user.OJUser;

/**
 * @author Simon Danner, 08.04.2020
 */
@Persist(containerId = "SVL_USERS")
public class SVLUser extends OJUser
{
  @NeverNull
  public static final EnumField<EUserRole> ROLE = OJFields.create(SVLUser.class);

  public SVLUser(String pEmail, String pDisplayName, EUserRole pUserRole)
  {
    super(pEmail, pDisplayName);
    setValue(ROLE, pUserRole);
  }

  /**
   * Required by OJCMS.
   */
  @SuppressWarnings("unused")
  private SVLUser()
  {
  }
}

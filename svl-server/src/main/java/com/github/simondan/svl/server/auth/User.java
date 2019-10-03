package com.github.simondan.svl.server.auth;

import com.github.simondan.svl.server.security.ERole;
import de.adito.ojcms.beans.*;
import de.adito.ojcms.beans.literals.fields.types.*;
import de.adito.ojcms.persistence.Persist;
import de.adito.ojcms.persistence.util.EStorageMode;

/**
 * @author Simon Danner, 20.09.2019
 */
@Persist(containerId = "SVL_USERS", storageMode = EStorageMode.AUTOMATIC)
public class User extends OJBean<User>
{
  public static final IntegerField ID = OJFields.create(User.class);
  public static final TextField FIRST_NAME = OJFields.create(User.class);
  public static final TextField LAST_NAME = OJFields.create(User.class);
  private static final TextField PASSWORD = OJFields.create(User.class);
  public static final EnumField<ERole> ROLE = OJFields.create(User.class);

  public User(String pFirstName, String pLastName, String pPassword, ERole pRole)
  {
    setValue(FIRST_NAME, pFirstName);
    setValue(LAST_NAME, pLastName);
    setValue(PASSWORD, pPassword);
    setValue(ROLE, pRole);
  }
}

package com.github.simondan.svl.server.auth;

import com.github.simondan.svl.server.security.ERole;
import de.adito.ojcms.beans.*;
import de.adito.ojcms.beans.annotations.*;
import de.adito.ojcms.beans.literals.fields.types.*;
import de.adito.ojcms.persistence.Persist;
import de.adito.ojcms.persistence.util.EStorageMode;

/**
 * @author Simon Danner, 20.09.2019
 */
@Persist(containerId = "SVL_USERS", storageMode = EStorageMode.AUTOMATIC)
public class User extends OJBean<User>
{
  @Identifier
  @FinalNeverNull
  public static final GenericField<UserName> NAME = OJFields.create(User.class);
  @NeverNull
  public static final TextField PASSWORD = OJFields.create(User.class);
  @FinalNeverNull
  public static final TextField EMAIL = OJFields.create(User.class);
  @NeverNull
  public static final EnumField<ERole> ROLE = OJFields.create(User.class);

  public User(UserName pName, String pPassword, String pEmail)
  {
    setValue(NAME, pName);
    setValue(PASSWORD, pPassword);
    setValue(EMAIL, pEmail);
    setValue(ROLE, ERole.DEFAULT);
  }

  /**
   * Required by OJCMS.
   */
  @SuppressWarnings("unused")
  private User()
  {
  }
}

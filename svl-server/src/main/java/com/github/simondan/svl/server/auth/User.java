package com.github.simondan.svl.server.auth;

import com.github.simondan.svl.communication.auth.EUserRole;
import de.adito.ojcms.beans.*;
import de.adito.ojcms.beans.annotations.*;
import de.adito.ojcms.beans.literals.fields.types.*;
import de.adito.ojcms.persistence.Persist;
import de.adito.ojcms.persistence.util.EStorageMode;

import java.time.Instant;

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
  public static final EnumField<EUserRole> ROLE = OJFields.create(User.class);
  public static final DateField RESTORE_TIMESTAMP = OJFields.create(User.class);
  @OptionalField
  public static final TextField RESTORE_CODE = OJFields.createOptional(User.class, (pUser, pCode) -> pUser.getValue(RESTORE_TIMESTAMP) != null);

  public User(UserName pName, String pPassword, String pEmail)
  {
    setValue(NAME, pName);
    setValue(PASSWORD, pPassword);
    setValue(EMAIL, pEmail);
    setValue(ROLE, EUserRole.DEFAULT);
  }

  /**
   * Required by OJCMS.
   */
  @SuppressWarnings("unused")
  private User()
  {
  }

  public String generateAndSetRestoreCode()
  {
    final String code = _generateRandomRestoreCode();
    setValue(RESTORE_TIMESTAMP, Instant.now());
    setValue(RESTORE_CODE, code);

    return code;
  }

  private String _generateRandomRestoreCode()
  {
    return "lol"; //TODO
  }
}

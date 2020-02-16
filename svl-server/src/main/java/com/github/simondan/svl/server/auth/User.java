package com.github.simondan.svl.server.auth;

import com.github.simondan.svl.communication.auth.*;
import com.github.simondan.svl.server.auth.exceptions.BadRestoreCodeException;
import com.github.simondan.svl.server.security.RandomString;
import de.adito.ojcms.beans.*;
import de.adito.ojcms.beans.annotations.*;
import de.adito.ojcms.beans.literals.fields.types.*;
import de.adito.ojcms.persistence.Persist;

import java.time.*;
import java.util.Objects;

import static com.github.simondan.svl.communication.utils.SharedUtils.*;

/**
 * @author Simon Danner, 20.09.2019
 */
@Persist(containerId = "SVL_USERS")
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

  public User(UserName pName, String pEmail)
  {
    setValue(NAME, pName);
    setValue(EMAIL, pEmail);
    setValue(ROLE, EUserRole.DEFAULT);
    generateNewPassword();
  }

  /**
   * Required by OJCMS.
   */
  @SuppressWarnings("unused")
  private User()
  {
  }

  public void generateNewPassword()
  {
    setValue(PASSWORD, RandomString.generate(50));
  }

  public void generateRestoreCode()
  {
    setValue(RESTORE_TIMESTAMP, Instant.now());
    setValue(RESTORE_CODE, RandomString.generate(CODE_LENGTH));
  }

  public void validateAndResetRestoreCode(String pRestoreCode) throws BadRestoreCodeException
  {
    final String code = getValue(RESTORE_CODE);

    if (code == null)
      throw new IllegalStateException("No restore code set!");

    if (!Objects.equals(pRestoreCode, code))
      throw new BadRestoreCodeException(pRestoreCode);

    final Instant restoreCodeTimestamp = getValue(RESTORE_TIMESTAMP);
    if (Duration.between(restoreCodeTimestamp, Instant.now()).compareTo(RESTORE_CODE_EXPIRATION_THRESHOLD) > 0)
      throw new BadRestoreCodeException(RESTORE_CODE_EXPIRATION_THRESHOLD);

    setValue(RESTORE_CODE, null);
    setValue(RESTORE_TIMESTAMP, null);
  }
}

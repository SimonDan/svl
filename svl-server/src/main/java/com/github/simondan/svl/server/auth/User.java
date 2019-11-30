package com.github.simondan.svl.server.auth;

import com.github.simondan.svl.communication.auth.EUserRole;
import com.github.simondan.svl.server.auth.exceptions.BadRestoreCodeException;
import de.adito.ojcms.beans.*;
import de.adito.ojcms.beans.annotations.*;
import de.adito.ojcms.beans.literals.fields.types.*;
import de.adito.ojcms.persistence.Persist;
import de.adito.ojcms.persistence.util.EStorageMode;

import java.security.SecureRandom;
import java.time.*;
import java.util.*;

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
    setValue(PASSWORD, RandomString.next(50));
  }

  public String generateAndSetRestoreCode()
  {
    final String code = RandomString.next(8);
    setValue(RESTORE_TIMESTAMP, Instant.now());
    setValue(RESTORE_CODE, code);

    return code;
  }

  public void validateAndResetRestoreCode(String pRestoreCode, Duration pAllowedRestoreDuration) throws BadRestoreCodeException
  {
    final String code = getValue(RESTORE_CODE);

    if (code == null)
      throw new IllegalStateException("No restore code set!");

    if (!Objects.equals(pRestoreCode, code))
      throw new BadRestoreCodeException(pRestoreCode);

    final Instant restoreCodeTimestamp = getValue(RESTORE_TIMESTAMP);
    if (Duration.between(restoreCodeTimestamp, Instant.now()).compareTo(pAllowedRestoreDuration) > 0)
      throw new BadRestoreCodeException(pAllowedRestoreDuration);

    setValue(RESTORE_TIMESTAMP, null);
    setValue(RESTORE_CODE, null);
  }

  private static class RandomString
  {
    private static final String UPPER_ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_ABC = UPPER_ABC.toLowerCase(Locale.ROOT);
    private static final String DIGITS = "0123456789";
    private static final char[] ABC = (UPPER_ABC + LOWER_ABC + DIGITS).toCharArray();
    private static final Random RANDOM = new SecureRandom();

    static String next(int pLength)
    {
      if (pLength < 1)
        throw new IllegalArgumentException("Bad length: " + pLength);

      final char[] buffer = new char[pLength];

      for (int i = 0; i < buffer.length; ++i)
        buffer[i] = ABC[RANDOM.nextInt(ABC.length)];

      return new String(buffer);
    }
  }
}

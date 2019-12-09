package com.github.simondan.svl.communication.auth;

import java.util.Objects;

import static com.github.simondan.svl.communication.utils.SharedUtils.*;
import static de.adito.ojcms.utils.StringUtility.requireNotEmpty;

/**
 * @author Simon Danner, 11.10.2019
 */
public final class UserName
{
  private final String firstName;
  private final String lastName;

  public static UserName of(String combinedString) throws BadUserNameException
  {
    final String[] parts = combinedString.split(" ");
    if (parts.length != 2)
      throw new BadUserNameException(new IndexOutOfBoundsException("Unable to split combined user name " + combinedString));

    return of(parts[0], parts[1]);
  }

  public static UserName of(String pFirstName, String pLastName) throws BadUserNameException
  {
    try
    {
      return new UserName(pFirstName, pLastName);
    }
    catch (IllegalArgumentException pBadNameException)
    {
      throw new BadUserNameException(pBadNameException);
    }
  }

  private UserName(String pFirstName, String pLastName) throws BadUserNameException
  {
    firstName = _prettyName(requireNotEmpty(pFirstName, "first name"));
    lastName = _prettyName(requireNotEmpty(pLastName, "last name"));
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  @Override
  public String toString()
  {
    return firstName + " " + lastName;
  }

  @Override
  public boolean equals(Object pOther)
  {
    if (this == pOther)
      return true;
    if (pOther == null || getClass() != pOther.getClass())
      return false;

    final UserName other = (UserName) pOther;
    return firstName.equals(other.firstName) && lastName.equals(other.lastName);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(firstName, lastName);
  }

  private String _prettyName(String pName) throws BadUserNameException
  {
    if (pName.length() < MIN_NAME_LENGTH || pName.length() > MAX_NAME_LENGTH)
      throw new BadUserNameException(MIN_NAME_LENGTH, MAX_NAME_LENGTH);

    final String lowerCase = pName.trim().toLowerCase();
    return Character.toUpperCase(lowerCase.charAt(0)) + lowerCase.substring(1);
  }
}

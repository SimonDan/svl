package com.github.simondan.svl.server.auth;

import com.github.simondan.svl.server.security.ERole;
import de.adito.ojcms.beans.*;
import de.adito.ojcms.beans.annotations.Private;
import de.adito.ojcms.beans.literals.fields.IField;

import java.security.Principal;

/**
 * @author Simon Danner, 20.09.2019
 */
public class User extends OJBean<User> implements Principal
{
  @Private
  public static final IField<String> NAME = OJFields.create(User.class);
  @Private
  public static final IField<String> PASSWORD = OJFields.create(User.class);
  @Private
  public static final IField<ERole> ROLE = OJFields.create(User.class);

  public User(String pName, String pPassword, ERole pRole)
  {
    setPrivateValue(NAME, pName);
    setPrivateValue(PASSWORD, pPassword);
    setPrivateValue(ROLE, pRole);
  }

  public String getName()
  {
    return getValue(NAME);
  }

  public ERole getRole()
  {
    return getValue(ROLE);
  }
}

package com.github.simondan.svl.communication.penalty.type;

import de.adito.ojcms.beans.*;
import de.adito.ojcms.beans.annotations.*;
import de.adito.ojcms.beans.literals.fields.types.TextField;

/**
 * @author Simon Danner, 21.03.2020
 */
public abstract class AbstractPenaltyType extends OJBean
{
  @Identifier
  @FinalNeverNull
  public static final TextField PENALTY_NAME = OJFields.create(AbstractPenaltyType.class);

  protected AbstractPenaltyType(String pPenaltyName)
  {
    setValue(PENALTY_NAME, pPenaltyName);
  }
}

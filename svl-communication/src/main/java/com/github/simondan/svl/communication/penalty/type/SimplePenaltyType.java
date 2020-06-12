package com.github.simondan.svl.communication.penalty.type;

import com.github.simondan.svl.communication.penalty.Money;
import de.adito.ojcms.beans.OJFields;
import de.adito.ojcms.beans.annotations.FinalNeverNull;
import de.adito.ojcms.beans.literals.fields.types.GenericField;

/**
 * @author Simon Danner, 21.03.2020
 */
public class SimplePenaltyType extends AbstractPenaltyType
{
  @FinalNeverNull
  public static final GenericField<Money> MONETARY_AMOUNT = OJFields.create(SimplePenaltyType.class);

  public SimplePenaltyType(String pPenaltyName, Money pMonetaryAmount)
  {
    super(pPenaltyName);
    setValue(MONETARY_AMOUNT, pMonetaryAmount);
  }
}

package com.github.simondan.svl.communication.penalty.type;

import com.github.simondan.svl.communication.penalty.Money;
import de.adito.ojcms.beans.OJFields;
import de.adito.ojcms.beans.annotations.FinalNeverNull;
import de.adito.ojcms.beans.literals.fields.types.GenericField;

/**
 * @author Simon Danner, 21.03.2020
 */
public class PenaltyTypeByCount extends AbstractPenaltyType
{
  @FinalNeverNull
  public static final GenericField<Money> MONETARY_AMOUNT_PER_COUNT = OJFields.create(PenaltyTypeByCount.class);

  public PenaltyTypeByCount(String pPenaltyName, Money pMonetaryAmountPerCount)
  {
    super(pPenaltyName);
    setValue(MONETARY_AMOUNT_PER_COUNT, pMonetaryAmountPerCount);
  }
}

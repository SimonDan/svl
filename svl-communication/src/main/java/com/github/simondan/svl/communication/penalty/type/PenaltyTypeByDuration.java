package com.github.simondan.svl.communication.penalty.type;

import com.github.simondan.svl.communication.penalty.Money;
import de.adito.ojcms.beans.OJFields;
import de.adito.ojcms.beans.annotations.FinalNeverNull;
import de.adito.ojcms.beans.literals.fields.types.*;

import java.time.Duration;

/**
 * @author Simon Danner, 21.03.2020
 */
public class PenaltyTypeByDuration extends AbstractPenaltyType
{
  @FinalNeverNull
  public static final GenericField<Money> MONETARY_AMOUNT_PER_INTERVAL = OJFields.create(PenaltyTypeByDuration.class);
  @FinalNeverNull
  public static final DurationField INTERVAL_DURATION = OJFields.create(PenaltyTypeByDuration.class);

  public PenaltyTypeByDuration(String pPenaltyName, Money pMonetaryAmountPerInterval, Duration pIntervalDuration)
  {
    super(pPenaltyName);
    setValue(MONETARY_AMOUNT_PER_INTERVAL, pMonetaryAmountPerInterval);
    setValue(INTERVAL_DURATION, pIntervalDuration);
  }
}

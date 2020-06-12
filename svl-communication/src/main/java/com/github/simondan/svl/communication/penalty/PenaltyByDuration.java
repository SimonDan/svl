package com.github.simondan.svl.communication.penalty;

import com.github.simondan.svl.communication.penalty.type.PenaltyTypeByDuration;
import de.adito.ojcms.beans.OJFields;
import de.adito.ojcms.beans.annotations.*;
import de.adito.ojcms.beans.literals.fields.types.DurationField;

import java.time.*;

/**
 * @author Simon Danner, 21.03.2020
 */
public class PenaltyByDuration extends AbstractPenalty<PenaltyTypeByDuration>
{
  @FinalNeverNull
  @FieldOrder(5)
  public static final DurationField TOTAL_DURATION = OJFields.create(PenaltyByDuration.class);

  public PenaltyByDuration(Instant pTimestamp, PenaltyTypeByDuration pPenaltyType, Money pMonetaryAmount, String pReceivingUserMail,
                           Duration pTotalDuration)
  {
    super(pTimestamp, pPenaltyType, pMonetaryAmount, pReceivingUserMail);
    setValue(TOTAL_DURATION, pTotalDuration);
  }
}

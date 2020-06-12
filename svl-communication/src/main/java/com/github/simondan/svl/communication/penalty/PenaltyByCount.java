package com.github.simondan.svl.communication.penalty;

import com.github.simondan.svl.communication.penalty.type.PenaltyTypeByCount;
import de.adito.ojcms.beans.OJFields;
import de.adito.ojcms.beans.annotations.*;
import de.adito.ojcms.beans.literals.fields.types.IntegerField;

import java.time.Instant;

/**
 * @author Simon Danner, 21.03.2020
 */
public class PenaltyByCount extends AbstractPenalty<PenaltyTypeByCount>
{
  @FinalNeverNull
  @FieldOrder(5)
  public static final IntegerField COUNT = OJFields.create(PenaltyByCount.class);

  public PenaltyByCount(Instant pTimestamp, PenaltyTypeByCount pPenaltyType, Money pMonetaryAmount, String pReceivingUserMail, int pCount)
  {
    super(pTimestamp, pPenaltyType, pMonetaryAmount, pReceivingUserMail);
    setValue(COUNT, pCount);
  }
}

package com.github.simondan.svl.communication.penalty;

import com.github.simondan.svl.communication.penalty.type.AbstractPenaltyType;
import de.adito.ojcms.beans.*;
import de.adito.ojcms.beans.annotations.*;
import de.adito.ojcms.beans.literals.fields.types.*;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Simon Danner, 21.03.2020
 */
public abstract class AbstractPenalty<TYPE extends AbstractPenaltyType> extends OJBean
{
  @FinalNeverNull
  @Identifier
  @FieldOrder(0)
  public static final TextField PENALTY_ID = OJFields.create(AbstractPenalty.class);
  @FinalNeverNull
  @FieldOrder(1)
  public static final TextField RECEIVING_USER_MAIL = OJFields.create(AbstractPenalty.class);
  @FinalNeverNull
  @FieldOrder(2)
  public static final TimestampField TIMESTAMP = OJFields.create(AbstractPenalty.class);
  @FinalNeverNull
  @FieldOrder(3)
  public static final BeanField<AbstractPenaltyType> PENALTY_TYPE = OJFields.create(AbstractPenalty.class);
  @FinalNeverNull
  @FieldOrder(4)
  public static final GenericField<Money> MONETARY_AMOUNT = OJFields.create(AbstractPenalty.class);

  protected AbstractPenalty(Instant pTimestamp, TYPE pPenaltyType, Money pMonetaryAmount, String pReceivingUserMail)
  {
    setValue(PENALTY_ID, UUID.randomUUID().toString());
    setValue(RECEIVING_USER_MAIL, pReceivingUserMail);
    setValue(TIMESTAMP, pTimestamp);
    setValue(PENALTY_TYPE, pPenaltyType);
    setValue(MONETARY_AMOUNT, pMonetaryAmount);
  }
}

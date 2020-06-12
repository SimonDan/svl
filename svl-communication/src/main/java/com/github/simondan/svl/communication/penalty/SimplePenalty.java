package com.github.simondan.svl.communication.penalty;

import com.github.simondan.svl.communication.penalty.type.SimplePenaltyType;

import java.time.Instant;

/**
 * @author Simon Danner, 21.03.2020
 */
public class SimplePenalty extends AbstractPenalty<SimplePenaltyType>
{
  public SimplePenalty(Instant pTimestamp, SimplePenaltyType pPenaltyType, Money pMonetaryAmount, String pReceivingUserMail)
  {
    super(pTimestamp, pPenaltyType, pMonetaryAmount, pReceivingUserMail);
  }
}

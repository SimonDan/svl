package com.github.simondan.svl.communication.penalty;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author Simon Danner, 23.12.2019
 */
public final class Money implements Serializable, Comparable<Money>
{
  public static final Money ZERO = fromCents(0);

  private final int cents;

  public static Money fromEuros(int pEuros)
  {
    return new Money(pEuros * 100);
  }

  public static Money fromCents(int pCents)
  {
    return new Money(pCents);
  }

  public static Money from(int pEuros, int pCents)
  {
    return new Money(pEuros * 100 + pCents);
  }

  private Money(int pCents)
  {
    if (pCents < 0)
      throw new IllegalArgumentException("Cents must be >= 0!");

    cents = pCents;
  }

  public int getEuros()
  {
    return cents / 100;
  }

  public int getCents()
  {
    return cents % 100;
  }

  public int getTotalCents()
  {
    return cents;
  }

  public Money plus(Money pToAdd)
  {
    return new Money(cents + pToAdd.cents);
  }

  public Money minus(Money pToSubtract)
  {
    if (compareTo(pToSubtract) <= 0)
      return ZERO;

    return new Money(cents - pToSubtract.cents);
  }

  public Money multiply(int pTimes)
  {
    return new Money(cents * pTimes);
  }

  @Override
  public int compareTo(@NotNull Money pOther)
  {
    return cents - pOther.cents;
  }

  @Override
  public int hashCode()
  {
    return cents;
  }

  @Override
  public boolean equals(Object pOther)
  {
    if (this == pOther)
      return true;

    if (!(pOther instanceof Money))
      return false;

    return cents == ((Money) pOther).cents;
  }

  @Override
  public String toString()
  {
    return getEuros() + "." + getCents() + "€";
  }
}

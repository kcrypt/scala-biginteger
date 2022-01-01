/*
 * scala-biginteger - highly optimized BigInteger implementation for scala, scala-js and scala-native.
 *
 * Copyright 2020, 2021 Kirill A. Korinsky <kirill@korins.ky>
 * Copyright 2022 Kcrypt Lab UG <opensource@kcry.pt>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 * Ported by Alistair Johnson from
 * https://github.com/gwtproject/gwt/blob/master/user/super/com/google/gwt/emul/java/math/BigInteger.java
 * Original license copied below:
 */

/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * INCLUDES MODIFICATIONS BY RICHARD ZSCHECH AS WELL AS GOOGLE.
 */

package pt.kcry.biginteger

import scala.annotation.tailrec

import java.util.Random

import scala.language.implicitConversions

object BigInteger {

  final val ONE = new BigInteger(1, 1)

  final val TWO = new BigInteger(1, 2)

  final val THREE = new BigInteger(1, 3)

  final val FOUR = new BigInteger(1, 4)

  final val FIVE = new BigInteger(1, 5)

  final val SIX = new BigInteger(1, 6)

  final val SEVEN = new BigInteger(1, 7)

  final val EIGHT = new BigInteger(1, 8)

  final val NINE = new BigInteger(1, 9)

  final val TEN = new BigInteger(1, 10)

  final val ZERO = new BigInteger(0, 0)

  private[biginteger] final val CERTAINTY = 100

  private[biginteger] final val EQUALS = 0

  /** The {@code BigInteger} constant 1 used for comparison. */
  private[biginteger] final val GREATER = 1

  /** The {@code BigInteger} constant -1 used for comparison. */
  private[biginteger] final  val LESS = -1

  /** The {@code BigInteger} constant -1 used for comparison. */
  private[biginteger] final val MINUS_ONE = new BigInteger(-1, 1)

  private[biginteger] final val MINUS_THREE = new BigInteger(-1, 3)

  /** 2^32. */
  private final val POW32 = 4294967296d

  /** All the {@code BigInteger} numbers in the range [0,10] are cached. */
  private final val SMALL_VALUES = Array(
    ZERO, ONE, new BigInteger(1, 2), new BigInteger(1, 3),
    new BigInteger(1, 4), new BigInteger(1, 5), new BigInteger(1, 6),
    new BigInteger(1, 7), new BigInteger(1, 8), new BigInteger(1, 9), TEN)

  private final val TWO_POWS = Array.tabulate[BigInteger](32)(i => BigInteger.valueOf(1L << i))

  /** The first non zero digit is either -1 if sign is zero, otherwise it is >= 0.
   *
   *  Furthermore, it is a value that is often used and so the value computed from
   *  {@code getFirstNonzeroDigit} is cached  to {@code firstNonzeroDigit} and can be
   *  marked as unknown by a call to {@code unCache}. To mark the value as unknown,
   *  {@code firstNonzeroDigit} is set to the magic number {@code -2}
   */
  private final val firstNonzeroDigitNotSet = -2

  def probablePrime(bitLength: Int, rnd: Random): BigInteger =
    new BigInteger(bitLength, CERTAINTY, rnd)

  def valueOf(lVal: Long): BigInteger = {
    if (lVal < 0) {
      if (lVal != -1) new BigInteger(-1, -lVal)
      else MINUS_ONE
    } else if (lVal <= 10) {
      SMALL_VALUES(lVal.toInt)
    } else {
      new BigInteger(1, lVal)
    }
  }

  private[biginteger] def getPowerOfTwo(exp: Int): BigInteger = {
    if (exp < TWO_POWS.length) {
      TWO_POWS(exp)
    } else {
      val intCount = exp >> 5
      val bitN = exp & 31
      val resDigits = new Array[Int](intCount + 1)
      resDigits(intCount) = 1 << bitN
      new BigInteger(1, intCount + 1, resDigits)
    }
  }

  @inline
  private def checkNotNull[T <: AnyRef](reference: T): reference.type = {
    if (reference == null)
      throw new NullPointerException
    else
      reference
  }

  @inline
  private def checkCriticalArgument(expression: Boolean, errorMessage: => String): Unit = {
    if (!expression)
      throw new IllegalArgumentException(errorMessage)
  }

  @inline
  private[biginteger] final class QuotAndRem(val quot: BigInteger, val rem: BigInteger) {
    def toArray(): Array[BigInteger] = Array[BigInteger](quot, rem)
  }

  private[biginteger] object QuotAndRem {
    val ZERO_ZERO = new QuotAndRem(BigInteger.ZERO, BigInteger.ZERO)
  }
}

class BigInteger extends Number with Comparable[BigInteger] {
  import BigInteger._

  /** The magnitude of this big integer.
   *
   *  This array is in little endian order and each "digit" is a 32-bit unsigned
   *  integer. For example:
   *   - {@code 13} is represented as [ 13 ]
   *   - {@code -13} is represented as [ 13 ]
   *   - {@code 2^32 + 13} is represented as [ 13, 1 ]
   *   - {@code 2^64 + 13} is represented as [ 13, 0, 1 ]
   *   - {@code 2^31} is represented as [ Integer.MIN_VALUE ]
   *  The magnitude array may be longer than strictly necessary, which results
   *  in additional trailing zeros.
   */
  private[biginteger] var digits: Array[Int] = _

  /** The length of this in measured in ints. Can be less than digits.length(). */
  private[biginteger] var numberLength: Int = _

  /** The sign of this. */
  private[biginteger] var sign: Int = _

  private var firstNonzeroDigit: Int = firstNonzeroDigitNotSet

  /** Cache for the hash code. */
  private var _hashCode: Int = 0

  def this(byteArray: Array[Byte]) = {
    this()
    if (byteArray.length == 0)
      throw new NumberFormatException("Zero length BigInteger")

    if (byteArray(0) < 0) {
      sign = -1
      this.putBytesNegativeToIntegers(byteArray)
    } else {
      sign = 1
      this.putBytesPositiveToIntegers(byteArray)
    }

    this.cutOffLeadingZeroes()
  }

  def this(signum: Int, magnitude: Array[Byte]) = {
    this()
    checkNotNull(magnitude)
    if ((signum < -1) || (signum > 1))
      throw new NumberFormatException("Invalid signum value")
    if (signum == 0) {
      var i = 0
      while (i < magnitude.length) {
        if (magnitude(i) != 0)
          throw new NumberFormatException("signum-magnitude mismatch")
        i += 1
      }
    }

    if (magnitude.length == 0) {
      sign = 0
      numberLength = 1
      digits = Array(0)
    } else {
      sign = signum
      this.putBytesPositiveToIntegers(magnitude)
      this.cutOffLeadingZeroes()
    }
  }

  def this(bitLength: Int, certainty: Int, rnd: Random) = {
    this()
    if (bitLength < 2)
      throw new ArithmeticException("bitLength < 2")

    val me = Primality.consBigInteger(bitLength, certainty, rnd)
    sign = me.sign
    numberLength = me.numberLength
    digits = me.digits
  }

  def this(numBits: Int, rnd: Random) = {
    this()
    checkCriticalArgument(numBits >= 0, "numBits must be non-negative")
    if (numBits == 0) {
      sign = 0
      numberLength = 1
      digits = Array(0)
    } else {
      sign = 1
      numberLength = (numBits + 31) >> 5
      digits = new Array[Int](numberLength)
      var i = 0
      while (i < numberLength) {
        digits(i) = rnd.nextInt()
        i += 1
      }
      digits(numberLength - 1) >>>= (-numBits) & 31
      this.cutOffLeadingZeroes()
    }
  }

  def this(s: String, radix: Int) = {
    this()
    checkNotNull(s)
    if ((radix < java.lang.Character.MIN_RADIX) || (radix > java.lang.Character.MAX_RADIX))
      throw new NumberFormatException("Radix out of range")
    if (s.isEmpty)
      throw new NumberFormatException("Zero length BigInteger")

    this.setFromString(s, radix)
  }

  def this(s: String) = {
    this(s, 10)
  }

  /** Constructs a number which array is of size 1.
   *
   *  @param sign the sign of the number
   *  @param value the only one digit of array
   */
  private[biginteger] def this(sign: Int, value: Int) = {
    this()
    this.sign = sign
    numberLength = 1
    digits = Array(value)
  }

  /** Creates a new {@code BigInteger} with the given sign and magnitude.
   *
   *  This constructor does not create a copy, so any changes to the reference will
   *  affect the new number.
   *
   *  @param signum The sign of the number represented by {@code digits}
   *  @param digits The magnitude of the number
   */
  private[biginteger] def this(signum: Int, digits: Array[Int]) = {
    this()
    if (digits.length == 0) {
      this.sign = 0
      this.numberLength = 1
      this.digits = Array(0)
    } else {
      this.sign = signum
      this.numberLength = digits.length
      this.digits = digits
      this.cutOffLeadingZeroes()
    }
  }

  /** Constructs a number without to create new space.
   *
   *  This construct should be used only if the three fields of representation
   *  are known.
   *
   *  @param sign the sign of the number
   *  @param numberLength the length of the internal array
   *  @param digits a reference of some array created before
   */
  private[biginteger] def this(sign: Int, numberLength: Int, digits: Array[Int]) = {
    this()
    this.sign = sign
    this.numberLength = numberLength
    this.digits = digits
  }

  /** Creates a new {@code BigInteger} with value equal to the specified {@code long}.
   *
   *  @param sign the sign of the number
   *  @param lVal the value of the new {@code BigInteger}.
   */
  private[biginteger] def this(sign: Int, lVal: Long) = {
    this()
    this.sign = sign
    val hi = (lVal >>> 32).toInt
    if (hi == 0) {
      numberLength = 1
      digits = Array(lVal.toInt)
    } else {
      numberLength = 2
      digits = Array(lVal.toInt, hi)
    }
  }

  def abs(): BigInteger = {
    if (sign < 0) new BigInteger(1, numberLength, digits)
    else this
  }

  def add(bi: BigInteger): BigInteger = Elementary.add(this, bi)

  def and(bi: BigInteger): BigInteger = Logical.and(this, bi)

  def andNot(bi: BigInteger): BigInteger = Logical.andNot(this, bi)

  def bitCount(): Int = BitLevel.bitCount(this)

  def bitLength(): Int = BitLevel.bitLength(this)

  def clearBit(n: Int): BigInteger = {
    if (testBit(n)) BitLevel.flipBit(this, n)
    else this
  }

  def compareTo(bi: BigInteger): Int = {
    if (sign > bi.sign) GREATER
    else if (sign < bi.sign) LESS
    else if (numberLength > bi.numberLength) sign
    else if (numberLength < bi.numberLength) -bi.sign
    // else Equal sign and equal numberLength
    else sign * Elementary.compareArrays(digits, bi.digits, numberLength)
  }

  def divide(divisor: BigInteger): BigInteger = {
    // simple cases
    if (divisor.sign == 0) {
      throw new ArithmeticException("BigInteger divide by zero")
    }

    if (divisor.isOne) {
      if (divisor.sign > 0)
        return this
      else
        return this.negate()
    }

    if (divisor.numberLength >= Division.whenBurnikelZiegler &&
      numberLength - divisor.numberLength >= Division.whenBurnikelZieglerDifferent) {
      return divideBurnikelZiegler(divisor)
    }

    divideKnuth(divisor)
  }

  private[biginteger] def divideKnuth(divisor: BigInteger): BigInteger = {
    val divisorSign = divisor.sign
    val thisSign = sign
    val thisLen = numberLength
    val divisorLen = divisor.numberLength
    if (thisLen + divisorLen == 2) {
      var bi = (digits(0) & 0xFFFFFFFFL) / (divisor.digits(0) & 0xFFFFFFFFL)
      if (thisSign != divisorSign)
        bi = -bi
      valueOf(bi)
    } else {
      val cmp = {
        if (thisLen != divisorLen) {
          if (thisLen > divisorLen) 1
          else -1
        } else {
          Elementary.compareArrays(digits, divisor.digits, thisLen)
        }
      }

      if (cmp == EQUALS) {
        if (thisSign == divisorSign) ONE
        else MINUS_ONE
      } else if (cmp == LESS) {
        ZERO
      } else {
        val resLength = thisLen - divisorLen + 1
        val resDigits = new Array[Int](resLength)
        val resSign = if (thisSign == divisorSign) 1 else -1
        if (divisorLen == 1) {
          Division.divideArrayByInt(resDigits, digits, thisLen, divisor.digits(0))
        } else {
          Division.divide(resDigits, resLength, digits, thisLen, divisor.digits, divisorLen)
        }
        val result = new BigInteger(resSign, resLength, resDigits)
        result.cutOffLeadingZeroes()
        result
      }
    }
  }

  private[biginteger] def divideBurnikelZiegler(divisor: BigInteger): BigInteger =
    divideAndRemainderBurnikelZiegler(divisor).quot

  def divideAndRemainder(divisor: BigInteger): Array[BigInteger] = {
    divideAndRemainderImpl(divisor).toArray()
  }

  @inline
  private[biginteger] def divideAndRemainderImpl(divisor: BigInteger): QuotAndRem = {
    if (divisor.sign == 0)
      throw new ArithmeticException("BigInteger divide by zero")

    if (divisor.numberLength >= Division.whenBurnikelZiegler &&
      numberLength - divisor.numberLength >= Division.whenBurnikelZieglerDifferent) {
      return divideAndRemainderBurnikelZiegler(divisor)
    }

    divideAndRemainderKnuth(divisor)
  }

  private[biginteger] def divideAndRemainderKnuth(divisor: BigInteger): QuotAndRem = {
    val divisorSign = divisor.sign
    val divisorLen = divisor.numberLength
    val divisorDigits = divisor.digits
    if (divisorLen == 1) {
      Division.divideAndRemainderByInteger(this, divisorDigits(0), divisorSign)
    } else {
      // res[0] is a quotient and res[1] is a remainder:
      val thisDigits = digits
      val thisLen = numberLength
      val cmp = {
        if (thisLen != divisorLen) {
          if (thisLen > divisorLen) 1
          else -1
        } else {
          Elementary.compareArrays(thisDigits, divisorDigits, thisLen)
        }
      }

      if (cmp < 0) {
        new QuotAndRem(ZERO, this)
      } else {
        val thisSign = sign
        val quotientLength = thisLen - divisorLen + 1
        val remainderLength = divisorLen
        val quotientSign = if (thisSign == divisorSign) 1 else -1
        val quotientDigits = new Array[Int](quotientLength)
        val remainderDigits = Division.divide(quotientDigits, quotientLength,
            thisDigits, thisLen, divisorDigits, divisorLen)
        val result0 = new BigInteger(quotientSign, quotientLength, quotientDigits)
        val result1 = new BigInteger(thisSign, remainderLength, remainderDigits)
        result0.cutOffLeadingZeroes()
        result1.cutOffLeadingZeroes()
        new QuotAndRem(result0, result1)
      }
    }
  }

  private[biginteger] def divideAndRemainderBurnikelZiegler(divisor: BigInteger): QuotAndRem = {
    val r = Division.divideAndRemainderBurnikelZieglerPositive(abs(), divisor.abs())

    val quot =
      if (sign * divisor.sign >= 0) r.quot
      else r.quot.negate()

    val rem =
      if (signum() >= 0) r.rem
      else r.rem.negate()

    new QuotAndRem(quot, rem)
  }

  private[biginteger] def getBlock(index: Int, numBlocks: Int, blockLength: Int): BigInteger = {
    val blockStart = index * blockLength

    var blockEnd = 0
    if (index == numBlocks - 1) {
      blockEnd = numberLength
    } else {
      blockEnd = (index + 1) * blockLength
    }

    if (blockStart >= numberLength || blockEnd > numberLength) {
      return new BigInteger()
    }

    val newDigits = new Array[Int](blockEnd - blockStart)
    System.arraycopy(digits, blockStart, newDigits, 0, newDigits.length)
    new BigInteger(sign, newDigits.length, newDigits)
  }

  override def doubleValue(): Double =
    java.lang.Double.parseDouble(this.toString)

  override def equals(x: Any): Boolean = x match {
    case that: BigInteger =>
      this.sign == that.sign &&
      this.numberLength == that.numberLength &&
      this.equalsArrays(that.digits)
    case _ => false
  }

  def flipBit(n: Int): BigInteger = {
    if (n < 0)
      throw new ArithmeticException("Negative bit address")

    BitLevel.flipBit(this, n)
  }

  override def floatValue(): Float =
    java.lang.Float.parseFloat(this.toString)

  def gcd(bi: BigInteger): BigInteger = {
    val val1 = this.abs()
    val val2 = bi.abs()
    // To avoid a possible division by zero
    if (val1.signum() == 0) {
      val2
    } else if (val2.signum() == 0) {
      val1
    } else if (((val1.numberLength == 1) && (val1.digits(0) > 0)) &&
        ((val2.numberLength == 1) && (val2.digits(0) > 0))) {
      // Optimization for small operands
      // (op2.bitLength() < 32) and (op1.bitLength() < 32)
      BigInteger.valueOf(Division.gcdBinary(val1.intValue(), val2.intValue()))
    } else {
      Division.gcdBinary(val1.copy(), val2.copy())
    }
  }

  def getLowestSetBit(): Int = {
    if (sign == 0) {
      -1
    } else {
      // (sign != 0) implies that exists some non zero digit
      val i = getFirstNonzeroDigit
      (i << 5) + java.lang.Integer.numberOfTrailingZeros(digits(i))
    }
  }

  override def hashCode(): Int = {
    if (_hashCode != 0) {
      _hashCode
    } else {
      var hashCode = 0
      var i = 0
      while (i < numberLength) {
        hashCode = hashCode * 33 + digits(i)
        i += 1
      }
      _hashCode = hashCode * sign
      _hashCode
    }
  }

  override def intValue(): Int = sign * digits(0)

  def isProbablePrime(certainty: Int): Boolean =
    Primality.isProbablePrime(abs(), certainty)

  override def longValue(): Long = {
    val value =
      if (numberLength > 1) (digits(1).toLong << 32) | (digits(0) & 0xFFFFFFFFL)
      else digits(0) & 0xFFFFFFFFL
    sign * value
  }

  def max(bi: BigInteger): BigInteger = {
    if (this.compareTo(bi) == GREATER) this
    else bi
  }

  def min(bi: BigInteger): BigInteger = {
    if (this.compareTo(bi) == LESS) this
    else bi
  }

  def mod(m: BigInteger): BigInteger = {
    if (m.sign <= 0)
      throw new ArithmeticException("BigInteger: modulus not positive")

    val rem = remainder(m)
    if (rem.sign < 0) rem.add(m)
    else rem
  }

  def modInverse(m: BigInteger): BigInteger = {
    if (m.sign <= 0) {
      throw new ArithmeticException("BigInteger: modulus not positive")
    } else if (!(testBit(0) || m.testBit(0))) {
      // If both are even, no inverse exists
      throw new ArithmeticException("BigInteger not invertible.")
    } else if (m.isOne) {
      ZERO
    } else {
      // From now on: (m > 1)
      val res = Division.modInverseMontgomery(abs().mod(m), m)
      if (res.sign == 0)
        throw new ArithmeticException("BigInteger not invertible.")

      if (sign < 0) m.subtract(res)
      else res
    }
  }

  def modPow(exponent: BigInteger, m: BigInteger): BigInteger = {
    var _exponent = exponent
    if (m.sign <= 0)
      throw new ArithmeticException("BigInteger: modulus not positive")

    var base = if (signum() < 0 || compareTo(m) >= 0) mod(m) else this

    if (m.isOne || (_exponent.sign > 0 && base.sign == 0)) {
      BigInteger.ZERO
    } else if (base.sign == 0 && _exponent.sign == 0) {
      BigInteger.ONE
    } else if (equals(ONE)) {
      BigInteger.ONE
    } else {
      if (_exponent.sign < 0) {
        base = modInverse(m)
        _exponent = _exponent.negate()
      }
      // From now on: (m > 0) and (exponent >= 0)
      val res =
        if (m.testBit(0)) Division.oddModPow(base, _exponent, m)
        else Division.evenModPow(base, _exponent, m)
      if ((base.sign < 0) && _exponent.testBit(0))
        m.subtract(BigInteger.ONE).multiply(res).mod(m)
      else
        res
    }
  }

  def multiply(bi: BigInteger): BigInteger = {
    if (bi.sign == 0 || sign == 0) ZERO
    else if (bi.equals(ONE)) this
    else if (equals(ONE)) bi
    else Multiplication.multiply(this, bi)
  }

  def negate(): BigInteger = {
    if (sign == 0) this
    else new BigInteger(-sign, numberLength, digits)
  }

  def nextProbablePrime(): BigInteger = {
    if (sign < 0)
      throw new ArithmeticException("start < 0: " + this)

    Primality.nextProbablePrime(this, CERTAINTY)
  }

  def not(): BigInteger = Logical.not(this)

  def or(bi: BigInteger): BigInteger = Logical.or(this, bi)

  def pow(exp: Int): BigInteger =
    if (exp < 0) {
      throw new ArithmeticException("Negative exponent")
    } else if (exp == 0) {
      ONE
    } else if (exp == 1 || equals(ONE) || equals(ZERO)) {
      this
    } else if (!testBit(0)) {
      var x = 1
      while (!testBit(x)) {
        x += 1
      }
      getPowerOfTwo(x * exp).multiply(this.shiftRight(x).pow(exp))
    } else {
      // if even take out 2^x factor which we can calculate by shifting.
      Multiplication.pow(this, exp)
    }

  def remainder(divisor: BigInteger): BigInteger = {
    if (divisor.sign == 0) {
      throw new ArithmeticException("BigInteger divide by zero")
    }

    if (numberLength < divisor.numberLength) {
      return this
    }

    if (isOne) {
      if (sign == 1) return ONE
      else divisor subtract ONE
    }

    if (divisor.numberLength >= Division.whenBurnikelZiegler &&
      numberLength - divisor.numberLength >= Division.whenBurnikelZieglerDifferent) {
      return remainderBurnikelZiegler(divisor)
    }

    remainderKnuth(divisor)
  }

  private[biginteger] def remainderKnuth(divisor: BigInteger): BigInteger = {
    val thisLen = numberLength
    val divisorLen = divisor.numberLength

    val cmp =
      if (thisLen == divisorLen) Elementary.compareArrays(digits, divisor.digits, thisLen)
      else thisLen.compareTo(divisorLen)

    if (cmp == LESS) {
      return this
    }

    val resLength = divisorLen
    var resDigits = new Array[Int](resLength)
    if (resLength == 1) {
      resDigits(0) = Division.remainderArrayByInt(digits, thisLen, divisor.digits(0))
    } else {
      val qLen = thisLen - divisorLen + 1
      resDigits = Division.divide(null, qLen, digits, thisLen, divisor.digits, divisorLen)
    }
    val result = new BigInteger(sign, resLength, resDigits)
    result.cutOffLeadingZeroes()
    result
  }

  private[biginteger] def remainderBurnikelZiegler(divisor: BigInteger): BigInteger =
    divideAndRemainderBurnikelZiegler(divisor).rem

  def setBit(n: Int): BigInteger = {
    if (testBit(n)) this
    else BitLevel.flipBit(this, n)
  }

  def shiftLeft(n: Int): BigInteger = {
    if (n == 0 || sign == 0) this
    else if (n > 0) BitLevel.shiftLeft(this, n)
    else BitLevel.shiftRight(this, -n)
  }

  def shiftRight(n: Int): BigInteger = {
    if (n == 0 || sign == 0) this
    else if (n > 0) BitLevel.shiftRight(this, n)
    else BitLevel.shiftLeft(this, -n)
  }

  private[biginteger] def getLower(n: Int): BigInteger = {
    val intCount: Int = n >> 5
    if (intCount == 0 || sign == 0) ZERO
    else if (numberLength < intCount) this
    else {
      val copyDigits = new Array[Int](intCount)
      System.arraycopy(digits, 0, copyDigits, 0, intCount)
      val res = new BigInteger(1, intCount, copyDigits)
      res.cutOffLeadingZeroes()
      res
    }
  }
  def signum(): Int = sign

  def subtract(bi: BigInteger): BigInteger = Elementary.subtract(this, bi)

  def testBit(n: Int): Boolean = {
    val intCount = n >> 5

    if (n == 0) {
      (digits(0) & 1) != 0
    } else if (n < 0) {
      throw new ArithmeticException("Negative bit address")
    } else if (intCount >= numberLength) {
      sign < 0
    } else if (sign < 0 && intCount < getFirstNonzeroDigit) {
      false
    } else {
      var digit = digits(intCount)
      if (sign < 0)
        digit = if (getFirstNonzeroDigit == intCount) -digit else ~digit
      val i = 1 << (n & 31)
      (digit & i) != 0
    }
  }

  def toByteArray(): Array[Byte] = {
    if (this.sign == 0)
      return Array(0.toByte) // scalastyle:ignore

    val temp: BigInteger = this
    val bitLen = bitLength()
    val firstNonZeroDigit = getFirstNonzeroDigit
    var bytesLen = (bitLen >> 3) + 1
    /*
     * Puts the little-endian int array representing the magnitude of this
     * BigInteger into the big-endian byte array.
     */
    val bytes = new Array[Byte](bytesLen)
    var firstByteNumber = 0
    var digitIndex = firstNonZeroDigit
    var bytesInInteger = 4
    var digit: Int = 0

    val highBytes: Int = {
      if (bytesLen - (numberLength << 2) == 1) {
        val bytesZero = if (sign < 0) -1 else 0
        bytes(0) = bytesZero.toByte
        firstByteNumber += 1
        4
      } else {
        val hB: Int = bytesLen & 3
        if (hB == 0) 4
        else hB
      }
    }

    @inline
    @tailrec
    def loopBytes(tempDigit: Int => Unit): Unit = {
      if (bytesLen > firstByteNumber) {
        tempDigit(digitIndex)
        loopBytes(tempDigit)
      }
    }

    @inline
    def setBytesForDigit(tempDigit: Int): Unit = {
      digit = tempDigit
      digitIndex += 1
      if (digitIndex == numberLength)
        bytesInInteger = highBytes
      var i = 0
      while (i < bytesInInteger) {
        bytesLen -= 1
        bytes(bytesLen) = digit.toByte
        digit >>= 8
        i += 1
      }
    }

    bytesLen -= firstNonZeroDigit << 2
    if (sign < 0) {
      setBytesForDigit(-temp.digits(digitIndex))
      loopBytes(i => setBytesForDigit(~temp.digits(i)))
    } else {
      loopBytes(i => setBytesForDigit(temp.digits(i)))
    }
    bytes
  }

  override def toString(): String =
    Conversion.toDecimalScaledString(this)

  def toString(radix: Int): String =
    Conversion.bigInteger2String(this, radix)

  def xor(bi: BigInteger): BigInteger = Logical.xor(this, bi)

  /** Returns a copy of the current instance to achieve immutability. */
  private[biginteger] def copy(): BigInteger = {
    val copyDigits = new Array[Int](numberLength)
    System.arraycopy(digits, 0, copyDigits, 0, numberLength)
    new BigInteger(sign, numberLength, copyDigits)
  }

  private[biginteger] def cutOffLeadingZeroes(): Unit = {
    var len = numberLength - 1
    while (len > 0 && digits(len) == 0) {
      len -= 1
    }
    if (digits(len) == 0) {
      sign = 0
    }
    numberLength = len + 1
  }

  private[biginteger] def equalsArrays(b: Array[Int]): Boolean = {
    // scalastyle:off return
    var i = 0
    while (i != numberLength) {
      if (digits(i) != b(i))
        return false
      i += 1
    }
    true
    // scalastyle:on return
  }

  private[biginteger] def getFirstNonzeroDigit: Int = {
    if (firstNonzeroDigit == firstNonzeroDigitNotSet) {
      firstNonzeroDigit = {
        if (this.sign == 0) {
          -1
        } else {
          var i = 0
          while (digits(i) == 0) {
            i += 1
          }
          i
        }
      }
    }
    firstNonzeroDigit
  }

  /** Tests if {@code this.abs()} is equals to {@code ONE}. */
  private[biginteger] def isOne: Boolean =
    numberLength == 1 && digits(0) == 1

  private[biginteger] def shiftLeftOneBit(): BigInteger = {
    if (sign == 0) this
    else BitLevel.shiftLeftOneBit(this)
  }

  private[biginteger] def unCache(): Unit = {
    firstNonzeroDigit = firstNonzeroDigitNotSet
    _hashCode = 0
  }

  /** Puts a big-endian byte array into a little-endian applying two complement. */
  private def putBytesNegativeToIntegers(byteValues: Array[Byte]): Unit = {
    var bytesLen = byteValues.length
    val highBytes = bytesLen & 3
    numberLength = (bytesLen >> 2) + (if (highBytes == 0) 0 else 1)
    digits = new Array[Int](numberLength)
    var i = 0
    // Setting the sign
    digits(numberLength - 1) = -1
    // Put bytes to the int array starting from the end of the byte array

    while ({
      var oneMoreTime = false

      if (bytesLen > highBytes) {
        digits(i) =
          (byteValues(bytesLen - 1) & 0xFF) |
            (byteValues(bytesLen - 2) & 0xFF) << 8 |
            (byteValues(bytesLen - 3) & 0xFF) << 16 |
            (byteValues(bytesLen - 4) & 0xFF) << 24
        bytesLen -= 4
        if (digits(i) != 0) {
          digits(i) = -digits(i)
          firstNonzeroDigit = i
          i += 1
          while (bytesLen > highBytes) {
            digits(i) =
              (byteValues(bytesLen - 1) & 0xFF) |
                (byteValues(bytesLen - 2) & 0xFF) << 8 |
                (byteValues(bytesLen - 3) & 0xFF) << 16 |
                (byteValues(bytesLen - 4) & 0xFF) << 24
            bytesLen -= 4
            digits(i) = ~digits(i)
            i += 1
          }
        } else {
          i += 1
          oneMoreTime = true
        }
      }

      oneMoreTime
    }) ()

    if (highBytes != 0) {
      // Put the first bytes in the highest element of the int array
      if (firstNonzeroDigit != firstNonzeroDigitNotSet) {
        var j = 0
        while (j < bytesLen) {
          digits(i) = (digits(i) << 8) | (byteValues(j) & 0xFF)
          j += 1
        }
        digits(i) = ~digits(i)
      } else {
        var j = 0
        while (j < bytesLen) {
          digits(i) = (digits(i) << 8) | (byteValues(j) & 0xFF)
          j += 1
        }
        digits(i) = -digits(i)
      }
    }
  }

  /** Puts a big-endian byte array into a little-endian int array. */
  private def putBytesPositiveToIntegers(byteValues: Array[Byte]): Unit = {
    var bytesLen = byteValues.length
    val highBytes = bytesLen & 3
    numberLength = (bytesLen >> 2) + (if (highBytes == 0) 0 else 1)
    digits = new Array[Int](numberLength)

    // Put bytes to the int array starting from the end of the byte array
    var i = 0
    while (bytesLen > highBytes) {
      digits(i) =
        (byteValues(bytesLen - 1) & 0xFF)       |
        (byteValues(bytesLen - 2) & 0xFF) << 8  |
        (byteValues(bytesLen - 3) & 0xFF) << 16 |
        (byteValues(bytesLen - 4) & 0xFF) << 24
      bytesLen = bytesLen  -4
      i += 1
    }
    // Put the first bytes in the highest element of the int array
    var j = 0
    while (j < bytesLen) {
      digits(i) = (digits(i) << 8) | (byteValues(j) & 0xFF)
      j += 1
    }
  }

  /** @see BigInteger#BigInteger(String, int). */
  private def setFromString(s: String, radix: Int): Unit = {
    if (s == "" || s == "+" || s == "-")
      throw new NumberFormatException("Zero length BigInteger")

    val stringLength0 = s.length
    val endChar = stringLength0
    val (_sign, startChar, stringLength) = {
      if (s.charAt(0) == '-') (-1, 1, stringLength0 - 1)
      else if (s.charAt(0) == '+') (1, 1, stringLength0 - 1)
      else (1, 0, stringLength0)
    }

    // Validate that there are no further sign characters
    var i = startChar
    while (i < stringLength0) {
      val c = s.charAt(i)
      if (c == '+' || c == '-')
        throw new NumberFormatException("Illegal embedded sign character")
      i += 1
    }

    /*
     * We use the following algorithm: split a string into portions of n
     * characters and convert each portion to an integer according to the radix.
     * Then convert an exp(radix, n) based number to binary using the
     * multiplication method. See D. Knuth, The Art of Computer Programming,
     * vol. 2.
     */
    val charsPerInt = Conversion.DigitFitInInt(radix)
    var bigRadixDigitsLength = stringLength / charsPerInt
    val topChars = stringLength % charsPerInt
    if (topChars != 0)
      bigRadixDigitsLength += 1

    val _digits = new Array[Int](bigRadixDigitsLength)
    val bigRadix = Conversion.BigRadices(radix - 2)
    var digitIndex = 0
    var substrEnd = startChar + (if (topChars == 0) charsPerInt else topChars)
    var newDigit: Int = 0
    var substrStart = startChar
    while (substrStart < endChar) {
      val bigRadixDigit = java.lang.Integer.parseInt(s.substring(substrStart, substrEnd), radix)
      newDigit = Multiplication.multiplyByInt(_digits, digitIndex, bigRadix)
      newDigit += Elementary.inplaceAdd(_digits, digitIndex, bigRadixDigit)
      _digits(digitIndex) = newDigit
      digitIndex += 1
      substrStart = substrEnd
      substrEnd = substrStart + charsPerInt
    }

    this.sign = _sign
    this.numberLength = digitIndex
    this.digits = _digits
    this.cutOffLeadingZeroes()
  }
}

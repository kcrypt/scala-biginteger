/*
 * Scala.js (https://www.scala-js.org/)
 *
 * Copyright EPFL.
 *
 * Licensed under Apache License 2.0
 * (https://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package ky.korins.math

import java.util.Arrays

import org.scalatest.wordspec

class BigIntegerTest extends wordspec.AnyWordSpec {

  "accept 3 as a Byte Array" in {
    val bi = new BigInteger(Array[Byte](3))
    assert(3 == bi.intValue())
  }

  "accept 127 as a Byte Array" in {
    val bi = new BigInteger(Array[Byte](127))
    assert(127 == bi.intValue())
  }

  "accept 3 as aLong" in {
    val bi = BigInteger.valueOf(3L)
    assert(3 == bi.intValue())
    assert(3L == bi.longValue())
  }

  "accept 999999999 as aLong" in {
    val bi = BigInteger.valueOf(999999999L)
    assert(999999999 == bi.intValue())
    assert(999999999L == bi.longValue())
  }

  "accept 9999999999 as aLong" in {
    val bi = BigInteger.valueOf(9999999999L)
    assert(9999999999L == bi.longValue())
  }

  "accept -999999999 as aLong" in {
    val bi = BigInteger.valueOf(-999999999L)
    assert(-999999999 == bi.intValue())
    assert(-999999999L == bi.longValue())
  }

  "accept -9999999999 as aLong" in {
    val bi = BigInteger.valueOf(-9999999999L)
    assert(-9999999999L == bi.longValue())
  }

  "accept 99 as a string" in {
    val bi = new BigInteger("99")
    assert(99 == bi.intValue())
    assert(99L == bi.longValue())
  }

  "accept 999999999 as sting" in {
    val bi = new BigInteger("999999999")
    assert(999999999 == bi.intValue())
    assert(999999999L == bi.longValue())
  }

  "accept 9999999999 as a string" in {
    val bi = new BigInteger("9999999999")
    assert(9999999999L == bi.longValue())
  }

  "accept -99 as a string" in {
    val bi = new BigInteger("-99")
    assert(-99 == bi.intValue())
    assert(-99L == bi.longValue())
  }

  "accept -999999999 as sting" in {
    val bi = new BigInteger("-999999999")
    assert(-999999999 == bi.intValue())
    assert(-999999999L == bi.longValue())
  }

  "accept -9999999999 as a string" in {
    val bi = new BigInteger("-9999999999")
    assert(-9999999999L == bi.longValue())
  }

  "intialise from byte array of Pos two's complement" in {
    val eBytesSignum = Array[Byte](27, -15, 65, 39)
    val eBytes = Array[Byte](27, -15, 65, 39)
    val expSignum = new BigInteger(eBytesSignum)
    assert(Arrays.equals(eBytes, expSignum.toByteArray))
  }

  "intialise from byte array of Neg two's complement" in {
    val eBytesSignum = Array[Byte](-27, -15, 65, 39)
    val eBytes = Array[Byte](-27, -15, 65, 39)
    val expSignum = new BigInteger(eBytesSignum)
    assert(Arrays.equals(eBytes, expSignum.toByteArray))
  }

  "intialise from Pos byte array with explicit sign" in {
    val eBytes = Array[Byte](27, -15, 65, 39)
    val eSign = 1
    val exp = new BigInteger(eSign, eBytes)
    assert(Arrays.equals(eBytes, exp.toByteArray))
  }

  "intialise from Zero byte array with explicit sign" in {
    val eBytes = Array[Byte](0, 0, 0, 0)
    val eSign = 0
    val exp = new BigInteger(eSign, eBytes)
    assert(Arrays.equals(Array[Byte](0), exp.toByteArray))
  }

  "intialise from Neg small byte array with explicit sign" in {
    val eBytes = Array[Byte](27)
    val eSign = -1
    val eRes = Array[Byte](-27)
    val exp = new BigInteger(eSign, eBytes)
    assert(Arrays.equals(eRes, exp.toByteArray))
  }

  "intialise from Neg byte array with explicit sign" in {
    val eBytes = Array[Byte](27, -15, 65, 39)
    val eSign = -1
    val eRes = Array[Byte](-28, 14, -66, -39)
    val exp = new BigInteger(eSign, eBytes)
    assert(Arrays.equals(eRes, exp.toByteArray))
  }

  "intialise both Pos byte arrays arrays the same" in {
    val eBytes = Array[Byte](27, -15, 65, 39)
    val eSign = 1
    val exp = new BigInteger(eSign, eBytes)
    val eBytesSignum = Array[Byte](27, -15, 65, 39)
    val expSignum = new BigInteger(eBytesSignum)

    assert(0 == expSignum.compareTo(exp))
    assert(Arrays.equals(eBytes, exp.toByteArray))
    assert(Arrays.equals(eBytes, expSignum.toByteArray))
    assert(Arrays.equals(exp.toByteArray, expSignum.toByteArray))
  }

  "intialise both Neg byte arrays arrays the same" in {
    val eBytes = Array[Byte](27, -15, 65, 39)
    val eSign = -1
    val eRes = Array[Byte](-28, 14, -66, -39)
    val exp = new BigInteger(eSign, eBytes)
    val eBytesSignum = Array[Byte](-28, 14, -66, -39)
    val expSignum = new BigInteger(eBytesSignum)

    assert(exp.toString == expSignum.toString)
    assert(Arrays.equals(eRes, exp.toByteArray))
    assert(Arrays.equals(eBytesSignum, expSignum.toByteArray))
    assert(Arrays.equals(exp.toByteArray, expSignum.toByteArray))
  }

  "uncache reset hashcode" in {
    val bigInteger = new BigInteger("123")
    val before = bigInteger.hashCode()
    Elementary.inplaceAdd(bigInteger, 2)
    assert(before != bigInteger.hashCode())
  }

  "nextProbablePrime returns the first prime" in {
    val firstPrime = new BigInteger("234702379496804530857127341657568429621")
    val nextPrime = firstPrime.nextProbablePrime()
    val diff = (nextPrime subtract firstPrime).intValue()
    assert(diff > 0)
    var i = 1
    while (i < diff) {
      val candidate = firstPrime.add(BigInteger.valueOf(i))
      val isCandidatePrime = candidate.isProbablePrime(100)
      assert(!isCandidatePrime, s"is prime: $candidate")
      i += 1
    }
  }
}

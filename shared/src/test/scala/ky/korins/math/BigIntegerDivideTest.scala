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

// scalastyle:off line.size.limit
/*
 * Ported by Alistair Johnson from
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerDivideTest.java
 */
// scalastyle:on line.size.limit

package ky.korins.math

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerDivideTest extends wordspec.AnyWordSpec {

  def compareBZandKnuth(a: BigInteger, b: BigInteger): Unit = {
    val knuth = a divideAndRemainderKnuth b
    val BZ = a divideAndRemainderBurnikelZiegler b
    assert(knuth.rem equals BZ.rem)
    assert(knuth.quot equals BZ.quot)
  }

  "Case1" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](0)
    val aSign = 1
    val bSign = 0
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    try {
      aNumber.divide(bNumber)
      fail()
    } catch {
      case _: Throwable => // As expected
    }
  }

  "Case10" in {
    val aBytes = Array[Byte](1, 100, 56, 7, 98, -1, 39, -128, 127, 5, 6, 7, 8, 9)
    val bBytes = Array[Byte](15, 48, -29, 7, 98, -1, 39, -128)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](23, 115, 11, 78, 35, -11)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case11" in {
    val aBytes = Array[Byte](0)
    val bBytes = Array[Byte](15, 48, -29, 7, 98, -1, 39, -128)
    val aSign = 0
    val bSign = -1
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case12" in {
    val bBytes = Array[Byte](15, 48, -29, 7, 98, -1, 39, -128)
    val bSign = -1
    val rBytes = Array[Byte](0)
    val aNumber = BigInteger.ZERO
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case13" in {
    val aBytes = Array[Byte](15, 48, -29, 7, 98, -1, 39, -128)
    val aSign = 1
    val rBytes = Array[Byte](15, 48, -29, 7, 98, -1, 39, -128)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = BigInteger.ONE
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case14" in {
    val rBytes = Array[Byte](1)
    val aNumber = BigInteger.ONE
    val bNumber = BigInteger.ONE
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case15" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](0)
    val aSign = 1
    val bSign = 0
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    try {
      aNumber.remainder(bNumber)
      fail()
    } catch {
      case _: Throwable => // As expected
    }
  }

  "Case16" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127)
    val bBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.remainder(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case17" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 75)
    val bBytes = Array[Byte](27, -15, 65, 39, 100)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](12, -21, 73, 56, 27)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.remainder(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case18" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 75)
    val bBytes = Array[Byte](27, -15, 65, 39, 100)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](-13, 20, -74, -57, -27)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.remainder(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case19" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 75)
    val bBytes = Array[Byte](27, -15, 65, 39, 100)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](12, -21, 73, 56, 27)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.remainder(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case2" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val aSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = BigInteger.ZERO
    try {
      aNumber.divide(bNumber)
      fail()
    } catch {
      case _: Throwable => // As expected
    }
  }

  "Case20" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 75)
    val bBytes = Array[Byte](27, -15, 65, 39, 100)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](-13, 20, -74, -57, -27)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.remainder(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case21" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 75)
    val bBytes = Array[Byte](27, -15, 65, 39, 100)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Array[Byte]](Array[Byte](-5, 94, -115, -74, -85, 84), Array[Byte](-13, 20, -74, -57, -27))
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divideAndRemainder(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result(0).toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(0)(i) == resBytes(i))
    }
    assert(-1 == result(0).signum())
    resBytes = result(1).toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(1)(i) == resBytes(i))
      assert(-1 == result(1).signum())
    }
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case22" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](1, 30, 40, 56, -1, 45)
    val aSign = 1
    val bSign = -1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    try {
      aNumber.mod(bNumber)
      fail()
    } catch {
      case _: Throwable => // As expected
    }
  }

  "Case23" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 75)
    val bBytes = Array[Byte](27, -15, 65, 39, 100)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](12, -21, 73, 56, 27)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.mod(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case24" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 75)
    val bBytes = Array[Byte](27, -15, 65, 39, 100)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](15, 5, -9, -17, 73)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.mod(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case3" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127)
    val bBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](1)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case4" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127)
    val bBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](-1)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case5" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127)
    val bBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 1, 2, 3, 4, 5)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case6" in {
    val aBytes = Array[Byte](1, 100, 56, 7, 98, -1, 39, -128, 127)
    val bBytes = Array[Byte](15, 100, 56, 7, 98, -1, 39, -128, 127)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case7" in {
    val aBytes = Array[Byte](1, 100, 56, 7, 98, -1, 39, -128, 127, 5, 6, 7, 8, 9)
    val bBytes = Array[Byte](15, 48, -29, 7, 98, -1, 39, -128)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](23, 115, 11, 78, 35, -11)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case8" in {
    val aBytes = Array[Byte](1, 100, 56, 7, 98, -1, 39, -128, 127, 5, 6, 7, 8, 9)
    val bBytes = Array[Byte](15, 48, -29, 7, 98, -1, 39, -128)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](-24, -116, -12, -79, -36, 11)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Case9" in {
    val aBytes = Array[Byte](1, 100, 56, 7, 98, -1, 39, -128, 127, 5, 6, 7, 8, 9)
    val bBytes = Array[Byte](15, 48, -29, 7, 98, -1, 39, -128)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](-24, -116, -12, -79, -36, 11)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "DivisionKnuth1" in {
    val aBytes = Array[Byte](-7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](-3, -3, -3, -3)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](0, -5, -12, -33, -96, -36, -105, -56, 92, 15, 48, -109)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "DivisionKnuthFirstDigitsEqual" in {
    val aBytes = Array[Byte](2, -3, -4, -5, -1, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5)
    val bBytes = Array[Byte](2, -3, -4, -5, -1, -1, -1, -1)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](0, -1, -1, -1, -1, -2, -88, -60, 41)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "DivisionKnuthIsNormalized" in {
    val aBytes = Array[Byte](-9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5)
    val bBytes = Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](0, -9, -8, -7, -6, -5, -4, -3)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "DivisionKnuthMultiDigitsByOneDigit" in {
    val aBytes = Array[Byte](113, -83, 123, -5, 18, -34, 67, 39, -29)
    val bBytes = Array[Byte](2, -3, -4, -5)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](-38, 2, 7, 30, 109, -43)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "DivisionKnuthOneDigitByOneDigit" in {
    val aBytes = Array[Byte](113, -83, 123, -5)
    val bBytes = Array[Byte](2, -3, -4, -5)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](-37)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.divide(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "RemainderKnuth1" in {
    val aBytes = Array[Byte](-9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1)
    val bBytes = Array[Byte](0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 7, 18, -89)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.remainder(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "RemainderKnuthMultiDigitsByOneDigit" in {
    val aBytes = Array[Byte](113, -83, 123, -5, 18, -34, 67, 39, -29)
    val bBytes = Array[Byte](2, -3, -4, -50)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](2, -37, -60, 59)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.remainder(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "RemainderKnuthOneDigitByOneDigit" in {
    val aBytes = Array[Byte](113, -83, 123, -5)
    val bBytes = Array[Byte](2, -3, -4, -50)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](2, -9, -14, 53)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.remainder(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
    compareBZandKnuth(aNumber, bNumber)
  }

  "Burnikel Ziegler" should {
    "works as Knuth" in {
      // let define some big number
      val a = new BigInteger("8419057691237171769526959718411722848843" +
        "2105454512860118841924637915397990466516641156326190422878700" +
        "74860320157957374710321784452523478046344093773176278")
      val b = new BigInteger("9858442886863316202859337022714996607589" +
        "4274217713324274815365294862730835165149509747120147968402021" +
        "2650019891523721429602327835427295737126221693539477318178467" +
        "2856260027720879089257104092279548763843242501442410620632717" +
        "9355355367021841894259278973706770252069515085737379608714459" +
        "37028874960052419193721")
      val c = new BigInteger("1654208531169013576793196572182045798014" +
        "7994568744570237269358736160161789767308576224529553076948359" +
        "6406770116298996817116845630084222507312076692079214154696009" +
        "4462490742937305924431875183335180495324964387333616198518107" +
        "4521717629529971642417416382993024039364817583283072759761509" +
        "6088390860617563517655019240444086715204150347401430773574239" +
        "3159837094450943903090512442995843062932223170271313214701845" +
        "4620126921059242010098036350448109135983305481621970338561991" +
        "2196645624653657891950987893387634341247079630321520700064532" +
        "8305226068428633886001186820927890481765325439573803489560609" +
        "2812662060366561537817607566173419839284915967522762896584172" +
        "1556636400857290069199182189696689447858093100469325640636538" +
        "8461242358636290560222913481535347846756778973034817918284763" +
        "6778403225420987965339927565419538641553095953109217007061299" +
        "6988178842792375340515163835050250533468017866312759760490895" +
        "1372192706647271952640428834183053347418036449176686169010990" +
        "2487795500695765580486194405365639798366889028139311627504640" +
        "9907837341819756326515874352126310380952437103551591981524484" +
        "8525781088426004357964803692448615596954485107391796813400880" +
        "3569677244492503530308228867770669638311553954551912287368542" +
        "89392426177175310988111535916563086")
      val d = new BigInteger("6790741244239789609085847928751960763353" +
        "9391408717142846681808742042505221234348520717539217142317558" +
        "5451586215383088332749012419482051171687502489088718061987281" +
        "2524188453065834593178005611037069542346418199573589972488270" +
        "7867078319015192577115900465624584663456391629946089687327635" +
        "3283431001999951795146854016461804885097705160238925219292403" +
        "7572939247606095064211808843705378392904907623278652522072732" +
        "1586118299882270458112244739256274904651282364521485807902756" +
        "5755293537355797874920788074956134243808034492281024293115784" +
        "9667096704913904635403270789383557832300737238861282128430413" +
        "9442902737163820762617806121124651336644560544608641615536058" +
        "2484359801471356802698264696492553740693879058321670408456569" +
        "9333800851741573111210874352770277394820055674698026266919856" +
        "0084725648339100745140181071792929171600917535635676331858489" +
        "7461149058720559870208634240139209342629339813274956988976005" +
        "9933746712689530006010161003899672217869150903150794528870180" +
        "7732354970455989872070372148398204982945253260275310031365718" +
        "4064345233896082546206791471684932077315417334806955060379450" +
        "9043841955960912859370587541981867917801633607131839932977017" +
        "7405382453810144818648082183896799008467258845811735267209547" +
        "1912658038319157406397341825259365433914236734429697316571999" +
        "5810140564742409050009175061699023431240454250048026086470786" +
        "8588181388365729535526210379019734915736653912640135673786020" +
        "0168653125219264877283088393400772416778022976071449955016743" +
        "3718789964353313228431629875425427508053251461341423268768475" +
        "6974719307660733604957561650980416976935318408148593888607742" +
        "7888925321318752454959185664353522081336571490452945328924545" +
        "6665969142594093331395580329441899804750707736017419678958652" +
        "6776085056313681175496996450011877985430897599805663423802017" +
        "9917639165079061436822284238673093397960501803988665391347547" +
        "5723528638689356920650485686086049909564855769220044072566404" +
        "1593951063379334507072744960974756112557652594008972692882920" +
        "3535077202915076286284746843766043987202790528543308958995281" +
        "1544620594801074066975605070203031595929516002846934609488186" +
        "0378964580306439104826127526723707566413715978632001417083567" +
        "5967214798762679534179189533613144425123621592148652053530905" +
        "3911360828560655045003541095591637863983718557384308192957857" +
        "6378414105553936410851273243314297826769060759800754575151948" +
        "0989191507801749131230799555308777393230394560086232564526601" +
        "8875909355236481968970969050573836574515235216619386726941183" +
        "93109195122850437864988947362195162479278725553")
      // and test them!
      Seq(a, b, c, d).combinations(2).flatMap(_.permutations).foreach { n =>
        compareBZandKnuth(n.head, n.tail.head)
        compareBZandKnuth(n.tail.head, n.head)
      }
    }
  }
}

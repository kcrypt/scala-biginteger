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

class BigIntegerDivideTest extends wordspec.AnyWordSpec {

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
    for (i <- 0 until resBytes.length){
      assert(rBytes(0)(i) == resBytes(i))
    }
    assert(-1 == result(0).signum())
    resBytes = result(1).toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(1)(i) == resBytes(i))
      assert(-1 == result(1).signum())
    }
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
  }
}

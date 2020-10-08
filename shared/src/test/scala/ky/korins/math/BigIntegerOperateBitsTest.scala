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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerOperateBitsTest.java
 */
// scalastyle:on line.size.limit

package ky.korins.math

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerOperateBitsTest extends wordspec.AnyWordSpec {

  "BitCountNeg" in {
    val aNumber = new BigInteger("-12378634756382937873487638746283767238657872368748726875")
    assert(87 == aNumber.bitCount())
  }

  "BitCountPos" in {
    val aNumber = new BigInteger("12378634756343564757582937873487638746283767238657872368748726875")
    assert(107 == aNumber.bitCount())
  }

  "BitCountZero" in {
    val aNumber = new BigInteger("0")
    assert(0 == aNumber.bitCount())
  }

  "BitLengthNegative1" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
    val aSign = -1
    val aNumber = new BigInteger(aSign, aBytes)
    assert(108 == aNumber.bitLength())
  }

  "BitLengthNegative2" in {
    val aBytes = Array[Byte](-128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val aNumber = new BigInteger(aSign, aBytes)
    assert(96 == aNumber.bitLength())
  }

  "BitLengthNegative3" in {
    val aBytes = Array[Byte](1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    val aNumber = new BigInteger(aSign, aBytes)
    assert(80 == aNumber.bitLength())
  }

  "BitLengthPositive1" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
    val aSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    assert(108 == aNumber.bitLength())
  }

  "BitLengthPositive2" in {
    val aBytes = Array[Byte](-128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    assert(96 == aNumber.bitLength())
  }

  "BitLengthPositive3" in {
    val aBytes = Array[Byte](1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    assert(81 == aNumber.bitLength())
  }

  "BitLengthZero" in {
    val aNumber = new BigInteger("0")
    assert(0 == aNumber.bitLength())
  }

  "ClearBitException" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = -7
    val aNumber = new BigInteger(aSign, aBytes)
    assertThrows[ArithmeticException](aNumber.clearBit(number))
  }

  "ClearBitNegativeInside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 15
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -46, -92, -4, 14, 92, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ClearBitNegativeInside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 44
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -62, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ClearBitNegativeInside3" in {
    val as = "-18446744073709551615"
    val number = 2
    val aNumber = new BigInteger(as)
    val result = aNumber.clearBit(number)
    assert(result.toString == as)
  }

  "ClearBitNegativeInside4" in {
    val as = "-4294967295"
    val res = "-4294967296"
    val number = 0
    val aNumber = new BigInteger(as)
    val result = aNumber.clearBit(number)
    assert(result.toString == res)
  }

  "ClearBitNegativeInside5" in {
    val as = "-18446744073709551615"
    val res = "-18446744073709551616"
    val number = 0
    val aNumber = new BigInteger(as)
    val result = aNumber.clearBit(number)
    assert(result.toString == res)
  }

  "ClearBitNegativeOutside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 150
    val rBytes = Array[Byte](-65, -1, -1, -1, -1, -1, -2, 127, -57, -101, 1, 75, -90, -46, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ClearBitNegativeOutside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 165
    val rBytes = Array[Byte](-33, -1, -1, -1, -1, -1, -1, -1, -2, 127, -57,
        -101, 1, 75, -90, -46, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ClearBitPositiveInside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 20
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -31, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ClearBitPositiveInside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 17
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ClearBitPositiveInside3" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 45
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 13, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ClearBitPositiveInside4" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 50
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ClearBitPositiveInside5" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 63
    val rBytes = Array[Byte](1, -128, 56, 100, -2, 52, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ClearBitPositiveOutside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 150
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ClearBitPositiveOutside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 191
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ClearBitTopNegative" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -15, 35, 26)
    val aSign = -1
    val number = 63
    val rBytes = Array[Byte](-1, 127, -2, 127, -57, -101, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ClearBitZero" in {
    val aBytes = Array[Byte](0)
    val aSign = 0
    val number = 0
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
  }

  "ClearBitZeroOutside1" in {
    val aBytes = Array[Byte](0)
    val aSign = 0
    val number = 95
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.clearBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
  }

  "FlipBitException" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = -7
    val aNumber = new BigInteger(aSign, aBytes)
    assertThrows[ArithmeticException](aNumber.flipBit(number))
  }

  "FlipBitLeftmostNegative" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -15, 35, 26)
    val aSign = -1
    val number = 48
    val rBytes = Array[Byte](-1, 127, -57, -101, 14, -36, -26, 49)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "FlipBitLeftmostPositive" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -15, 35, 26)
    val aSign = 1
    val number = 48
    val rBytes = Array[Byte](0, -128, 56, 100, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "FlipBitNegativeInside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 15
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -46, -92, -4, 14, 92, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "FlipBitNegativeInside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 45
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -14, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "FlipBitNegativeInside3" in {
    val as = "-18446744073709551615"
    val res = "-18446744073709551611"
    val number = 2
    val aNumber = new BigInteger(as)
    val result = aNumber.flipBit(number)
    assert(result.toString == res)
  }

  "FlipBitNegativeInside4" in {
    val as = "-4294967295"
    val res = "-4294967296"
    val number = 0
    val aNumber = new BigInteger(as)
    val result = aNumber.flipBit(number)
    assert(result.toString == res)
  }

  "FlipBitNegativeInside5" in {
    val as = "-18446744073709551615"
    val res = "-18446744073709551616"
    val number = 0
    val aNumber = new BigInteger(as)
    val result = aNumber.flipBit(number)
    assert(result.toString == res)
  }

  "FlipBitNegativeOutside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 150
    val rBytes = Array[Byte](-65, -1, -1, -1, -1, -1, -2, 127, -57, -101, 1,
        75, -90, -46, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "FlipBitNegativeOutside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 191
    val rBytes = Array[Byte](-1, 127, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -2, 127, -57, -101, 1, 75, -90, -46, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "FlipBitPositiveInside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 15
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, -93, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "FlipBitPositiveInside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 45
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 13, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "FlipBitPositiveOutside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 150
    val rBytes = Array[Byte](64, 0, 0, 0, 0, 0, 1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "FlipBitPositiveOutside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 191
    val rBytes = Array[Byte](0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -128,
        56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "FlipBitZero" in {
    val aBytes = Array[Byte](0)
    val aSign = 0
    val number = 0
    val rBytes = Array[Byte](1)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "FlipBitZeroOutside1" in {
    val aBytes = Array[Byte](0)
    val aSign = 0
    val number = 62
    val rBytes = Array[Byte](64, 0, 0, 0, 0, 0, 0, 0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "FlipBitZeroOutside2" in {
    val aBytes = Array[Byte](0)
    val aSign = 0
    val number = 63
    val rBytes = Array[Byte](0, -128, 0, 0, 0, 0, 0, 0, 0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.flipBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "SetBitBug1331" in {
    val result = BigInteger.valueOf(0L).setBit(191)
    assert("3138550867693340381917894711603833208051177722232017256448" == result.toString)
    assert(1 == result.signum())
  }

  "SetBitException" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = -7
    val aNumber = new BigInteger(aSign, aBytes)
    assertThrows[ArithmeticException](aNumber.setBit(number))
  }

  "SetBitLeftmostNegative" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -15, 35, 26)
    val aSign = -1
    val number = 48
    val rBytes = Array[Byte](-1, 127, -57, -101, 14, -36, -26, 49)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "SetBitNegativeInside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 15
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -46, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "SetBitNegativeInside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 44
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -46, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "SetBitNegativeInside3" in {
    val as = "-18446744073709551615"
    val res = "-18446744073709551611"
    val number = 2
    val aNumber = new BigInteger(as)
    val result = aNumber.setBit(number)
    assert(result.toString == res)
  }

  "SetBitNegativeInside4" in {
    val as = "-4294967295"
    val number = 0
    val aNumber = new BigInteger(as)
    val result = aNumber.setBit(number)
    assert(as == result.toString)
  }

  "SetBitNegativeInside5" in {
    val as = "-18446744073709551615"
    val number = 0
    val aNumber = new BigInteger(as)
    val result = aNumber.setBit(number)
    assert(as == result.toString)
  }

  "SetBitNegativeOutside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 150
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -46, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "SetBitNegativeOutside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 191
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -46, -92, -4, 14, -36, -26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "SetBitPositiveInside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 20
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "SetBitPositiveInside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 17
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -13, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "SetBitPositiveInside3" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 45
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "SetBitPositiveInside4" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 50
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 93, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "SetBitPositiveOutside1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 150
    val rBytes = Array[Byte](64, 0, 0, 0, 0, 0, 1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "SetBitPositiveOutside2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 223
    val rBytes = Array[Byte](0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "SetBitTopPositive" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -15, 35, 26)
    val aSign = 1
    val number = 63
    val rBytes = Array[Byte](0, -128, 1, -128, 56, 100, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "SetBitZero" in {
    val aBytes = Array[Byte](0)
    val aSign = 0
    val number = 0
    val rBytes = Array[Byte](1)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "SetBitZeroOutside1" in {
    val aBytes = Array[Byte](0)
    val aSign = 0
    val number = 95
    val rBytes = Array[Byte](0, -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.setBit(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ShiftLeft1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 0
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftLeft(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ShiftLeft2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = -27
    val rBytes = Array[Byte](48, 7, 12, -97, -42, -117, 37, -85, 96)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftLeft(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ShiftLeft3" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 27
    val rBytes = Array[Byte](12, 1, -61, 39, -11, -94, -55, 106, -40, 31, -119, 24, -48, 0, 0, 0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftLeft(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ShiftLeft4" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 45
    val rBytes = Array[Byte](48, 7, 12, -97, -42, -117, 37, -85, 96, 126, 36, 99, 64, 0, 0, 0, 0, 0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftLeft(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ShiftLeft5" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 45
    val rBytes = Array[Byte](-49, -8, -13, 96, 41, 116, -38, 84, -97, -127,
        -37, -100, -64, 0, 0, 0, 0, 0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftLeft(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ShiftRight1" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 0
    val rBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftRight(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ShiftRight2" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = -27
    val rBytes = Array[Byte](12, 1, -61, 39, -11, -94, -55, 106, -40, 31, -119, 24, -48, 0, 0, 0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftRight(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ShiftRight3" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 27
    val rBytes = Array[Byte](48, 7, 12, -97, -42, -117, 37, -85, 96)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftRight(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ShiftRight4" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 45
    val rBytes = Array[Byte](12, 1, -61, 39, -11, -94, -55)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftRight(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ShiftRight5" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 300
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftRight(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
  }

  "ShiftRightNegNonZeroes" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    val number = 68
    val rBytes = Array[Byte](-25, -4, 121, -80, 20, -70, 109, 42)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftRight(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ShiftRightNegNonZeroesMul32" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 1, 0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    val number = 64
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -46, -92)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftRight(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ShiftRightNegZeroes" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    val number = 68
    val rBytes = Array[Byte](-25, -4, 121, -80, 20, -70, 109, 48)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftRight(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ShiftRightNegZeroesMul32" in {
    val aBytes = Array[Byte](1, -128, 56, 100, -2, -76, 89, 45, 91, 0, 0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    val number = 64
    val rBytes = Array[Byte](-2, 127, -57, -101, 1, 75, -90, -46, -91)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.shiftRight(number)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "TestBitException" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = -7
    val aNumber = new BigInteger(aSign, aBytes)
    assertThrows[ArithmeticException](aNumber.testBit(number))
  }

  "TestBitNegative1" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 7
    val aNumber = new BigInteger(aSign, aBytes)
    assert(aNumber.testBit(number))
  }

  "TestBitNegative2" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 45
    val aNumber = new BigInteger(aSign, aBytes)
    assert(!aNumber.testBit(number))
  }

  "TestBitNegative3" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = -1
    val number = 300
    val aNumber = new BigInteger(aSign, aBytes)
    assert(aNumber.testBit(number))
  }

  "TestBitPositive1" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 7
    val aNumber = new BigInteger(aSign, aBytes)
    assert(!aNumber.testBit(number))
  }

  "TestBitPositive2" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 45
    val aNumber = new BigInteger(aSign, aBytes)
    assert(aNumber.testBit(number))
  }

  "TestBitPositive3" in {
    val aBytes = Array[Byte](-1, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26)
    val aSign = 1
    val number = 300
    val aNumber = new BigInteger(aSign, aBytes)
    assert(!aNumber.testBit(number))
  }
}

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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerOrTest.java
 */
// scalastyle:on line.size.limit

package ky.korins.math

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerOrTest extends wordspec.AnyWordSpec {

  "NegNegFirstLonger" in {
    val aBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](-1, 1, 75, -89, -45, -2, -3, -18, -36, -17, -10, -3, -6, -7, -21)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NegNegFirstShorter" in {
    val aBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val bBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](-1, 1, 75, -89, -45, -2, -3, -18, -36, -17, -10, -3, -6, -7, -21)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NegNegSameLength" in {
    val aBytes = Array[Byte](-128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](-1, 127, -57, -101, -5, -5, -18, -38, -17, -2, -65, -2, -11, -3)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NegPos" in {
    val aBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val bBytes = Array[Byte](0)
    val aSign = -1
    val bSign = 0
    val rBytes = Array[Byte](-1, 1, 2, 3, 3, -6, -15, -24, -40, -49, -58, -67, -6, -15, -23)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NegPosFirstLonger" in {
    val aBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](-1, 127, -10, -57, -101, -1, -1, -2, -2, -91, -2, 31, -1, -11, 125, -22, -83, 30, 95)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NegPosFirstShorter" in {
    val aBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val bBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](-74, 91, 47, -5, -13, -7, -5, -33, -49, -65, -1, -9, -3)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NegPosSameLength" in {
    val aBytes = Array[Byte](-128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](-1, 5, 79, -73, -9, -76, -3, 78, -35, -17, 119)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "OneOne" in {
    val aBytes = Array[Byte](1)
    val bBytes = Array[Byte](1)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](1)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "PosNegFirstLonger" in {
    val aBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](-74, 91, 47, -5, -13, -7, -5, -33, -49, -65, -1, -9, -3)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "PosNegFirstShorter" in {
    val aBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val bBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](-1, 127, -10, -57, -101, -1, -1, -2, -2, -91, -2, 31, -1, -11, 125, -22, -83, 30, 95)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "PosNegSameLength" in {
    val aBytes = Array[Byte](-128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](-1, 1, -126, 59, 103, -2, -11, -7, -3, -33, -57, -3, -5, -5, -21)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "PosPosFirstLonger" in {
    val aBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](0, -128, 9, 56, 100, -2, -3, -3, -3, 95, 15, -9, 39, 58, -69, 87, 87, -17, -73)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "PosPosFirstShorter" in {
    val aBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val bBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](0, -128, 9, 56, 100, -2, -3, -3, -3, 95, 15, -9, 39, 58, -69, 87, 87, -17, -73)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "PosPosSameLength" in {
    val aBytes = Array[Byte](-128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](0, -2, -3, -4, -4, -1, -66, 95, 47, 123, 59, -13, 39, 30, -97)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "PosZero" in {
    val aBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val bBytes = Array[Byte](0)
    val aSign = 1
    val bSign = 0
    val rBytes = Array[Byte](0, -2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Regression" in {
    val x = new BigInteger("-1023")
    val r1 = x.and((BigInteger.ZERO.not()).shiftLeft(32))
    val r3 = x.and((BigInteger.ZERO.not().shiftLeft(32)).not())
    val result = r1.or(r3)
    assert(x equals result)
  }

  "ZeroNeg" in {
    val aBytes = Array[Byte](0)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = 0
    val bSign = -1
    val rBytes = Array[Byte](-1, 1, 2, 3, 3, -6, -15, -24, -40, -49, -58, -67, -6, -15, -23)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "ZeroOne" in {
    val aBytes = Array[Byte](0)
    val bBytes = Array[Byte](1)
    val aSign = 0
    val bSign = 1
    val rBytes = Array[Byte](1)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ZeroPos" in {
    val aBytes = Array[Byte](0)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = 0
    val bSign = 1
    val rBytes = Array[Byte](0, -2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ZeroZero" in {
    val aBytes = Array[Byte](0)
    val bBytes = Array[Byte](0)
    val aSign = 0
    val bSign = 0
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.or(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
     assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
  }
}

/*
 * scala-biginteger - highly optimized BigInteger implementation for scala, scala-js and scala-native.
 *
 * Copyright 2020 Kirill A. Korinsky <kirill@korins.ky>
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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerModPowTest.java
 */
// scalastyle:on line.size.limit

package ky.korins.math

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerModPowTest extends wordspec.AnyWordSpec {

  "sGcdFirstOne" in {
    val aBytes = Array[Byte](1, 0, 0, 0, 0)
    val big = new BigInteger(1, aBytes)
    assert(1 == BigInteger.ONE.gcd(big).intValue)
    assert(1 == BigInteger.ONE.gcd(BigInteger.ZERO).intValue)
  }

  "sGcdSecondOne" in {
    val aBytes = Array[Byte](1, 0, 0, 0, 0)
    val big = new BigInteger(1, aBytes)
    assert(1 == big.gcd(BigInteger.ONE).intValue)
    assert(1 == BigInteger.ZERO.gcd(BigInteger.ONE).intValue)
  }

  "sGcdBothOne" in {
    assert(1 == BigInteger.ONE.gcd(BigInteger.ONE).intValue)
  }

  "GcdBothZeros" in {
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger("0")
    val bNumber = BigInteger.valueOf(0L)
    val result = aNumber.gcd(bNumber)
    val resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
  }

  "GcdFirstLonger" in {
    val aBytes = Array[Byte](-15, 24, 123, 56, -11, -112, -34, -98, 8, 10, 12, 14, 25, 125, -15, 28, -127)
    val bBytes = Array[Byte](-12, 1, 0, 0, 0, 23, 44, 55, 66)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](7)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.gcd(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "GcdFirstZero" in {
    val aBytes = Array[Byte](0)
    val bBytes = Array[Byte](15, 24, 123, 57, -15, 24, 123, 57, -15, 24, 123, 57)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](15, 24, 123, 57, -15, 24, 123, 57, -15, 24, 123, 57)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.gcd(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "GcdFirstZero2" in {
    val bBytes = Array[Byte](15, 24, 123, 57, -15, 24, 123, 57, -15, 24, 123, 57)
    val bSign = 1
    val rBytes = Array[Byte](15, 24, 123, 57, -15, 24, 123, 57, -15, 24, 123, 57)
    val aNumber = BigInteger.ZERO
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.gcd(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "GcdSecondLonger" in {
    val aBytes = Array[Byte](-12, 1, 0, 0, 0, 23, 44, 55, 66)
    val bBytes = Array[Byte](-15, 24, 123, 56, -11, -112, -34, -98, 8, 10, 12, 14, 25, 125, -15, 28, -127)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](7)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.gcd(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "GcdSecondZero" in {
    val aBytes = Array[Byte](15, 24, 123, 57, -15, 24, 123, 57, -15, 24, 123, 57)
    val bBytes = Array[Byte](0)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](15, 24, 123, 57, -15, 24, 123, 57, -15, 24, 123, 57)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.gcd(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "modInverseException" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val mBytes = Array[Byte](1, 2, 3)
    val aSign = 1
    val mSign = -1
    val aNumber = new BigInteger(aSign, aBytes)
    val modulus = new BigInteger(mSign, mBytes)
    assertThrows[ArithmeticException](aNumber.modInverse(modulus))
  }

  "modInverseNeg1" in {
    val aBytes = Array[Byte](15, 24, 123, 56, -11, -112, -34, -98, 8, 10, 12, 14, 25, 125, -15, 28, -127)
    val mBytes = Array[Byte](2, 122, 45, 36, 100)
    val aSign = -1
    val mSign = 1
    val rBytes = Array[Byte](0, -41, 4, -91, 27)
    val aNumber = new BigInteger(aSign, aBytes)
    val modulus = new BigInteger(mSign, mBytes)
    val result = aNumber.modInverse(modulus)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "modInverseNeg2" in {
    val aBytes = Array[Byte](-15, 24, 123, 57, -15, 24, 123, 57, -15, 24, 123, 57)
    val mBytes = Array[Byte](122, 2, 4, 122, 2, 4)
    val rBytes = Array[Byte](85, 47, 127, 4, -128, 45)
    val aNumber = new BigInteger(aBytes)
    val modulus = new BigInteger(mBytes)
    val result = aNumber.modInverse(modulus)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "modInverseNonInvertible" in {
    val aBytes = Array[Byte](-15, 24, 123, 56, -11, -112, -34, -98, 8, 10, 12, 14, 25, 125, -15, 28, -127)
    val mBytes = Array[Byte](-12, 1, 0, 0, 0, 23, 44, 55, 66)
    val aSign = 1
    val mSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    val modulus = new BigInteger(mSign, mBytes)
    assertThrows[ArithmeticException](aNumber.modInverse(modulus))
  }

  "modInversePos1" in {
    val aBytes = Array[Byte](24, 123, 56, -11, -112, -34, -98, 8, 10, 12, 14, 25, 125, -15, 28, -127)
    val mBytes = Array[Byte](122, 45, 36, 100, 122, 45)
    val aSign = 1
    val mSign = 1
    val rBytes = Array[Byte](47, 3, 96, 62, 87, 19)
    val aNumber = new BigInteger(aSign, aBytes)
    val modulus = new BigInteger(mSign, mBytes)
    val result = aNumber.modInverse(modulus)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "modInversePos2" in {
    val aBytes = Array[Byte](15, 24, 123, 56, -11, -112, -34, -98, 8, 10, 12, 14, 25, 125, -15, 28, -127)
    val mBytes = Array[Byte](2, 122, 45, 36, 100)
    val aSign = 1
    val mSign = 1
    val rBytes = Array[Byte](1, -93, 40, 127, 73)
    val aNumber = new BigInteger(aSign, aBytes)
    val modulus = new BigInteger(mSign, mBytes)
    val result = aNumber.modInverse(modulus)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "testmodInverse - #1764" in {
    def test(a: BigInt, b: BigInt, expexted: BigInt): Unit =
      assert(a.modInverse(b) equals expexted)

    // Cases that failed due to the bug
    test(BigInt(1795804389L), BigInt(2957870813L), BigInt(2849476504L))
    test(BigInt(53389L), BigInt(29578713L), BigInt(4631629L))
    test(BigInt(175389L), BigInt(2954378713L), BigInt(2628921865L))
  }

  "ModPowException" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val eBytes = Array[Byte](1, 2, 3, 4, 5)
    val mBytes = Array[Byte](1, 2, 3)
    val aSign = 1
    val eSign = 1
    val mSign = -1
    val aNumber = new BigInteger(aSign, aBytes)
    assert(aBytes.toSeq == aNumber.toByteArray().toSeq)
    val exp = new BigInteger(eSign, eBytes)
    val modulus = new BigInteger(mSign, mBytes)
    assertThrows[ArithmeticException](aNumber.modPow(exp, modulus))
  }

  "ModPowNegExp" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 75, 48, -7)
    val eBytes = Array[Byte](27, -15, 65, 39)
    val mBytes = Array[Byte](-128, 2, 3, 4, 5)
    val aSign = 1
    val eSign = -1
    val mSign = 1
    val rBytes = Array[Byte](12, 118, 46, 86, 92)
    val aNumber = new BigInteger(aSign, aBytes)
    val exp = new BigInteger(eSign, eBytes)
    val modulus = new BigInteger(mSign, mBytes)
    val result = aNumber.modPow(exp, modulus)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "ModPowPosExp" in {
    val aBytes = Array[Byte](-127, 100, 56, 7, 98, -1, 39, -128, 127, 75, 48, -7)
    val eBytes = Array[Byte](27, -15, 65, 39)
    val mBytes = Array[Byte](-128, 2, 3, 4, 5)
    val aSign = 1
    val eSign = 1
    val mSign = 1
    val rBytes = Array[Byte](113, 100, -84, -28, -85)
    val aNumber = new BigInteger(aSign, aBytes)
    val exp = new BigInteger(eSign, eBytes)
    val modulus = new BigInteger(mSign, mBytes)
    val result = aNumber.modPow(exp, modulus)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Overflow int at monReduction" in {
    val that = new BigInteger("3305421065326933993902152933720599759383448872735079828046644324196784608401692")
    val exponent = new BigInteger("1436717344494928391383870735860707930000062552532987154529176227809955095439131")
    val modulus = new BigInteger("5746869377979713565535482943442831720005209173643665270846638168530759837688261")
    val result = that.modPow(exponent, modulus)

    assert(result equals new BigInteger("5460564903238843431550363269308342873211315346397855429778722709516677044851419"))
  }
}

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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerConstructorsTest.java
 */
// scalastyle:on line.size.limit

package ky.korins.math

import java.util.Random

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerConstructorsTest extends wordspec.AnyWordSpec {

  "ConstructorBytesException" in {
    val aBytes = Array[Byte]()
    assertThrows[NumberFormatException](new BigInteger(aBytes))
  }

  "ConstructorBytesNegative1" in {
    val aBytes = Array[Byte](-12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
    val rBytes = Array[Byte](-12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
    val aNumber = new BigInteger(aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorBytesNegative2" in {
    val aBytes = Array[Byte](-12, 56, 100)
    val rBytes = Array[Byte](-12, 56, 100)
    val aNumber = new BigInteger(aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorBytesNegative3" in {
    val aBytes = Array[Byte](-128, -12, 56, 100)
    val rBytes = Array[Byte](-128, -12, 56, 100)
    val aNumber = new BigInteger(aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorBytesNegative4" in {
    val aBytes = Array[Byte](-128, -12, 56, 100, -13, 56, 93, -78)
    val rBytes = Array[Byte](-128, -12, 56, 100, -13, 56, 93, -78)
    val aNumber = new BigInteger(aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorBytesPositive" in {
    val aBytes = Array[Byte](127, 56, 100, -1, 14, 75, -24, -100)
    val rBytes = Array[Byte](127, 56, 100, -1, 14, 75, -24, -100)
    val aNumber = new BigInteger(aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorBytesPositive1" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
    val rBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
    val aNumber = new BigInteger(aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorBytesPositive2" in {
    val aBytes = Array[Byte](12, 56, 100)
    val rBytes = Array[Byte](12, 56, 100)
    val aNumber = new BigInteger(aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorBytesPositive3" in {
    val aBytes = Array[Byte](127, 56, 100, -1)
    val rBytes = Array[Byte](127, 56, 100, -1)
    val aNumber = new BigInteger(aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorBytesZero" in {
    val aBytes = Array[Byte](0, 0, 0, -0, 0, 0, -0)
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == aNumber.signum())
  }

  "ConstructorPrime" in {
    val bitLen = 25
    val rnd = new Random()
    val aNumber = new BigInteger(bitLen, 80, rnd)
    assert(bitLen == aNumber.bitLength())
  }

  "ConstructorPrime2" in {
    val bitLen = 2
    val rnd = new Random()
    val aNumber = new BigInteger(bitLen, 80, rnd)
    assert(bitLen == aNumber.bitLength())
    val num = aNumber.intValue()
    assert(num == 2 || num == 3)
  }

  "ConstructorBigPrime" in {
    val bitLen = 250
    val rnd = new Random()
    val aNumber = new BigInteger(bitLen, 80, rnd)
    assert(bitLen == aNumber.bitLength())
  }

  "ConstructorRandom" in {
    val bitLen = 75
    val rnd: Random = new Random()
    val aNumber = new BigInteger(bitLen, rnd)
    assert(aNumber.bitLength() <= bitLen)
  }

  "ConstructorSignBytesException1" in {
    val aBytes = Array[Byte](123, 45, -3, -76)
    val aSign = 3
    assertThrows[NumberFormatException](new BigInteger(aSign, aBytes))
  }

  "ConstructorSignBytesException2" in {
    val aBytes = Array[Byte](123, 45, -3, -76)
    val aSign = 0
    assertThrows[NumberFormatException](new BigInteger(aSign, aBytes))
  }

  "ConstructorSignBytesNegative1" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15)
    val aSign = -1
    val rBytes = Array[Byte](-13, -57, -101, 1, 75, -90, -46, -92, -4, 15)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorSignBytesNegative2" in {
    val aBytes = Array[Byte](-12, 56, 100, -2, -76, 89, 45, 91, 3, -15)
    val aSign = -1
    val rBytes = Array[Byte](-1, 11, -57, -101, 1, 75, -90, -46, -92, -4, 15)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorSignBytesNegative3" in {
    val aBytes = Array[Byte](-12, 56, 100)
    val aSign = -1
    val rBytes = Array[Byte](-1, 11, -57, -100)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorSignBytesNegative4" in {
    val aBytes = Array[Byte](127, 56, 100, -2)
    val aSign = -1
    val rBytes = Array[Byte](-128, -57, -101, 2)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorSignBytesNegative5" in {
    val aBytes = Array[Byte](-127, 56, 100, -2)
    val aSign = -1
    val rBytes = Array[Byte](-1, 126, -57, -101, 2)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorSignBytesNegative6" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 23, -101)
    val aSign = -1
    val rBytes = Array[Byte](-13, -57, -101, 1, 75, -90, -46, -92, -4, 14, -24, 101)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorSignBytesNegative7" in {
    val aBytes = Array[Byte](-12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 23, -101)
    val aSign = -1
    val rBytes = Array[Byte](-1, 11, -57, -101, 1, 75, -90, -46, -92, -4, 14, -24, 101)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorSignBytesPositive1" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15)
    val aSign = 1
    val rBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorSignBytesPositive2" in {
    val aBytes = Array[Byte](-12, 56, 100, -2, -76, 89, 45, 91, 3, -15)
    val aSign = 1
    val rBytes = Array[Byte](0, -12, 56, 100, -2, -76, 89, 45, 91, 3, -15)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorSignBytesPositive3" in {
    val aBytes = Array[Byte](-12, 56, 100)
    val aSign = 1
    val rBytes = Array[Byte](0, -12, 56, 100)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorSignBytesPositive4" in {
    val aBytes = Array[Byte](127, 56, 100, -2)
    val aSign = 1
    val rBytes = Array[Byte](127, 56, 100, -2)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorSignBytesPositive5" in {
    val aBytes = Array[Byte](-127, 56, 100, -2)
    val aSign = 1
    val rBytes = Array[Byte](0, -127, 56, 100, -2)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorSignBytesPositive6" in {
    val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 23, -101)
    val aSign = 1
    val rBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 23, -101)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorSignBytesPositive7" in {
    val aBytes = Array[Byte](-12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 23, -101)
    val aSign = 1
    val rBytes = Array[Byte](0, -12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 23, -101)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorSignBytesZero1" in {
    val aBytes = Array[Byte](-0, 0, 0, 0, 0, 0, 0)
    val aSign = -1
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == aNumber.signum())
  }

  "ConstructorSignBytesZero2" in {
    val aBytes = Array[Byte](-0, 0, 0, 0, 0, 0, 0)
    val aSign = 0
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == aNumber.signum())
  }

  "ConstructorSignBytesZero3" in {
    val aBytes = Array[Byte](-0, 0, 0, 0, 0, 0, 0)
    val aSign = 1
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == aNumber.signum())
  }

  "ConstructorSignBytesZeroNull1" in {
    val aBytes = Array[Byte]()
    val aSign = -1
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == aNumber.signum())
  }

  "ConstructorSignBytesZeroNull2" in {
    val aBytes = Array[Byte]()
    val aSign = 0
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == aNumber.signum())
  }

  "ConstructorSignBytesZeroNull3" in {
    val aBytes = Array[Byte]()
    val aSign = 1
    val rBytes = Array[Byte](0)
    val aNumber = new BigInteger(aSign, aBytes)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == aNumber.signum())
  }

  "ConstructorStringException" in {
    def test(s: String, radix: Int): Unit =
      assertThrows[NumberFormatException](new BigInteger(s, radix))

    test("9234853876401", 45)
    test("   9234853876401", 10)
    test("92348$*#78987", 34)
    test("98zv765hdsaiy", 20)

    test("", 10)
    test("+", 10)
    test("-", 10)
    test("100000000+10000000", 10) // embedded sign character
  }

  "ConstructorStringRadix10" in {
    val value = "987328901348934898"
    val radix = 10
    val rBytes = Array(13, -77, -78, 103, -103, 97, 68, -14)
    val aNumber = new BigInteger(value, radix)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorStringRadix10Negative" in {
    val value = "-234871376037"
    val radix = 36
    val rBytes = Array(-4, 48, 71, 62, -76, 93, -105, 13)
    val aNumber = new BigInteger(value, radix)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == aNumber.signum())
  }

  "ConstructorStringRadix10Zero" in {
    val value = "-00000000000000"
    val radix = 10
    val rBytes = Array(0)
    val aNumber = new BigInteger(value, radix)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == aNumber.signum())
  }

  "ConstructorStringRadix10Issue2228" in {
    val value = "+100000000"
    val radix = 10
    val rBytes = Array[Byte](5, -11, -31, 0)
    val aNumber = new BigInteger(value, radix)
    assert(rBytes.toSeq == aNumber.toByteArray().toSeq)
    assert(1 == aNumber.signum())
  }

  "ConstructorStringRadix16" in {
    val value = "fe2340a8b5ce790"
    val radix = 16
    val rBytes = Array(15, -30, 52, 10, -117, 92, -25, -112)
    val aNumber = new BigInteger(value, radix)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorStringRadix2" in {
    val value = "10101010101010101"
    val radix = 2
    val rBytes = Array(1, 85, 85)
    val aNumber = new BigInteger(value, radix)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorStringRadix36" in {
    val value = "skdjgocvhdjfkl20jndjkf347ejg457"
    val radix = 36
    val rBytes = Array(0, -12, -116, 112, -105, 12, -36, 66, 108, 66, -20,
        -37, -15, 108, -7, 52, -99, -109, -8, -45, -5)
    val aNumber = new BigInteger(value, radix)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }

  "ConstructorStringRadix8" in {
    val value = "76356237071623450"
    val radix = 8
    val rBytes = Array(7, -50, -28, -8, -25, 39, 40)
    val aNumber = new BigInteger(value, radix)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = aNumber.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == aNumber.signum())
  }
}

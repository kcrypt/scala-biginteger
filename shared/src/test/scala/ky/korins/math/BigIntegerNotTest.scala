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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerNotTest.java
 */
// scalastyle:on line.size.limit

package ky.korins.math

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerNotTest extends wordspec.AnyWordSpec {

  "AndNotNegNegFirstLonger" in {
    val aBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](73, -92, -48, 4, 12, 6, 4, 32, 48, 64, 0, 8, 2)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.andNot(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "AndNotPosPosFirstLonger" in {
    val aBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](0, -128, 9, 56, 100, 0, 0, 1, 1, 90, 1, -32, 0, 10, -126, 21, 82, -31, -96)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.andNot(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "AndNotPosPosFirstShorter" in {
    val aBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val bBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](73, -92, -48, 4, 12, 6, 4, 32, 48, 64, 0, 8, 2)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.andNot(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "NegPosFirstLonger" in {
    val aBytes = Array[Byte](-128, 9, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117, 23, 87, -25, -75)
    val bBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](-1, 127, -10, -57, -101, 1, 2, 2, 2, -96, -16, 8, -40, -59, 68, -88, -88, 16, 72)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.andNot(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NotNeg" in {
    val aBytes = Array[Byte](-128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117)
    val aSign = -1
    val rBytes = Array[Byte](0, -128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -118)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.not()
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "NotOne" in {
    val rBytes = Array[Byte](-2)
    val aNumber = BigInteger.ONE
    val result = aNumber.not()
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NotPos" in {
    val aBytes = Array[Byte](-128, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, -117)
    val aSign = 1
    val rBytes = Array[Byte](-1, 127, -57, -101, 1, 75, -90, -46, -92, -4, 14, -36, -27, 116)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.not()
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NotSpecialCase" in {
    val aBytes = Array[Byte](-1, -1, -1, -1)
    val aSign = 1
    val rBytes = Array[Byte](-1, 0, 0, 0, 0)
    val aNumber = new BigInteger(aSign, aBytes)
    val result = aNumber.not()
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "NotZero" in {
    val rBytes = Array[Byte](-1)
    val aNumber = BigInteger.ZERO
    val result = aNumber.not()
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }
}

/*
 * scala-biginteger - highly optimized BigInteger implementation for scala, scala-js and scala-native.
 *
 * Copyright 2020, 2021 Kirill A. Korinsky <kirill@korins.ky>
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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerAddTest.java
 */
// scalastyle:on line.size.limit

package ky.korins.math

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerAddTest extends wordspec.AnyWordSpec {

  "Case1" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3)
    val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](11, 22, 33, 44, 55, 66, 77, 11, 22, 33)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case2" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3)
    val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](-12, -23, -34, -45, -56, -67, -78, -12, -23, -33)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "Case3" in {
    val aBytes = Array[Byte](3, 4, 5, 6, 7, 8, 9)
    val bBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val rBytes = Array[Byte](2, 2, 2, 2, 2, 2, 2)
    val aSign = 1
    val bSign = -1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case4" in {
    val aBytes = Array[Byte](3, 4, 5, 6, 7, 8, 9)
    val bBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val rBytes = Array[Byte](-3, -3, -3, -3, -3, -3, -2)
    val aSign = -1
    val bSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "Case5" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](3, 4, 5, 6, 7, 8, 9)
    val rBytes = Array[Byte](-3, -3, -3, -3, -3, -3, -2)
    val aSign = 1
    val bSign = -1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "Case6" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](3, 4, 5, 6, 7, 8, 9)
    val rBytes = Array[Byte](2, 2, 2, 2, 2, 2, 2)
    val aSign = -1
    val bSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case7" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](1, 2, 3, 4, 15, 26, 37, 41, 52, 63, 74, 15, 26, 37)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case8" in {
    val aBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val bBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7)
    val rBytes = Array[Byte](1, 2, 3, 4, 15, 26, 37, 41, 52, 63, 74, 15, 26, 37)
    val aNumber = new BigInteger(aBytes)
    val bNumber = new BigInteger(bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case9" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val aSign = -1
    val bSign = -1
    val rBytes = Array[Byte](-2, -3, -4, -5, -16, -27, -38, -42, -53, -64, -75, -16, -27, -37)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "Case10" in {
    val aBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val bBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7)
    val aSign = -1
    val bSign = -1
    val rBytes = Array(-2, -3, -4, -5, -16, -27, -38, -42, -53, -64, -75, -16, -27, -37)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "Case11" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](1, 2, 3, 3, -6, -15, -24, -40, -49, -58, -67, -6, -15, -23)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case12" in {
    val aBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val bBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7)
    val aSign = 1
    val bSign = -1
    val rBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "Case13" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](-2, -3, -4, -4, 5, 14, 23, 39, 48, 57, 66, 5, 14, 23)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(-1 == result.signum())
  }

  "Case14" in {
    val aBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
    val bBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7)
    val aSign = -1
    val bSign = 1
    val rBytes = Array[Byte](1, 2, 3, 3, -6, -15, -24, -40, -49, -58, -67, -6, -15, -23)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case15" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val rBytes = Array[Byte](0)
    val aSign = -1
    val bSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    val resBytes = Array.ofDim[Byte](rBytes.length)
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
  }

  "Case16" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val bBytes = Array[Byte](0)
    val rBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val aSign = 1
    val bSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case17" in {
    val aBytes = Array[Byte](0)
    val bBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val rBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val aSign = 1
    val bSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case18" in {
    val aBytes = Array[Byte](0)
    val bBytes = Array[Byte](0)
    val rBytes = Array[Byte](0)
    val aSign = 1
    val bSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
  }

  "Case19" in {
    val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val rBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val aSign = 1
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = BigInteger.ZERO
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case20" in {
    val bBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val rBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
    val bSign = 1
    val aNumber = BigInteger.ZERO
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case21" in {
    val rBytes = Array[Byte](0)
    val aNumber = BigInteger.ZERO
    val bNumber = BigInteger.ZERO
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(0 == result.signum())
  }

  "Case22" in {
    val rBytes = Array[Byte](2)
    val aNumber = BigInteger.ONE
    val bNumber = BigInteger.ONE
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }

  "Case23" in {
    val aBytes = Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
    val bBytes = Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1)
    val aSign = 1
    val bSign = 1
    val rBytes = Array[Byte](1, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -2)
    val aNumber = new BigInteger(aSign, aBytes)
    val bNumber = new BigInteger(bSign, bBytes)
    val result = aNumber.add(bNumber)
    var resBytes = Array.ofDim[Byte](rBytes.length)
    resBytes = result.toByteArray()
    for (i <- 0 until resBytes.length) {
      assert(rBytes(i) == resBytes(i))
    }
    assert(1 == result.signum())
  }
}

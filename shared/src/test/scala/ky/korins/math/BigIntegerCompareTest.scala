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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerCompareTest.java
 */
// scalastyle:on line.size.limit

package ky.korins.math

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerCompareTest extends wordspec.AnyWordSpec {



    "AbsNegative" in {
      val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
      val aSign = -1
      val rBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
      val aNumber = new BigInteger(aSign, aBytes)
      val result = aNumber.abs()
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(1 == result.signum())
    }

    "AbsPositive" in {
      val aBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
      val aSign = 1
      val rBytes = Array[Byte](1, 2, 3, 4, 5, 6, 7)
      val aNumber = new BigInteger(aSign, aBytes)
      val result = aNumber.abs()
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(1 == result.signum())
    }

    "CompareNegNeg2" in {
      val aBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
      val bBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = -1
      val bSign = -1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(1 == aNumber.compareTo(bNumber))
      assert(1 == aNumber.compareTo(bNumber))
    }

    "CompareToDiffSigns1" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
      val aSign = 1
      val bSign = -1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(1 == aNumber.compareTo(bNumber))
    }

    "CompareToDiffSigns2" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
      val aSign = -1
      val bSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(-1 == aNumber.compareTo(bNumber))
    }

    "CompareToEqualNeg" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = -1
      val bSign = -1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(0 == aNumber.compareTo(bNumber))
    }

    "CompareToEqualPos" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(0 == aNumber.compareTo(bNumber))
    }

    "CompareToNegNeg1" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
      val aSign = -1
      val bSign = -1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(-1 == aNumber.compareTo(bNumber))
    }

    "CompareToNegZero" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = -1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = BigInteger.ZERO
      assert(-1 == aNumber.compareTo(bNumber))
    }

    "CompareToPosPos1" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
      val aSign = 1
      val bSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(1 == aNumber.compareTo(bNumber))
    }

    "CompareToPosPos2" in {
      val aBytes = Array[Byte](10, 20, 30, 40, 50, 60, 70, 10, 20, 30)
      val bBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(-1 == aNumber.compareTo(bNumber))
    }

    "CompareToPosZero" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = BigInteger.ZERO
      assert(1 == aNumber.compareTo(bNumber))
    }

    "CompareToZeroNeg" in {
      val bBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bSign = -1
      val aNumber = BigInteger.ZERO
      val bNumber = new BigInteger(bSign, bBytes)
      assert(1 == aNumber.compareTo(bNumber))
    }

    "CompareToZeroPos" in {
      val bBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bSign = 1
      val aNumber = BigInteger.ZERO
      val bNumber = new BigInteger(bSign, bBytes)
      assert(-1 == aNumber.compareTo(bNumber))
    }

    "CompareToZeroZero" in {
      val aNumber = BigInteger.ZERO
      val bNumber = BigInteger.ZERO
      assert(0 == aNumber.compareTo(bNumber))
    }

    "EqualsBigIntegerFalse" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(aNumber != bNumber)
    }

    "EqualsBigIntegerTrue" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      assert(aNumber equals bNumber)
    }

    "EqualsNull" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      assert(aNumber != null)
    }

    "EqualsObject" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      val obj = new AnyRef()
      assert(aNumber != obj)
    }

    "MaxEqual" in {
      val aBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val rBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      val result = aNumber.max(bNumber)
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(1 == result.signum())
    }

    "MaxGreater" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val rBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      var result = aNumber.max(bNumber)
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(1 == result.signum())
      result = bNumber.max(aNumber)
      assert(aNumber == result)
    }

    "MaxLess" in {
      val aBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val rBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      val result = aNumber.max(bNumber)
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(1 == result.signum())
    }

    "MaxNegZero" in {
      val aBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = -1
      val rBytes = Array[Byte](0)
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = BigInteger.ZERO
      val result = aNumber.max(bNumber)
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(0 == result.signum())
    }

    "MinEqual" in {
      val aBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val rBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      val result = aNumber.min(bNumber)
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(1 == result.signum())
    }

    "MinGreater" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val rBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      val result = aNumber.min(bNumber)
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(1 == result.signum())
    }

    "MinLess" in {
      val aBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val bBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val bSign = 1
      val rBytes = Array(45, 91, 3, -15, 35, 26, 3, 91)
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = new BigInteger(bSign, bBytes)
      val result = aNumber.min(bNumber)
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(1 == result.signum())
    }

    "MinPosZero" in {
      val aBytes = Array[Byte](45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val rBytes = Array[Byte](0)
      val aNumber = new BigInteger(aSign, aBytes)
      val bNumber = BigInteger.ZERO
      val result = aNumber.min(bNumber)
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(0 == result.signum())
    }

    "NegateNegative" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = -1
      val rBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aNumber = new BigInteger(aSign, aBytes)
      val result = aNumber.negate()
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(1 == result.signum())
    }

    "NegatePositive" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val rBytes = Array[Byte](-13, -57, -101, 1, 75, -90, -46, -92, -4, 14, -36, -27, -4, -91)
      val aNumber = new BigInteger(aSign, aBytes)
      val result = aNumber.negate()
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(-1 == result.signum())
    }

    "NegateZero" in {
      val rBytes = Array[Byte](0)
      val aNumber = BigInteger.ZERO
      val result = aNumber.negate()
      var resBytes = Array.ofDim[Byte](rBytes.length)
      resBytes = result.toByteArray()
      for (i <- 0 until resBytes.length) {
        assert(rBytes(i) == resBytes(i))
      }
      assert(0 == result.signum())
    }

    "tassestSignumNegative" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = -1
      val aNumber = new BigInteger(aSign, aBytes)
      assert(-1 == aNumber.signum())
    }

    "SignumPositive" in {
      val aBytes = Array[Byte](12, 56, 100, -2, -76, 89, 45, 91, 3, -15, 35, 26, 3, 91)
      val aSign = 1
      val aNumber = new BigInteger(aSign, aBytes)
      assert(1 == aNumber.signum())
    }

    "SignumZero" in {
      val aNumber = BigInteger.ZERO
      assert(0 == aNumber.signum())
    }
}

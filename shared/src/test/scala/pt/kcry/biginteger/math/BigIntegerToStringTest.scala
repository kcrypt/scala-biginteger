/*
 * scala-biginteger - highly optimized BigInteger implementation for scala, scala-js and scala-native.
 *
 * Copyright 2020, 2021 Kirill A. Korinsky <kirill@korins.ky>
 * Copyright 2022 Kcrypt Lab UG <opensource@kcry.pt>
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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerToStringTest.java
 */
// scalastyle:on line.size.limit

package pt.kcry.biginteger

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerToStringTest extends wordspec.AnyWordSpec {

  "tes10PosVerySmall" in {
    val value = "2"
    val aNumber = new BigInteger(value)
    val result = aNumber.toString()
    assert(2 == aNumber.intValue())
    assert(value == result)
  }

  "tes10NegVerySmall" in {
    val value = "-2"
    val aNumber = new BigInteger(value)
    val result = aNumber.toString()
    assert(-2 == aNumber.intValue())
    assert(value == result)
  }

  "tes10PosSmall" in {
    val value = "24"
    val aNumber = new BigInteger(value)
    val result = aNumber.toString()
    assert(24 == aNumber.intValue())
    assert(value == result)
  }

  "tes10NegSmall" in {
    val value = "-24"
    val aNumber = new BigInteger(value)
    val result = aNumber.toString()
    assert(-24 == aNumber.intValue())
    assert(value == result)
  }

  "tes10PosLong" in {
    val value = "2489756308572"
    val aNumber = new BigInteger(value)
    val result = aNumber.toString()
    assert(2489756308572L == aNumber.longValue())
    assert(value == result)
  }

  "tes10NegLong" in {
    val value = "-2489756308572"
    val aNumber = new BigInteger(value)
    val result = aNumber.toString()
    assert(-2489756308572L == aNumber.longValue())
    assert(value == result)
  }

  "tes10Neg" in {
    val value = "-2489756308572364789878394872984"
    val aNumber = new BigInteger(value)
    val result = aNumber.toString()
    assert(value == result)
  }

  "Radix10Neg" in {
    val value = "-2489756308572364789878394872984"
    val radix = 10
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix10Pos" in {
    val value = "2387627892347567398736473476"
    val radix = 10
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix1610Neg" in {
    val value = "-2489756308572364789878394872984"
    val radix = 16
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix1610Pos" in {
    val value = "2387627892347567398736473476"
    val radix = 16
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix16Neg" in {
    val value = "-287628a883451b800865c67e8d7ff20"
    val radix = 16
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix16Pos" in {
    val value = "287628a883451b800865c67e8d7ff20"
    val radix = 16
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix24Neg" in {
    val value = "-287628a88gmn3451b8ijk00865c67e8d7ff20"
    val radix = 24
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix24Pos" in {
    val value = "287628a883451bg80ijhk0865c67e8d7ff20"
    val radix = 24
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix2Neg" in {
    val value = "-101001100010010001001010101110000101010110001010010101010101010101010101010101010101010101010010101"
    val radix = 2
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix2Pos" in {
    val value = "101000011111000000110101010101010101010001001010101010101010010101010101010000100010010"
    val radix = 2
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix36Neg" in {
    val value = "-uhguweut98iu4h3478tq3985pq98yeiuth33485yq4aiuhalai485yiaehasdkr8tywi5uhslei8"
    val radix = 36
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "Radix36Pos" in {
    val value = "23895lt45y6vhgliuwhgi45y845htsuerhsi4586ysuerhtsio5y68peruhgsil4568ypeorihtse48y6"
    val radix = 36
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(radix)
    assert(value == result)
  }

  "RadixOutOfRange" in {
    val value = "442429234853876401"
    val radix = 10
    val aNumber = new BigInteger(value, radix)
    val result = aNumber.toString(45)
    assert(value == result)
  }
}

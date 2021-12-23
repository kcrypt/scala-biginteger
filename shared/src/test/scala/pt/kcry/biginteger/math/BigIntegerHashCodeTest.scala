/*
 * scala-biginteger - highly optimized BigInteger implementation for scala, scala-js and scala-native.
 *
 * Copyright 2020, 2021 Kirill A. Korinsky <kirill@korins.ky>
 * Copyright 2022 Kcrypt Lab UG <support@kcry.pt>
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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerHashCodeTest.java
 */
// scalastyle:on line.size.limit

package pt.kcry.biginteger

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerHashCodeTest extends wordspec.AnyWordSpec {

  "EqualObjects" in {
    val value1 = "12378246728727834290276457386374882976782849"
    val value2 = "12378246728727834290276457386374882976782849"
    val aNumber1 = new BigInteger(value1)
    val aNumber2 = new BigInteger(value2)
    val code1 = aNumber1.hashCode
    val code2 = aNumber2.hashCode
    if (aNumber1 == aNumber2) {
      assert(code2 == code1)
    }
  }

  "hashCodeIssue2159" in {
    val a = 936417286865811553L
    val b = 1136802186495971562L
    val c = BigInteger.valueOf(a).add(BigInteger.valueOf(b))
    val d = BigInteger.valueOf(c.longValue())
    assert(c equals d) // sanity
    assert(c.hashCode == d.hashCode)
  }

  "SameObject" in {
    val value1 = "12378246728727834290276457386374882976782849"
    val value2 = "-5634562095872038262928728727834290276457386374882976782849"
    val aNumber1 = new BigInteger(value1)
    val aNumber2 = new BigInteger(value2)
    val code1 = aNumber1.hashCode
    aNumber1.add(aNumber2).shiftLeft(125)
    aNumber1.subtract(aNumber2).shiftRight(125)
    aNumber1.multiply(aNumber2).toByteArray()
    aNumber1.divide(aNumber2).bitLength()
    aNumber1.gcd(aNumber2).pow(7)
    val code2 = aNumber1.hashCode
    assert(code2 == code1)
  }

  "UnequalObjectsUnequal" in {
    val value1 = "12378246728727834290276457386374882976782849"
    val value2 = "-5634562095872038262928728727834290276457386374882976782849"
    val aNumber1 = new BigInteger(value1)
    val aNumber2 = new BigInteger(value2)
    val code1 = aNumber1.hashCode
    val code2 = aNumber2.hashCode
    if (aNumber1 != aNumber2) {
      assert(code1 != code2)
    }
  }
}

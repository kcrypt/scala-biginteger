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
 * https://github.com/gwtproject/gwt/blob/master/user/test/com/google/gwt/emultest/java/math/BigIntegerXorTest.java
 */
// scalastyle:on line.size.limit

package pt.kcry.biginteger

import org.scalatest.wordspec

import scala.language.implicitConversions

class BigIntegerXorTest extends wordspec.AnyWordSpec {

  "NegNegFirstLonger" in {
    val numA = "-2837462783428374767845648748973847593874837948575684767"
    val numB = "-293478573489347658763745839457637"
    val res = "2837462783428374767845615168483972194300564226167553530"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "NegNegFirstShorter" in {
    val numA = "293478573489347658763745839457637"
    val numB = "2837462783428374767845648748973847593874837948575684767"
    val res = "2837462783428374767845615168483972194300564226167553530"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "NegNegSameLength" in {
    val numA = "-283746278342837476784564875684767"
    val numB = "-293478573489347658763745839457637"
    val res = "71412358434940908477702819237626"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "NegPos" in {
    val numA = "-27384627835298756289327365"
    val numB = "0"
    val res = "-27384627835298756289327365"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "NegPosFirstLonger" in {
    val numA = "-2837462783428374767845648748973847593874837948575684767"
    val numB = "293478573489347658763745839457637"
    val res = "-2837462783428374767845615168483972194300564226167553532"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "NegPosFirstShorter" in {
    val numA = "-293478573489347658763745839457637"
    val numB = "2837462783428374767845648748973847593874837948575684767"
    val res = "-2837462783428374767845615168483972194300564226167553532"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "NegPosSameLength" in {
    val numA = "-283746278342837476784564875684767"
    val numB = "293478573489347658763745839457637"
    val res = "-71412358434940908477702819237628"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "OneOne" in {
    val numA = "1"
    val numB = "1"
    val res = "0"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "PosNegFirstLonger" in {
    val numA = "2837462783428374767845648748973847593874837948575684767"
    val numB = "-293478573489347658763745839457637"
    val res = "-2837462783428374767845615168483972194300564226167553532"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "PosNegFirstShorter" in {
    val numA = "293478573489347658763745839457637"
    val numB = "-2837462783428374767845648748973847593874837948575684767"
    val res = "-2837462783428374767845615168483972194300564226167553532"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "PosNegSameLength" in {
    val numA = "283746278342837476784564875684767"
    val numB = "-293478573489347658763745839457637"
    val res = "-71412358434940908477702819237628"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "PosPosFirstLonger" in {
    val numA = "2837462783428374767845648748973847593874837948575684767"
    val numB = "293478573489347658763745839457637"
    val res = "2837462783428374767845615168483972194300564226167553530"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "PosPosFirstShorter" in {
    val numA = "293478573489347658763745839457637"
    val numB = "2837462783428374767845648748973847593874837948575684767"
    val res = "2837462783428374767845615168483972194300564226167553530"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "PosPosSameLength" in {
    val numA = "283746278342837476784564875684767"
    val numB = "293478573489347658763745839457637"
    val res = "71412358434940908477702819237626"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "PosZero" in {
    val numA = "27384627835298756289327365"
    val numB = "0"
    val res = "27384627835298756289327365"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "ZeroNeg" in {
    val numA = "0"
    val numB = "-27384627835298756289327365"
    val res = "-27384627835298756289327365"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "ZeroOne" in {
    val numA = "0"
    val numB = "1"
    val res = "1"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "ZeroPos" in {
    val numA = "0"
    val numB = "27384627835298756289327365"
    val res = "27384627835298756289327365"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }

  "ZeroZero" in {
    val numA = "0"
    val numB = "0"
    val res = "0"
    val aNumber = new BigInteger(numA)
    val bNumber = new BigInteger(numB)
    val result = aNumber.xor(bNumber)
    assert(result.toString == res)
  }
}

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

package ky.korins.math.benchmark

import java.math.{BigInteger => nBigInteger}
import ky.korins.math.benchmark.original.java.{BigInteger => jBigInteger}
import ky.korins.math.benchmark.original.scalajs.{BigInteger => sBigInteger}
import ky.korins.math.{BigInteger => kBigInteger}

trait BaseBenchmark {
  var bits: Int

  val javaONE = jBigInteger.valueOf(1)
  val javaTWO = jBigInteger.valueOf(2)
  val javaTHREE = jBigInteger.valueOf(3)

  val sTWO = sBigInteger.valueOf(2)
  val sONE = sBigInteger.valueOf(1)
  val sTHREE = sBigInteger.valueOf(3)

  val nTWO = nBigInteger.valueOf(2)
  val nONE = nBigInteger.valueOf(1)
  val nTHREE = nBigInteger.valueOf(3)

  val kONE = kBigInteger.valueOf(1)
  val kTWO = kBigInteger.valueOf(2)
  val kTHREE = kBigInteger.valueOf(3)

  var javaPrime1: jBigInteger = jBigInteger.valueOf(0)
  var javaPrime2: jBigInteger = jBigInteger.valueOf(0)
  var javaPrimeHalf: jBigInteger = jBigInteger.valueOf(0)
  var javaHuge: jBigInteger = jBigInteger.valueOf(0)

  var javaEven1: jBigInteger = jBigInteger.valueOf(0)
  var javaEven2: jBigInteger = jBigInteger.valueOf(0)

  var sPrime1: sBigInteger = sBigInteger.valueOf(0)
  var sPrime2: sBigInteger = sBigInteger.valueOf(0)
  var sPrimeHalf: sBigInteger = sBigInteger.valueOf(0)
  var sHuge: sBigInteger = sBigInteger.valueOf(0)

  var sEven1: sBigInteger = sBigInteger.valueOf(0)
  var sEven2: sBigInteger = sBigInteger.valueOf(0)

  var nPrime1: nBigInteger = nBigInteger.valueOf(0)
  var nPrime2: nBigInteger = nBigInteger.valueOf(0)
  var nPrimeHalf: nBigInteger = nBigInteger.valueOf(0)
  var nHuge: nBigInteger = nBigInteger.valueOf(0)

  var nEven1: nBigInteger = nBigInteger.valueOf(0)
  var nEven2: nBigInteger = nBigInteger.valueOf(0)

  var kPrime1: kBigInteger = kBigInteger.valueOf(0)
  var kPrime2: kBigInteger = kBigInteger.valueOf(0)
  var kPrimeHalf: kBigInteger = kBigInteger.valueOf(0)
  var kHuge: kBigInteger = kBigInteger.valueOf(0)

  var kEven1: kBigInteger = kBigInteger.valueOf(0)
  var kEven2: kBigInteger = kBigInteger.valueOf(0)

  def setupNumbers(): Unit = {
    javaPrime1 = new jBigInteger(Numbers.primes_1(bits))
    sPrime1 = new sBigInteger(Numbers.primes_1(bits))
    nPrime1 = new nBigInteger(Numbers.primes_1(bits))
    kPrime1 = new kBigInteger(Numbers.primes_1(bits))

    javaEven1 = javaPrime1 subtract javaONE
    sEven1 = sPrime1 subtract sONE
    nEven1 = nPrime1 subtract nONE
    kEven1 = kPrime1 subtract kONE

    javaPrime2 = new jBigInteger(Numbers.primes_2(bits))
    sPrime2 = new sBigInteger(Numbers.primes_2(bits))
    nPrime2 = new nBigInteger(Numbers.primes_2(bits))
    kPrime2 = new kBigInteger(Numbers.primes_2(bits))

    javaEven2 = javaPrime2 subtract javaONE
    sEven2 = sPrime2 subtract sONE
    nEven2 = nPrime2 subtract nONE
    kEven2 = kPrime2 subtract kONE

    javaPrimeHalf = javaPrime1 shiftRight(bits / 2)
    sPrimeHalf = sPrime1 shiftRight(bits / 2)
    nPrimeHalf = nPrime1 shiftRight(bits / 2)
    kPrimeHalf = kPrime1 shiftRight(bits / 2)

    javaHuge = javaPrime1 multiply javaPrime2
    sHuge = sPrime1 multiply sPrime2
    nHuge = nPrime1 multiply nPrime2
    kHuge = kPrime1 multiply kPrime2
  }
}

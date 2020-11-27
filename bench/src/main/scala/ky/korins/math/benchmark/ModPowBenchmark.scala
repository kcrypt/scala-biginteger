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

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class ModPowBenchmark extends BaseBenchmark {
  @Param(Array("128", "512", "1024", "4096", "8192"))
  override var bits: Int = 0

  @Setup
  def setup(): Unit =
    super.setupNumbers()

  @Benchmark
  def java(): Unit = {
    javaPrime1.modPow(javaPrime2, javaPrime2)
    javaPrime2.modPow(javaPrime1, javaPrime1)
    javaPrime1.modPow(javaPrime2, javaEven2)
    javaPrime2.modPow(javaPrime1, javaEven1)
  }

  @Benchmark
  def scalajs(): Unit = {
    sPrime1.modPow(sPrime2, sPrime2)
    sPrime2.modPow(sPrime1, sPrime1)
    sPrime1.modPow(sPrime2, sEven2)
    sPrime2.modPow(sPrime1, sEven1)
  }

  @Benchmark
  def java_native(): Unit = {
    nPrime1.modPow(nPrime2, nPrime2)
    nPrime2.modPow(nPrime1, nPrime1)
    nPrime1.modPow(nPrime2, nEven2)
    nPrime2.modPow(nPrime1, nEven1)
  }

  @Benchmark
  def korinsky(): Unit = {
    kPrime1.modPow(kPrime2, kPrime2)
    kPrime2.modPow(kPrime1, kPrime1)
    kPrime1.modPow(kPrime2, kEven2)
    kPrime2.modPow(kPrime2, kEven1)
  }
}
